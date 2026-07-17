import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, tap } from 'rxjs';
import { environment } from '../../../environments/environment.development';


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

   /** Modos que este usuario tiene disponibles según sus roles */
  getModosDisponibles(): string[] {
    const roles = this.getRoles();
    return this.PRIORIDAD_MODOS.filter(m => roles.includes(m));
  }

  /** El sombrero puesto ahora mismo */
  getModo(): string | null {
    const guardado = localStorage.getItem(this.MODO_KEY);
    const disponibles = this.getModosDisponibles();
    // si lo guardado ya no es válido (cambió de roles), cae al de mayor prioridad
    if (guardado && disponibles.includes(guardado)) return guardado;
    return disponibles[0] ?? null;
  }

  enModo(modo: string): boolean {
    return this.getModo() === modo;
  }

  /** El botón "Cambiar a..." llama esto */
  cambiarModo(modo: string): void {
    if (!this.getModosDisponibles().includes(modo)) return;   // no tiene ese rol
    localStorage.setItem(this.MODO_KEY, modo);
    this.router.navigate([this.RUTA_POR_MODO[modo] ?? '/']);
  }

  constructor(private http: HttpClient, private router: Router) {}

  login(credentials: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.API}/login`, credentials).pipe(
      tap(response => {
        localStorage.setItem(this.TOKEN_KEY, response.token);
        localStorage.setItem(this.USER_KEY, JSON.stringify({
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

  private redirigirSegunRol(tipoContexto: string, roles: string[]): void {
    if (roles.includes('VISITANTE')) { this.router.navigate(['/primeros-pasos']); return; }
    if (tipoContexto === 'PLATAFORMA') { this.router.navigate(['/dashboard/admin']); return; }

    localStorage.removeItem(this.MODO_KEY);               // arranca limpio en cada login
    const modo = this.getModo();                          // el de mayor prioridad que tenga
    this.router.navigate([modo ? this.RUTA_POR_MODO[modo] : '/']);
  }

  logout(): void {
    localStorage.removeItem(this.TOKEN_KEY);
    localStorage.removeItem(this.USER_KEY);
    localStorage.removeItem(this.MODO_KEY);
    this.router.navigate(['/login']);
  }

  getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
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
    const user = localStorage.getItem(this.USER_KEY);
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
