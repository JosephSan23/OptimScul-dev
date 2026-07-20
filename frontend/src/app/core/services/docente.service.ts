import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
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
}
