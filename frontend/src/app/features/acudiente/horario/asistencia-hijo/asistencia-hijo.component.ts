import { Component, OnInit } from '@angular/core';
import {
  AcudienteService,
  Hijo,
} from '../../../../core/services/rol/acudiente.service';
import { AsistenciaMateria } from '../../../../core/services/rol/estudiante.service';
import {
  AnioLectivoService,
  AnioLectivo,
} from '../../../../core/services/anio-lectivo.service';

@Component({
  selector: 'app-asistencia-hijo',
  templateUrl: './asistencia-hijo.component.html',
  styleUrls: ['./asistencia-hijo.component.scss'],
})
export class AsistenciaHijoComponent implements OnInit {
  hijos: Hijo[] = [];
  hijoSeleccionado = '';
  anios: AnioLectivo[] = [];
  anioSeleccionado = '';
  materias: AsistenciaMateria[] = [];
  cargando = false;
  error = '';

  constructor(
    private acudienteService: AcudienteService,
    private anioService: AnioLectivoService,
  ) {}

  ngOnInit(): void {
    this.acudienteService.hijos().subscribe({
      next: (h) => {
        this.hijos = h;
        if (h.length) this.hijoSeleccionado = h[0].estudianteId;
        this.anioService.listar().subscribe({
          next: (d) => {
            this.anios = d;
            const actual = d.find((a) => a.esActual) ?? d[0];
            if (actual) {
              this.anioSeleccionado = actual.id;
              this.cargar();
            }
          },
          error: () => {
            this.error = 'No se pudieron cargar los años lectivos.';
          },
        });
      },
      error: () => {
        this.error = 'No se pudieron cargar tus hijos.';
      },
    });
  }

  cambioHijo(): void {
    this.cargar();
  }
  cambioAnio(): void {
    this.cargar();
  }

  cargar(): void {
    if (!this.hijoSeleccionado || !this.anioSeleccionado) return;
    this.cargando = true;
    this.error = '';
    this.acudienteService
      .asistenciaHijo(this.hijoSeleccionado, this.anioSeleccionado)
      .subscribe({
        next: (d) => {
          this.materias = d;
          this.cargando = false;
        },
        error: () => {
          this.error = 'No se pudo cargar la asistencia.';
          this.cargando = false;
        },
      });
  }

  porcentaje(m: AsistenciaMateria): number {
    if (!m.total) return 0;
    return Math.round(((m.presente + m.tarde) / m.total) * 100);
  }
}
