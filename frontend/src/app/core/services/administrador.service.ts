import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment.development';

// Lo que ENVIAMOS al crear un administrador
export interface AdministradorRequest {
  tipoDocumento: string;
  numeroDocumento: string;
  primerNombre: string;
  primerApellido: string;
  correo: string;
  institucionId: string;
}

// Lo que RECIBIMOS al listar (coincide con la proyección del backend)
export interface Administrador {
  usuarioId: string;
  username: string;
  estado: string;
  requiereCambioPassword: boolean;
  primerNombre: string;
  primerApellido: string;
  numeroDocumento: string;
  correo: string;
  emailLogin: string;
  tipoDocumento?: string;
  institucionId: string;
  institucionNombre: string;
  ultimoLogin?: string;
  createdAt?: string;
}

@Injectable({ providedIn: 'root' })
export class AdministradorService {

  private readonly API = `${environment.apiUrl}/administradores`;

  constructor(private http: HttpClient) {}

  crear(datos: AdministradorRequest): Observable<{ mensaje: string }> {
    return this.http.post<{ mensaje: string }>(this.API, datos);
  }

  listar(): Observable<Administrador[]> {
    return this.http.get<Administrador[]>(this.API);
  }

  activar(id: string): Observable<{ mensaje: string }> {
    return this.http.patch<{ mensaje: string }>(`${this.API}/${id}/activar`, {});
  }

  inactivar(id: string): Observable<{ mensaje: string }> {
    return this.http.patch<{ mensaje: string }>(`${this.API}/${id}/inactivar`, {});
  }

  editar(id: string, datos: AdministradorRequest): Observable<{ mensaje: string }> {
    return this.http.put<{ mensaje: string }>(`${this.API}/${id}`, datos);
  }

}
