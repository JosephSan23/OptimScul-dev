import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment.development';
import { MisNotasVista } from './estudiante.service';
import { MiHorarioVista, AsistenciaMateria } from './estudiante.service';

export interface Hijo {
  estudianteId: string;
  nombre: string;
  codigoEstudiante: string;
  numeroDocumento: string;
}

@Injectable({ providedIn: 'root' })
export class AcudienteService {
  private readonly API = `${environment.apiUrl}/acudiente`;
  constructor(private http: HttpClient) {}
  hijos(): Observable<Hijo[]> {
    return this.http.get<Hijo[]>(`${this.API}/hijos`);
  }
  notasHijo(
    estudianteId: string,
    anioId: string,
    periodoId: string,
  ): Observable<MisNotasVista> {
    return this.http.get<MisNotasVista>(
      `${this.API}/hijos/${estudianteId}/notas?anioId=${anioId}&periodoId=${periodoId}`,
    );
  }

  horarioHijo(estudianteId: string, anioId: string): Observable<MiHorarioVista> {
    return this.http.get<MiHorarioVista>(`${this.API}/hijos/${estudianteId}/horario?anioId=${anioId}`);
  }
  asistenciaHijo(estudianteId: string, anioId: string): Observable<AsistenciaMateria[]> {
    return this.http.get<AsistenciaMateria[]>(`${this.API}/hijos/${estudianteId}/asistencia?anioId=${anioId}`);
  }
}
