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
}
