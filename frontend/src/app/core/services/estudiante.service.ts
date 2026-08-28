import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

export interface Estudiante {
  estudianteId: string;
  codigoEstudiante: string;
  estado: string;
  fechaIngreso?: string;
  usuarioId?: string;
  username?: string;
  primerNombre: string;
  primerApellido: string;
  numeroDocumento: string;
  correo?: string;
  correoAlcanzable?: boolean;
}
export interface EstudianteRequest {
  tipoDocumento: string;
  numeroDocumento: string;
  primerNombre: string;
  primerApellido: string;
  correo?: string;
  fechaIngreso?: string;
  observaciones?: string;
}

export interface EstudianteDetalle {
  estudianteId: string;
  codigoEstudiante: string;
  estado: string;
  fechaIngreso?: string;
  observaciones?: string;
  tipoDocumento: string;
  numeroDocumento: string;
  primerNombre: string;
  segundoNombre?: string;
  primerApellido: string;
  segundoApellido?: string;
  correo?: string;
  telefono?: string;
  fechaNacimiento?: string;
  direccion?: string;
  ciudad?: string;
}
export interface EditarEstudianteRequest {
  tipoDocumento: string;
  numeroDocumento: string;
  primerNombre: string;
  segundoNombre?: string;
  primerApellido: string;
  segundoApellido?: string;
  correo?: string;
  telefono?: string;
  fechaNacimiento?: string;
  direccion?: string;
  ciudad?: string;
  fechaIngreso?: string;
  estado?: string;
  observaciones?: string;
}

export interface EnvioCredencialesResponse {
  mensaje: string;
  enviado: boolean;
}

@Injectable({ providedIn: 'root' })
export class EstudianteService {
  private readonly API = `${environment.apiUrl}/community/estudiantes`;
  constructor(private http: HttpClient) {}
  listar(): Observable<Estudiante[]> {
    return this.http.get<Estudiante[]>(this.API);
  }
  crear(d: EstudianteRequest): Observable<{ mensaje: string }> {
    return this.http.post<{ mensaje: string }>(this.API, d);
  }

  obtener(id: string): Observable<EstudianteDetalle> {
    return this.http.get<EstudianteDetalle>(`${this.API}/${id}`);
  }

  editar(
    id: string,
    d: EditarEstudianteRequest,
  ): Observable<{ mensaje: string }> {
    return this.http.put<{ mensaje: string }>(`${this.API}/${id}`, d);
  }

  activar(id: string): Observable<{ mensaje: string }> {
    return this.http.patch<{ mensaje: string }>(
      `${this.API}/${id}/activar`,
      {},
    );
  }

  inactivar(id: string): Observable<{ mensaje: string }> {
    return this.http.patch<{ mensaje: string }>(
      `${this.API}/${id}/inactivar`,
      {},
    );
  }

  enviarCredenciales(id: string): Observable<EnvioCredencialesResponse> {
    return this.http.post<EnvioCredencialesResponse>(
      `${this.API}/${id}/enviar-credenciales`,
      {},
    );
  }
}
