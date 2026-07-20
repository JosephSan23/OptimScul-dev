import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment.development';

export interface HorarioResumen {
  id: string;
  cargaAcademicaId: string;
  diaSemana: string;
  horaInicio: string;
  horaFin: string;
  aula?: string;
  activo: boolean;
  asignaturaNombre: string;
  profesorNombre: string;
  sedeNombre?: string;
}
export interface HorarioRequest {
  cargaAcademicaId: string;
  sedeId?: string | null;
  diaSemana: string;
  horaInicio: string;
  horaFin: string;
  aula?: string;
}

@Injectable({ providedIn: 'root' })
export class HorarioService {
  private readonly API = `${environment.apiUrl}/academico/horarios`;
  constructor(private http: HttpClient) {}
  listarPorGrupo(grupoId: string): Observable<HorarioResumen[]> {
    return this.http.get<HorarioResumen[]>(`${this.API}/por-grupo/${grupoId}`);
  }
  obtener(id: string): Observable<any> {
    return this.http.get(`${this.API}/${id}`);
  }
  crear(d: HorarioRequest): Observable<any> {
    return this.http.post(this.API, d);
  }
  editar(id: string, d: HorarioRequest): Observable<any> {
    return this.http.put(`${this.API}/${id}`, d);
  }
  activar(id: string): Observable<{ mensaje: string }> {
    return this.http.patch<{ mensaje: string }>(
      `${this.API}/${id}/activar`,
      {},
    );
  }
  inactivar(id: string): Observable<{ mensaje: string }> {
    return this.http.patch<{ mensaje: string }>(
      `${this.API}/${id}/inactivar`,
      {},
    );
  }
}
