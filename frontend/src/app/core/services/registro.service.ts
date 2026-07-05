import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface RegistroRequest {
  tipoDocumento: string;
  numeroDocumento: string;
  primerNombre: string;
  primerApellido: string;
  correo: string;
  password: string;
}

@Injectable({ providedIn: 'root' })
export class RegistroService {

  private readonly API = 'http://localhost:8080/api/auth/registro';

  constructor(private http: HttpClient) {}

  registrar(datos: RegistroRequest): Observable<{ mensaje: string }> {
    return this.http.post<{ mensaje: string }>(this.API, datos);
  }
}
