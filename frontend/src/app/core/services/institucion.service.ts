import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment.development';


// El "molde" de una institución tal como la devuelve tu API
export interface Institucion {
  id: string;
  codigo: string;
  nombre: string;
  nombreCorto?: string;
  tipoInstitucion: string;
  nit?: string;
  dane?: string;
  resolucionFuncionamiento?: string;
  descripcion?: string;
  correoContacto?: string;
  telefonoContacto?: string;
  sitioWeb?: string;
  dominioCorreo?: string;
  direccionPrincipal?: string;
  ciudad?: string;
  departamento?: string;
  pais?: string;
  logoUrl?: string;
  zonaHoraria?: string;
  moneda?: string;
  estado: string;
}

// Lo que enviamos al crear o editar (sin id, estado ni campos que pone el sistema)
export interface InstitucionRequest {
  codigo: string;
  nombre: string;
  nombreCorto?: string;
  tipoInstitucion: string;
  nit?: string;
  dane?: string;
  resolucionFuncionamiento?: string;
  descripcion?: string;
  correoContacto?: string;
  telefonoContacto?: string;
  sitioWeb?: string;
  dominioCorreo?: string;
  direccionPrincipal?: string;
  ciudad?: string;
  departamento?: string;
  pais?: string;
  logoUrl?: string;
  zonaHoraria?: string;
  moneda?: string;
}

@Injectable({ providedIn: 'root' })
export class InstitucionService {

  private readonly API = `${environment.apiUrl}/instituciones`;

  constructor(private http: HttpClient) {}

  listar(): Observable<Institucion[]> {
    return this.http.get<Institucion[]>(this.API);
  }

  crear(datos: InstitucionRequest): Observable<Institucion> {
    return this.http.post<Institucion>(this.API, datos);
  }

  editar(id: string, datos: InstitucionRequest): Observable<Institucion> {
    return this.http.put<Institucion>(`${this.API}/${id}`, datos);
  }

  suspender(id: string): Observable<Institucion> {
    return this.http.patch<Institucion>(`${this.API}/${id}/suspender`, {});
  }

  activar(id: string): Observable<Institucion> {
    return this.http.patch<Institucion>(`${this.API}/${id}/activar`, {});
  }

  reactivar(id: string): Observable<Institucion> {
    return this.http.patch<Institucion>(`${this.API}/${id}/reactivar`, {});
  }
}
