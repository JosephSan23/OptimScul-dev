import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { MatrizAsistencia } from './reporte.service'
import { environment } from '../../../environments/environment.development';

export interface MiClase {
  cargaId: string;
  grupoId: string;
  anioLectivoId: string;
  asignaturaNombre: string;
  asignaturaCodigo: string;
  grupoNombre: string;
  grupoCodigo: string;
  gradoNombre: string;
  intensidadHorariaSemanal?: number;
  totalEstudiantes: number;
}
export interface MiFranja {
  id: string;
  cargaAcademicaId: string;
  diaSemana: string;
  horaInicio: string;
  horaFin: string;
  aula?: string;
  asignaturaNombre: string;
  sedeNombre?: string;
}
export interface EstudianteDeClase {
  estudianteId: string;
  matriculaId: string;
  codigoEstudiante: string;
  nombre: string;
  numeroDocumento: string;
}

export interface AsistenciaMarca {
  estudianteId: string;
  codigoEstudiante: string;
  nombre: string;
  numeroDocumento: string;
  tipo: string;
  minutosTarde?: number | null;
  observacion?: string | null;
}
export interface AsistenciaVista {
  sesionId?: string;
  fecha: string;
  tema?: string;
  estudiantes: AsistenciaMarca[];
}

export interface ReporteAsistenciaFila {
  estudianteId: string;
  nombre: string;
  codigoEstudiante: string;
  presente: number;
  ausente: number;
  tarde: number;
  justificada: number;
  totalRegistros: number;
}

@Injectable({ providedIn: 'root' })
export class DocenteService {
  private readonly API = `${environment.apiUrl}/docente`;
  constructor(private http: HttpClient) {}

  misClases(anioId: string): Observable<MiClase[]> {
    return this.http.get<MiClase[]>(`${this.API}/mis-clases/${anioId}`);
  }
  miHorario(anioId: string): Observable<MiFranja[]> {
    return this.http.get<MiFranja[]>(`${this.API}/mi-horario/${anioId}`);
  }
  estudiantesDeClase(cargaId: string): Observable<EstudianteDeClase[]> {
    return this.http.get<EstudianteDeClase[]>(`${this.API}/clases/${cargaId}/estudiantes`);
  }

  obtenerAsistencia(cargaId: string, fecha: string): Observable<AsistenciaVista> {
    return this.http.get<AsistenciaVista>(`${this.API}/clases/${cargaId}/asistencia?fecha=${fecha}`);
  }
  guardarAsistencia(cargaId: string, body: any): Observable<{ mensaje: string }> {
    return this.http.post<{ mensaje: string }>(`${this.API}/clases/${cargaId}/asistencia`, body);
  }

  reporteAsistencia(cargaId: string): Observable<ReporteAsistenciaFila[]> {
    return this.http.get<ReporteAsistenciaFila[]>(`${this.API}/clases/${cargaId}/reporte-asistencia`);
  }

  matrizClase(cargaId: string): Observable<MatrizAsistencia> {
    return this.http.get<MatrizAsistencia>(`${this.API}/clases/${cargaId}/matriz-asistencia`);
  }
}
