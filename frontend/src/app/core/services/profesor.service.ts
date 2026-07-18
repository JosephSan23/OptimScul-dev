import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment.development';

export interface ProfesorResumen {
  id: string;
  nombre: string;
  codigoProfesor: string;
  especialidad?: string;
  estado: string;
}

@Injectable({ providedIn: 'root' })
export class ProfesorService {
  private readonly API = `${environment.apiUrl}/academico/profesores`;
  constructor(private http: HttpClient) {}
  listar(): Observable<ProfesorResumen[]> {
    return this.http.get<ProfesorResumen[]>(this.API);
  }
}
