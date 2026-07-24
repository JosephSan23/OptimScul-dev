import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment.development';

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
}
