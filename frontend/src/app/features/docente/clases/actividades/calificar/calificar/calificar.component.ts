import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import {
  ActividadService,
  NotaEstudiante,
} from '../../../../../../core/services/actividad.service';

@Component({
  selector: 'app-calificar',
  templateUrl: './calificar.component.html',
  styleUrls: ['./calificar.component.scss'],
})
export class CalificarComponent implements OnInit {
  actividadId!: string;
  anio = '';
  cargaId = '';
  titulo = '';
  notaMaxima = 5;
  estado = '';
  notas: NotaEstudiante[] = [];
  cargando = false;
  guardando = false;
  error = '';
  exito = '';

  constructor(
    private actividadService: ActividadService,
    private route: ActivatedRoute,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.actividadId = this.route.snapshot.paramMap.get('id')!;
    this.anio = this.route.snapshot.queryParamMap.get('anio') ?? '';
    this.cargaId = this.route.snapshot.queryParamMap.get('carga') ?? '';
    this.cargar();
  }

  cargar(): void {
    this.cargando = true;
    this.error = '';
    this.exito = '';
    this.actividadService.obtenerCalificaciones(this.actividadId).subscribe({
      next: (v) => {
        this.titulo = v.titulo;
        this.notaMaxima = v.notaMaxima;
        this.estado = v.estado;
        this.notas = v.estudiantes;
        this.cargando = false;
      },
      error: (err) => {
        this.error =
          err?.status === 403
            ? 'Esta actividad no te pertenece.'
            : 'No se pudieron cargar las calificaciones.';
        this.cargando = false;
      },
    });
  }

  get bloqueado(): boolean {
    return this.estado === 'ANULADA';
  }

  notaValida(n: NotaEstudiante): boolean {
    return (
      n.notaObtenida == null ||
      (n.notaObtenida >= 0 && n.notaObtenida <= this.notaMaxima)
    );
  }

  get hayErrores(): boolean {
    return this.notas.some((n) => !this.notaValida(n));
  }

  get calificados(): number {
    return this.notas.filter((n) => n.notaObtenida != null).length;
  }

  guardar(): void {
    this.error = '';
    this.exito = '';
    if (this.hayErrores) {
      this.error = `Hay notas fuera del rango 0 – ${this.notaMaxima}.`;
      return;
    }
    this.guardando = true;
    const body = {
      notas: this.notas.map((n) => ({
        estudianteId: n.estudianteId,
        notaObtenida: n.notaObtenida,
        observacion: n.observacion ?? null,
      })),
    };
    this.actividadService
      .guardarCalificaciones(this.actividadId, body)
      .subscribe({
        next: () => {
          this.guardando = false;
          this.exito = 'Calificaciones guardadas.';
        },
        error: (err) => {
          this.guardando = false;
          this.error = err?.error?.mensaje || 'No se pudo guardar.';
        },
      });
  }

  volver(): void {
    this.router.navigate(
      ['/dashboard/mis-clases', this.cargaId, 'actividades'],
      { queryParams: { anio: this.anio } },
    );
  }
}
