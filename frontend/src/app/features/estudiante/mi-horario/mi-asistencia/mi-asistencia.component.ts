import { Component, OnInit } from '@angular/core';
import {
  EstudianteService,
  AsistenciaMateria,
} from '../../../../core/services/rol/estudiante.service';
import {
  AnioLectivoService,
  AnioLectivo,
} from '../../../../core/services/anio-lectivo.service';

@Component({
  selector: 'app-mi-asistencia',
  templateUrl: './mi-asistencia.component.html',
  styleUrls: ['./mi-asistencia.component.scss'],
})
export class MiAsistenciaComponent implements OnInit {
  anios: AnioLectivo[] = [];
  anioSeleccionado = '';
  materias: AsistenciaMateria[] = [];
  cargando = false;
  error = '';

  constructor(
    private estudianteService: EstudianteService,
    private anioService: AnioLectivoService,
  ) {}

  ngOnInit(): void {
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
  }

  cambioAnio(): void {
    this.cargar();
  }

  cargar(): void {
    if (!this.anioSeleccionado) return;
    this.cargando = true;
    this.error = '';
    this.estudianteService.miAsistencia(this.anioSeleccionado).subscribe({
      next: (d) => {
        this.materias = d;
        this.cargando = false;
      },
      error: () => {
        this.error = 'No se pudo cargar tu asistencia.';
        this.cargando = false;
      },
    });
  }

  porcentaje(m: AsistenciaMateria): number {
    if (!m.total) return 0;
    return Math.round(((m.presente + m.tarde) / m.total) * 100);
  }
}
