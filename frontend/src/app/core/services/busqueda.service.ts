import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

export interface EstudianteResultado {
  id: string;
  nombre: string;
  codigo: string;
  documento: string;
}
export interface PersonalResultado {
  id: string;
  nombre: string;
  rol: string;
  documento: string;
}
export interface AsignaturaResultado {
  id: string;
  nombre: string;
  codigo: string;
}
export interface ResultadoBusqueda {
  estudiantes: EstudianteResultado[];
  personal: PersonalResultado[];
  asignaturas: AsignaturaResultado[];
}
export interface MiInstitucion {
  id: string;
  nombre: string;
  nombreCorto: string | null;
}

@Injectable({ providedIn: 'root' })
export class BusquedaService {
  private readonly API = environment.apiUrl;

  constructor(private http: HttpClient) {}

  /** Institución del usuario autenticado (para el nombre de la cabecera). */
  miInstitucion(): Observable<MiInstitucion> {
    return this.http.get<MiInstitucion>(`${this.API}/mi-institucion`);
  }

  /** Buscador global: estudiantes y asignaturas de mi institución. */
  buscar(q: string): Observable<ResultadoBusqueda> {
    const params = new HttpParams().set('q', q);
    return this.http.get<ResultadoBusqueda>(`${this.API}/busqueda`, { params });
  }
}
