import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment.development';

export interface MatrizSesion {
  sesionId: string;
  fecha: string;
}
export interface MatrizFila {
  estudianteId: string;
  codigoEstudiante: string;
  nombre: string;
  numeroDocumento: string;
  marcas: string[]; // alineadas a sesiones (mismo índice)
}
export interface MatrizAsistencia {
  sesiones: MatrizSesion[];
  estudiantes: MatrizFila[];
}

@Injectable({ providedIn: 'root' })
export class ReporteService {
  private readonly API = `${environment.apiUrl}/academico/reportes`;
  constructor(private http: HttpClient) {}
  matrizClase(cargaId: string): Observable<MatrizAsistencia> {
    return this.http.get<MatrizAsistencia>(
      `${this.API}/matriz-asistencia/${cargaId}`,
    );
  }
}
