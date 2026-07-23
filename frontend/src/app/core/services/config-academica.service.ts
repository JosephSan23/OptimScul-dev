import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment.development';

export interface ConfigAcademica {
  id?: string;
  usaPeriodos: boolean;
  numeroPeriodos: number;
  notaMinimaAprobacion: number;
  notaMinima: number;
  notaMaxima: number;
  decimalesNota: number;
  usaRecuperacion: boolean;
  asistenciaPorClase: boolean;
  manejaComportamiento: boolean;
  manejaPuestos: boolean;
  porcentajeInasistenciaReprobacion?: number | null;
}

@Injectable({ providedIn: 'root' })
export class ConfigAcademicaService {
  private readonly API = `${environment.apiUrl}/academico/configuracion`;
  constructor(private http: HttpClient) {}
  obtener(): Observable<ConfigAcademica> {
    return this.http.get<ConfigAcademica>(this.API);
  }
  guardar(d: ConfigAcademica): Observable<ConfigAcademica> {
    return this.http.put<ConfigAcademica>(this.API, d);
  }
}
