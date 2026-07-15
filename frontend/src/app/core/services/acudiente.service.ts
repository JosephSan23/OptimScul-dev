import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

export interface AcudienteDeEstudiante {
  vinculoId: string;
  acudienteId: string;
  parentesco: string;
  esPrincipal: boolean;
  autorizadoRecogida: boolean;
  autorizadoInfoAcademica: boolean;
  primerNombre: string;
  primerApellido: string;
  numeroDocumento: string;
  correo?: string;
  username?: string;
  ocupacion?: string;
  empresa?: string;
  estado: string;
}
export interface AcudienteRequest {
  tipoDocumento: string;
  numeroDocumento: string;
  primerNombre: string;
  primerApellido: string;
  correo?: string;
  ocupacion?: string;
  empresa?: string;
  parentesco: string;
  esPrincipal?: boolean;
  autorizadoRecogida?: boolean;
  autorizadoInfoAcademica?: boolean;
}
export interface AcudienteDetalle {
  vinculoId: string;
  acudienteId: string;
  parentesco: string;
  esPrincipal: boolean;
  autorizadoRecogida: boolean;
  autorizadoInfoAcademica: boolean;
  estado: string;
  ocupacion?: string;
  empresa?: string;
  tipoDocumento: string;
  numeroDocumento: string;
  primerNombre: string;
  segundoNombre?: string;
  primerApellido: string;
  segundoApellido?: string;
  correo?: string;
  telefono?: string;
}
export interface EditarAcudienteRequest {
  tipoDocumento: string;
  numeroDocumento: string;
  primerNombre: string;
  segundoNombre?: string;
  primerApellido: string;
  segundoApellido?: string;
  correo?: string;
  telefono?: string;
  ocupacion?: string;
  empresa?: string;
  estado?: string;
  parentesco: string;
  esPrincipal?: boolean;
  autorizadoRecogida?: boolean;
  autorizadoInfoAcademica?: boolean;
}

@Injectable({ providedIn: 'root' })
export class AcudienteService {
  private readonly API = `${environment.apiUrl}/community`;
  constructor(private http: HttpClient) {}
  listarPorEstudiante(id: string): Observable<AcudienteDeEstudiante[]> {
    return this.http.get<AcudienteDeEstudiante[]>(
      `${this.API}/estudiantes/${id}/acudientes`,
    );
  }
  crearVincular(
    id: string,
    d: AcudienteRequest,
  ): Observable<{ mensaje: string }> {
    return this.http.post<{ mensaje: string }>(
      `${this.API}/estudiantes/${id}/acudientes`,
      d,
    );
  }
  obtener(vinculoId: string): Observable<AcudienteDetalle> {
    return this.http.get<AcudienteDetalle>(
      `${this.API}/acudientes/vinculo/${vinculoId}`,
    );
  }
  editar(
    vinculoId: string,
    d: EditarAcudienteRequest,
  ): Observable<{ mensaje: string }> {
    return this.http.put<{ mensaje: string }>(
      `${this.API}/acudientes/vinculo/${vinculoId}`,
      d,
    );
  }
  desvincular(vinculoId: string): Observable<{ mensaje: string }> {
    return this.http.delete<{ mensaje: string }>(
      `${this.API}/acudientes/vinculo/${vinculoId}`,
    );
  }
}
