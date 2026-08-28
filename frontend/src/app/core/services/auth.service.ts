import { Injectable, Inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, tap } from 'rxjs';
import { environment } from '../../../environments/environment';
import { PerfilService } from './perfil.service';


export interface LoginRequest {
  usernameOrEmail: string;
  password: string;
}

export interface LoginResponse {
  token: string;
  usuarioId: string;
  username: string;
  tipoContexto: string;
  roles: string[];
}

@Injectable({ providedIn: 'root' })
export class AuthService {

  private readonly API        = `${environment.apiUrl}/auth`;
  private readonly TOKEN_KEY  = 'optimscul_token';
  private readonly USER_KEY   = 'optimscul_user';
  private readonly MODO_KEY = 'optimscul_modo';

  private readonly RUTA_POR_MODO: Record<string, string> = {
    'ADMIN_INSTITUCION':      '/dashboard/colegio',
    'COORDINADOR_ACADEMICO':  '/dashboard/cooracademico',
    'DOCENTE':                '/dashboard/profesor',
    'ESTUDIANTE':             '/dashboard/estudiante',
    'ACUDIENTE':              '/dashboard/acudiente'
  };

  private readonly PRIORIDAD_MODOS = [
    'ADMIN_INSTITUCION', 'COORDINADOR_ACADEMICO', 'DOCENTE', 'ESTUDIANTE', 'ACUDIENTE'
  ];

  /** Acceso seguro a localStorage: null cuando corre en el servidor (SSR) */
  private get storage(): Storage | null {
    return isPlatformBrowser(this.platformId) ? localStorage : null;
  }

   /** Modos que este usuario tiene disponibles según sus roles */
  getModosDisponibles(): string[] {
    const roles = this.getRoles();
    return this.PRIORIDAD_MODOS.filter(m => roles.includes(m));
  }

  /** El sombrero puesto ahora mismo */
  getModo(): string | null {
    const guardado = this.storage?.getItem(this.MODO_KEY) ?? null;
    const disponibles = this.getModosDisponibles();
    if (guardado && disponibles.includes(guardado)) return guardado;
    return disponibles[0] ?? null;
  }

  enModo(modo: string): boolean {
    return this.getModo() === modo;
  }

  /** El botón "Cambiar a..." llama esto */
  cambiarModo(modo: string): void {
    if (!this.getModosDisponibles().includes(modo)) return;
    this.storage?.setItem(this.MODO_KEY, modo);
    this.router.navigate([this.RUTA_POR_MODO[modo] ?? '/']);
  }

  constructor(private http: HttpClient, private router: Router, private perfil: PerfilService,
              @Inject(PLATFORM_ID) private platformId: Object) {}

  login(credentials: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.API}/login`, credentials).pipe(
      tap(response => {
        this.perfil.refrescar();
        this.storage?.setItem(this.TOKEN_KEY, response.token);
        this.storage?.setItem(this.USER_KEY, JSON.stringify({
          usuarioId:    response.usuarioId,
          username:     response.username,
          tipoContexto: response.tipoContexto,
          roles:        response.roles
        }));
        this.redirigirSegunRol(response.tipoContexto, response.roles);
      })
    );
  }

  esSuperAdmin(): boolean {
    return this.getTipoContexto() === 'PLATAFORMA' && !this.tieneRol('VISITANTE');
  }

  /** Ruta del dashboard de inicio según el rol/modo activo del usuario. */
  rutaInicio(): string {
    if (this.esSuperAdmin()) return '/dashboard/admin';
    const modo = this.getModo();
    return modo ? (this.RUTA_POR_MODO[modo] ?? '/dashboard/perfil') : '/dashboard/perfil';
  }

  private redirigirSegunRol(tipoContexto: string, roles: string[]): void {
    if (roles.includes('VISITANTE')) { this.router.navigate(['/primeros-pasos']); return; }
    if (tipoContexto === 'PLATAFORMA') { this.router.navigate(['/dashboard/admin']); return; }

    this.storage?.removeItem(this.MODO_KEY);
    const modo = this.getModo();
    this.router.navigate([modo ? this.RUTA_POR_MODO[modo] : '/']);
  }

  logout(): void {
    this.perfil.refrescar();
    this.storage?.removeItem(this.TOKEN_KEY);
    this.storage?.removeItem(this.USER_KEY);
    this.storage?.removeItem(this.MODO_KEY);
    this.router.navigate(['/login']);
  }

  getToken(): string | null {
    return this.storage?.getItem(this.TOKEN_KEY) ?? null;
  }

  isLoggedIn(): boolean {
    const token = this.getToken();
    if (!token) return false;
    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      return payload.exp * 1000 > Date.now();
    } catch {
      return false;
    }
  }

  getUsuarioActual(): any {
    const user = this.storage?.getItem(this.USER_KEY) ?? null;
    return user ? JSON.parse(user) : null;
  }

  getTipoContexto(): string | null {
    return this.getUsuarioActual()?.tipoContexto ?? null;
  }

  getRoles(): string[] {
    return this.getUsuarioActual()?.roles ?? [];
  }

  tieneRol(rol: string): boolean {
    return this.getRoles().includes(rol);
  }
}
