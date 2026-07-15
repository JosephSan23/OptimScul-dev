import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment.development';



export interface InstitucionConfig {
  codigo: string;
  tipoInstitucion: string;
  estado: string;
  dominioCorreo?: string;
  nombre: string;
  nombreCorto?: string;
  descripcion?: string;
  nit?: string;
  dane?: string;
  resolucionFuncionamiento?: string;
  correoContacto?: string;
  telefonoContacto?: string;
  sitioWeb?: string;
  direccionPrincipal?: string;
  ciudad?: string;
  departamento?: string;
  pais?: string;
  zonaHoraria?: string;
  moneda?: string;
}
export interface InstitucionConfigRequest {
  nombre: string;
  nombreCorto?: string;
  descripcion?: string;
  nit?: string;
  dane?: string;
  resolucionFuncionamiento?: string;
  correoContacto?: string;
  telefonoContacto?: string;
  sitioWeb?: string;
  direccionPrincipal?: string;
  ciudad?: string;
  departamento?: string;
  pais?: string;
  zonaHoraria?: string;
  moneda?: string;
}

@Injectable({ providedIn: 'root' })
export class InstitucionConfigService {
  private readonly API = `${environment.apiUrl}/config/institucion`;
  constructor(private http: HttpClient) {}

  miInstitucion(): Observable<InstitucionConfig> {
    return this.http.get<InstitucionConfig>(this.API);
  }
  guardar(datos: InstitucionConfigRequest): Observable<InstitucionConfig> {
    return this.http.put<InstitucionConfig>(this.API, datos);
  }
}
