import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface StaffRequest {
  rolCodigo: string;
  tipoDocumento: string;
  numeroDocumento: string;
  primerNombre: string;
  primerApellido: string;
  correo: string;
}

export interface StaffDetalle {
  usuarioId: string; username: string; emailLogin: string; estado: string;
  rolCodigo: string; rolNombre: string;
  tipoDocumento: string; numeroDocumento: string;
  primerNombre: string; segundoNombre?: string; primerApellido: string; segundoApellido?: string;
  correo: string; telefono?: string; telefonoAlternativo?: string;
  fechaNacimiento?: string; sexo?: string; nacionalidad?: string;
  direccion?: string; barrio?: string; ciudad?: string; departamento?: string; pais?: string;
  fotoUrl?: string; observaciones?: string;
}

export interface EditarStaffRequest {
  rolCodigo: string; tipoDocumento: string; numeroDocumento: string;
  primerNombre: string; segundoNombre?: string; primerApellido: string; segundoApellido?: string;
  correo: string; telefono?: string; telefonoAlternativo?: string;
  fechaNacimiento?: string; sexo?: string; nacionalidad?: string;
  direccion?: string; barrio?: string; ciudad?: string; departamento?: string; pais?: string;
  observaciones?: string;
}

export interface Staff {
  usuarioId: string;
  username: string;
  emailLogin: string;
  estado: string;
  primerNombre: string;
  primerApellido: string;
  numeroDocumento: string;
  correo: string;
  rolCodigo: string;
  rolNombre: string;
  ultimoLogin?: string;
  createdAt?: string;
}

@Injectable({ providedIn: 'root' })
export class StaffService {

  private readonly API = 'http://localhost:8080/api/staff';

  constructor(private http: HttpClient) {}

  listar(): Observable<Staff[]> {
    return this.http.get<Staff[]>(this.API);
  }

  crear(datos: StaffRequest): Observable<{ mensaje: string }> {
    return this.http.post<{ mensaje: string }>(this.API, datos);
  }

  obtener(id: string): Observable<StaffDetalle> {
    return this.http.get<StaffDetalle>(`${this.API}/${id}`);
  }

  editar(id: string, datos: EditarStaffRequest): Observable<{ mensaje: string }> {
    return this.http.put<{ mensaje: string }>(`${this.API}/${id}`, datos);
  }

  activar(id: string): Observable<{ mensaje: string }> {
    return this.http.patch<{ mensaje: string }>(`${this.API}/${id}/activar`, {});
  }
  inactivar(id: string): Observable<{ mensaje: string }> {
    return this.http.patch<{ mensaje: string }>(`${this.API}/${id}/inactivar`, {});
  }
}
