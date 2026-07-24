import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment.development';

export interface Actividad {
  id: string;
  periodoAcademicoId: string;
  tipo: string;
  titulo: string;
  descripcion?: string;
  fechaEntrega?: string;
  fechaCierre?: string;
  porcentaje?: number;
  notaMaxima?: number;
  permiteEntregaTardia?: boolean;
  estado: string;
}
export interface ActividadRequest {
  periodoAcademicoId: string;
  tipo: string;
  titulo: string;
  descripcion?: string;
  fechaEntrega?: string | null;
  fechaCierre?: string | null;
  porcentaje?: number | null;
  notaMaxima?: number | null;
  permiteEntregaTardia?: boolean;
}

export interface NotaEstudiante {
  estudianteId: string;
  codigoEstudiante: string;
  nombre: string;
  notaObtenida: number | null;
  observacion?: string | null;
}
export interface CalificacionesVista {
  actividadId: string;
  titulo: string;
  notaMaxima: number;
  estado: string;
  estudiantes: NotaEstudiante[];
}

export interface ConsolidadoFila {
  estudianteId: string;
  codigoEstudiante: string;
  nombre: string;
  notaFinal: number | null;
  aprueba: boolean;
  calificadas: number;
  totalActividades: number;
}
export interface ConsolidadoVista {
  sumaPorcentajes: number;
  notaAprobacion: number;
  estudiantes: ConsolidadoFila[];
}

@Injectable({ providedIn: 'root' })
export class ActividadService {
  private readonly API = `${environment.apiUrl}/docente`;
  constructor(private http: HttpClient) {}

  listar(cargaId: string, periodoId: string): Observable<Actividad[]> {
    return this.http.get<Actividad[]>(
      `${this.API}/clases/${cargaId}/actividades?periodoId=${periodoId}`,
    );
  }
  obtener(id: string): Observable<Actividad> {
    return this.http.get<Actividad>(`${this.API}/actividades/${id}`);
  }
  crear(cargaId: string, d: ActividadRequest): Observable<Actividad> {
    return this.http.post<Actividad>(
      `${this.API}/clases/${cargaId}/actividades`,
      d,
    );
  }
  editar(id: string, d: ActividadRequest): Observable<Actividad> {
    return this.http.put<Actividad>(`${this.API}/actividades/${id}`, d);
  }
  publicar(id: string): Observable<{ mensaje: string }> {
    return this.http.patch<{ mensaje: string }>(
      `${this.API}/actividades/${id}/publicar`,
      {},
    );
  }
  cerrar(id: string): Observable<{ mensaje: string }> {
    return this.http.patch<{ mensaje: string }>(
      `${this.API}/actividades/${id}/cerrar`,
      {},
    );
  }
  anular(id: string): Observable<{ mensaje: string }> {
    return this.http.patch<{ mensaje: string }>(
      `${this.API}/actividades/${id}/anular`,
      {},
    );
  }

  obtenerCalificaciones(actividadId: string): Observable<CalificacionesVista> {
    return this.http.get<CalificacionesVista>(`${this.API}/actividades/${actividadId}/calificaciones`);
  }
  guardarCalificaciones(actividadId: string, body: any): Observable<{ mensaje: string }> {
    return this.http.post<{ mensaje: string }>(`${this.API}/actividades/${actividadId}/calificaciones`, body);
  }
  eliminarActividad(actividadId: string): Observable<{ mensaje: string }> {
    return this.http.delete<{ mensaje: string }>(`${this.API}/actividades/${actividadId}`);
  }

  consolidado(cargaId: string, periodoId: string): Observable<ConsolidadoVista> {
    return this.http.get<ConsolidadoVista>(`${this.API}/clases/${cargaId}/consolidado?periodoId=${periodoId}`);
  }
}
