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
}

// hace que el servicio este disponible en toda la aplicacion angular
@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly API = 'http://localhost:8080/api/auth';
  private readonly TOKEN_KEY = 'optimscul_token';
  private readonly USER_KEY  = 'optimscul_user';

  constructor(private http: HttpClient, private router: Router) {}

  // metodo login que hace una peticion HTTP POST al backend enviando el usuario y contraseña
  login(credentials: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.API}/login`, credentials).pipe(
      tap(response => {
        localStorage.setItem(this.TOKEN_KEY, response.token);
        localStorage.setItem(this.USER_KEY, JSON.stringify({
          usuarioId:    response.usuarioId,
          username:     response.username,
          tipoContexto: response.tipoContexto
        }));
      })
    );
  }

  // Borra el token y los datos del usuario del navegador y lo redirige al login
  logout(): void {
    localStorage.removeItem(this.TOKEN_KEY);
    localStorage.removeItem(this.USER_KEY);
    this.router.navigate(['/login']);
  }

  // compara la fecha de expiracion con la hora actual
  getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  isLoggedIn(): boolean {
    const token = this.getToken();
    if (!token) return false;
    try {
      // Verificar expiración leyendo el payload del JWT
      const payload = JSON.parse(atob(token.split('.')[1]));
      return payload.exp * 1000 > Date.now();
    } catch {
      return false;
    }
  }

  // permite saber que usuario inicio sesion y asi mismo mostrarle informacion
  getUsuarioActual(): any {
    const user = localStorage.getItem(this.USER_KEY);
    return user ? JSON.parse(user) : null;
  }

  getTipoContexto(): string | null {
    return this.getUsuarioActual()?.tipoContexto ?? null;
  }


}
