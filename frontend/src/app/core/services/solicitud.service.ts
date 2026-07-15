import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment.development';

// Lo que devuelve la API (coincide con tu SolicitudInstitucionResponseDto)
export interface Solicitud {
  id: string;
  nombreColegio: string;
  nit?: string;
  ciudad?: string;
  direccion?: string;
  telefono?: string;
  nombreContacto: string;
  correo: string;
  mensaje?: string;
  estado: string;
  convertidaEnInstitucionId?: string;
  createdAt: string;
}

export interface SolicitudRequest {
  nombreColegio: string;
  nit?: string;
  ciudad?: string;
  direccion?: string;
  telefono?: string;
  nombreContacto: string;
  correo: string;
  mensaje?: string;
}



@Injectable({ providedIn: 'root' })
export class SolicitudService {

  private readonly API = `${environment.apiUrl}/solicitudes`;

  constructor(private http: HttpClient) {}

  listar(): Observable<Solicitud[]> {
    return this.http.get<Solicitud[]>(this.API);
  }

  crear(datos: SolicitudRequest): Observable<Solicitud> {
    return this.http.post<Solicitud>(this.API, datos);
  }

  aprobar(id: string): Observable<Solicitud> {
    return this.http.patch<Solicitud>(`${this.API}/${id}/aprobar`, {});
  }

  rechazar(id: string, motivo: string): Observable<Solicitud> {
    return this.http.patch<Solicitud>(`${this.API}/${id}/rechazar`, { motivo });
  }

  misSolicitudes(): Observable<Solicitud[]> {
    return this.http.get<Solicitud[]>(`${this.API}/mias`);
  }
}
