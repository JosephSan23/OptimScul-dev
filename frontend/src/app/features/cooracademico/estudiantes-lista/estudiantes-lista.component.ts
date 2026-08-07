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

  // ── Envío de credenciales por correo ──
  enviandoId: string | null = null;
  feedback = '';
  feedbackOk = false;

  enviarCredenciales(e: Estudiante): void {
    if (!e.correoAlcanzable) return;
    this.enviandoId = e.estudianteId;
    this.feedback = '';
    this.estudianteService.enviarCredenciales(e.estudianteId).subscribe({
      next: (r) => {
        this.enviandoId = null;
        this.feedback = r.mensaje;
        this.feedbackOk = r.enviado;
      },
      error: (err) => {
        this.enviandoId = null;
        this.feedbackOk = false;
        this.feedback =
          err?.error?.mensaje || 'No se pudieron enviar las credenciales.';
      },
    });
  }
}
