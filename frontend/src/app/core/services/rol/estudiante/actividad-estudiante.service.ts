import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../../environments/environment.development';

export interface MiActividad {
  actividadId: string;
  titulo: string;
  tipo: string;
  fechaEntrega: string | null;
  fechaCierre: string | null;
  notaMaxima: number | null;
  asignatura: string;
  cargaId: string;
  estadoEntrega: string;   // PENDIENTE | ENTREGADA | ENTREGADA_TARDE | NO_ENTREGADA | CALIFICADA
  notaObtenida: number | null;
}

export interface DocumentoEntrega {
  id: string;
  nombreOriginal: string;
  mimeType: string;
  tamanoBytes: number;
  urlDescarga: string;
}

export interface MiEntrega {
  entregaId: string | null;
  actividadId: string;
  estado: string;
  comentarioEstudiante: string | null;
  fechaEntrega: string | null;
  documentos: DocumentoEntrega[];
}

@Injectable({ providedIn: 'root' })
export class ActividadEstudianteService {

  private readonly API = `${environment.apiUrl}/estudiante/actividades`;

  constructor(private http: HttpClient) {}

  misActividades(anioId: string): Observable<MiActividad[]> {
    return this.http.get<MiActividad[]>(`${this.API}?anioId=${anioId}`);
  }

  miEntrega(actividadId: string): Observable<MiEntrega> {
    return this.http.get<MiEntrega>(`${this.API}/${actividadId}/entrega`);
  }

  entregar(actividadId: string, comentario: string, archivos: File[]): Observable<MiEntrega> {
    const fd = new FormData();
    if (comentario) fd.append('comentario', comentario);
    archivos.forEach(f => fd.append('archivos', f));
    // No seteamos Content-Type: el navegador arma el multipart; el interceptor añade el token.
    return this.http.post<MiEntrega>(`${this.API}/${actividadId}/entrega`, fd);
  }

  eliminarArchivo(actividadId: string, documentoId: string) {
    return this.http.delete<MiEntrega>(`${this.API}/${actividadId}/entrega/documentos/${documentoId}`);
  }
}
