import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, shareReplay } from 'rxjs';
import { environment } from '../../../environments/environment';

export interface Perfil {
  tipoDocumento: string; numeroDocumento: string; primerNombre: string; primerApellido: string;
  segundoNombre: string; segundoApellido: string; fechaNacimiento: string; sexo: string; nacionalidad: string;
  telefono: string; telefonoAlternativo: string; correo: string;
  direccion: string; barrio: string; ciudad: string; departamento: string; pais: string;
  fotoUrl: string | null;
  esDocente: boolean; especialidad: string | null; tituloProfesional: string | null;
  username: string; requiereCambioPassword: boolean; perfilCompleto: boolean; camposFaltantes: string[];
}

@Injectable({ providedIn: 'root' })
export class PerfilService {
  private readonly API = `${environment.apiUrl}/perfil`;
  private cache$?: Observable<Perfil>;

  constructor(private http: HttpClient) {}

  /** Cacheada para el guard (una sola llamada por sesión hasta refrescar). */
  perfil(): Observable<Perfil> {
    if (!this.cache$) this.cache$ = this.http.get<Perfil>(this.API).pipe(shareReplay(1));
    return this.cache$;
  }
  refrescar(): void { this.cache$ = undefined; }

  actualizar(datos: Partial<Perfil>): Observable<Perfil> { return this.http.put<Perfil>(this.API, datos); }
  cambiarPassword(actual: string, nueva: string): Observable<void> {
    return this.http.post<void>(`${this.API}/password`, { actual, nueva });
  }
  subirFoto(archivo: File): Observable<{ fotoUrl: string }> {
    const fd = new FormData(); fd.append('archivo', archivo);
    return this.http.post<{ fotoUrl: string }>(`${this.API}/foto`, fd);
  }
}
