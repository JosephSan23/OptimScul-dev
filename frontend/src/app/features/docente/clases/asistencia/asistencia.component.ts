import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import {
  DocenteService,
  AsistenciaMarca,
} from '../../../../core/services/docente.service';

@Component({
  selector: 'app-asistencia',
  templateUrl: './asistencia.component.html',
  styleUrls: ['./asistencia.component.scss'],
})
export class AsistenciaComponent implements OnInit {
  readonly ESTADOS = [
    {
      valor: 'PRESENTE',
      label: 'P',
      titulo: 'Presente',
      clase: 'est-presente',
    },
    { valor: 'AUSENTE', label: 'A', titulo: 'Ausente', clase: 'est-ausente' },
    { valor: 'TARDE', label: 'T', titulo: 'Tarde', clase: 'est-tarde' },
    {
      valor: 'JUSTIFICADA',
      label: 'J',
      titulo: 'Justificada',
      clase: 'est-justificada',
    },
  ];

  cargaId!: string;
  fecha = new Date().toISOString().substring(0, 10);
  tema = '';
  marcas: AsistenciaMarca[] = [];
  cargando = false;
  guardando = false;
  error = '';
  exito = '';

  constructor(
    private docenteService: DocenteService,
    private route: ActivatedRoute,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.cargaId = this.route.snapshot.paramMap.get('cargaId')!;
    this.cargar();
  }

  cargar(): void {
    this.cargando = true;
    this.error = '';
    this.exito = '';
    this.docenteService.obtenerAsistencia(this.cargaId, this.fecha).subscribe({
      next: (v) => {
        this.marcas = v.estudiantes;
        this.tema = v.tema ?? '';
        this.cargando = false;
      },
      error: (err) => {
        this.error =
          err?.status === 403
            ? 'Esta clase no te pertenece.'
            : 'No se pudo cargar la asistencia.';
        this.cargando = false;
      },
    });
  }

  cambioFecha(): void {
    this.cargar();
  }

  marcar(m: AsistenciaMarca, tipo: string): void {
    m.tipo = tipo;
  }

  todosPresente(): void {
    this.marcas.forEach((m) => (m.tipo = 'PRESENTE'));
  }

  conteo(tipo: string): number {
    return this.marcas.filter((m) => m.tipo === tipo).length;
  }

  guardar(): void {
    this.guardando = true;
    this.error = '';
    this.exito = '';
    const body = {
      fecha: this.fecha,
      tema: this.tema,
      marcas: this.marcas.map((m) => ({
        estudianteId: m.estudianteId,
        tipo: m.tipo,
        minutosTarde: m.minutosTarde ?? null,
      })),
    };
    this.docenteService.guardarAsistencia(this.cargaId, body).subscribe({
      next: () => {
        this.guardando = false;
        this.exito = 'Asistencia guardada.';
      },
      error: (err) => {
        this.guardando = false;
        this.error = err?.error?.mensaje || 'No se pudo guardar.';
      },
    });
  }

  volver(): void {
    this.router.navigate(['/dashboard/mis-clases', this.cargaId]);
  }
}
