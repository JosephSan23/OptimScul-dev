import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';

export interface MateriaNota {
  asignaturaNombre: string;
  profesorNombre: string;
  notaFinal: number | null;
  aprueba: boolean;
}
export interface MisNotasVista {
  matriculado: boolean;
  gradoNombre?: string;
  grupoNombre?: string;
  promedio: number | null;
  notaAprobacion: number;
  materias: MateriaNota[];
  institucionNombre: string;
  estudianteNombre: string;
  boletinHabilitado: boolean;
}

export interface FranjaHorario {
  id: string;
  diaSemana: string;
  horaInicio: string;
  horaFin: string;
  aula?: string;
  asignaturaNombre: string;
  profesorNombre: string;
  sedeNombre?: string;
}
export interface MiHorarioVista {
  matriculado: boolean;
  franjas: FranjaHorario[];
}
export interface AsistenciaMateria {
  asignaturaNombre: string;
  presente: number;
  ausente: number;
  tarde: number;
  justificada: number;
  total: number;
}

@Injectable({ providedIn: 'root' })
export class EstudianteService {
  private readonly API = `${environment.apiUrl}/estudiante`;
  constructor(private http: HttpClient) {}
  misNotas(anioId: string, periodoId: string): Observable<MisNotasVista> {
    return this.http.get<MisNotasVista>(
      `${this.API}/mis-notas?anioId=${anioId}&periodoId=${periodoId}`,
    );
  }

  miHorario(anioId: string): Observable<MiHorarioVista> {
    return this.http.get<MiHorarioVista>(`${this.API}/mi-horario?anioId=${anioId}`);
  }
  miAsistencia(anioId: string): Observable<AsistenciaMateria[]> {
    return this.http.get<AsistenciaMateria[]>(`${this.API}/mi-asistencia?anioId=${anioId}`);
  }
}
