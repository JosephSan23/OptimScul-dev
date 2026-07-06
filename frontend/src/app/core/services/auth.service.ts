import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, tap } from 'rxjs';

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

  private readonly API        = 'http://localhost:8080/api/auth';
  private readonly TOKEN_KEY  = 'optimscul_token';
  private readonly USER_KEY   = 'optimscul_user';

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
    // El visitante va a la página principal, no a un dashboard
    if (roles.includes('VISITANTE')) {
      this.router.navigate(['/primeros-pasos']);
      return;
    }

    if (tipoContexto === 'PLATAFORMA') {
      this.router.navigate(['/dashboard/admin']);
      return;
    }
    if (roles.includes('ADMIN_INSTITUCION'))   this.router.navigate(['/dashboard/colegio']);
    else if (roles.includes('DOCENTE'))   this.router.navigate(['/dashboard/profesor']);
    else if (roles.includes('ESTUDIANTE')) this.router.navigate(['/dashboard/estudiante']);
    else if (roles.includes('ACUDIENTE'))  this.router.navigate(['/dashboard/acudiente']);
    else this.router.navigate(['/']);
  }

  logout(): void {
    localStorage.removeItem(this.TOKEN_KEY);
    localStorage.removeItem(this.USER_KEY);
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
