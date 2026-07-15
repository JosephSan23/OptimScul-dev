import { Component, OnInit } from '@angular/core';
import {
  EstudianteService,
  Estudiante,
} from '../../../core/services/estudiante.service';

@Component({
  selector: 'app-estudiantes-lista',
  templateUrl: './estudiantes-lista.component.html',
  styleUrls: ['./estudiantes-lista.component.scss'],
})
export class EstudiantesListaComponent implements OnInit {
  estudiantes: Estudiante[] = [];
  cargando = false;
  error = '';
  constructor(private estudianteService: EstudianteService) {}
  ngOnInit(): void {
    this.cargar();
  }
  cargar(): void {
    this.cargando = true;
    this.error = '';
    this.estudianteService.listar().subscribe({
      next: (d) => {
        this.estudiantes = d;
        this.cargando = false;
      },
      error: () => {
        this.error = 'No se pudieron cargar los estudiantes.';
        this.cargando = false;
      },
    });
  }

  procesandoId: string | null = null;
  activar(e: Estudiante): void {
    this.procesandoId = e.estudianteId;
    this.estudianteService.activar(e.estudianteId).subscribe({
      next: () => {
        this.procesandoId = null;
        this.cargar();
      },
      error: () => {
        this.procesandoId = null;
        this.error = 'No se pudo activar.';
      },
    });
  }
  inactivar(e: Estudiante): void {
    this.procesandoId = e.estudianteId;
    this.estudianteService.inactivar(e.estudianteId).subscribe({
      next: () => {
        this.procesandoId = null;
        this.cargar();
      },
      error: () => {
        this.procesandoId = null;
        this.error = 'No se pudo inactivar.';
      },
    });
  }
}
