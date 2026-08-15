import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { forkJoin, of } from 'rxjs';

import { AuthService } from '../../../core/services/auth.service';
import { AcudienteService, Hijo } from '../../../core/services/rol/acudiente.service';
import { MisNotasVista, MiHorarioVista, AsistenciaMateria, FranjaHorario } from '../../../core/services/rol/estudiante.service';
import { AnioLectivoService, AnioLectivo } from '../../../core/services/anio-lectivo.service';
import { PeriodoService, Periodo } from '../../../core/services/periodo.service';

import { KpiCardComponent } from '../shared/kpi-card.component';
import { BarChartComponent, BarItem } from '../shared/bar-chart.component';
import { DonutChartComponent, DonutItem } from '../shared/donut-chart.component';

interface ResumenHijo {
  estudianteId: string;
  nombre: string;
  grado?: string;
  grupo?: string;
  promedio: number | null;
  notaAprobacion: number | null;
  matriculado: boolean;
}

@Component({
  selector: 'app-acudiente-panel',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule, KpiCardComponent, BarChartComponent, DonutChartComponent],
  templateUrl: './acudiente-panel.component.html',
  styleUrls: ['./panel.shared.scss'],
})
export class AcudientePanelComponent implements OnInit {
  usuario = this.auth.getUsuarioActual();
  cargando = true;
  cargandoHijo = false;
  error = '';

  anioActual: AnioLectivo | null = null;
  periodoActual: Periodo | null = null;

  hijos: Hijo[] = [];
  hijoSeleccionado = '';
  resumenHijos: ResumenHijo[] = [];

  notas: MisNotasVista | null = null;
  asistencia: AsistenciaMateria[] = [];
  horario: MiHorarioVista | null = null;

  notasPorMateria: BarItem[] = [];
  asistenciaGlobal: DonutItem[] = [];
  horarioHoy: FranjaHorario[] = [];

  constructor(
    private auth: AuthService,
    private acudienteService: AcudienteService,
    private anioService: AnioLectivoService,
    private periodoService: PeriodoService,
  ) {}

  ngOnInit(): void {
    this.acudienteService.hijos().subscribe({
      next: (hijos) => {
        this.hijos = hijos || [];
        if (this.hijos.length === 0) { this.cargando = false; return; }
        this.hijoSeleccionado = this.hijos[0].estudianteId;
        this.anioService.listar().subscribe({
          next: (anios) => {
            this.anioActual = (anios || []).find(a => a.esActual) ?? (anios || [])[0] ?? null;
            if (!this.anioActual) { this.cargando = false; return; }
            this.periodoService.listarPorAnio(this.anioActual.id).subscribe({
              next: (periodos) => {
                this.periodoActual = (periodos || [])[0] ?? null;
                this.construirResumen();
                this.cargarHijo();
                this.cargando = false;
              },
              error: () => { this.cargando = false; },
            });
          },
          error: () => {
            this.error = 'No se pudieron cargar los años lectivos.';
            this.cargando = false;
          },
        });
      },
      error: () => {
        this.error = 'No se pudo cargar la información de tus hijos.';
        this.cargando = false;
      },
    });
  }

  private construirResumen(): void {
    if (!this.anioActual || !this.periodoActual) {
      this.resumenHijos = this.hijos.map(h => ({
        estudianteId: h.estudianteId, nombre: h.nombre,
        promedio: null, notaAprobacion: null, matriculado: false,
      }));
      return;
    }
    const anioId = this.anioActual.id;
    const periodoId = this.periodoActual.id;
    forkJoin(
      this.hijos.map(h =>
        this.acudienteService.notasHijo(h.estudianteId, anioId, periodoId)
      )
    ).subscribe({
      next: (vistas) => {
        this.resumenHijos = this.hijos.map((h, i) => {
          const v = vistas[i];
          return {
            estudianteId: h.estudianteId,
            nombre: h.nombre,
            grado: v?.gradoNombre,
            grupo: v?.grupoNombre,
            promedio: v?.promedio ?? null,
            notaAprobacion: v?.notaAprobacion ?? null,
            matriculado: !!v?.matriculado,
          };
        });
      },
      error: () => {},
    });
  }

  cambioHijo(): void {
    this.cargarHijo();
  }

  private cargarHijo(): void {
    if (!this.hijoSeleccionado || !this.anioActual) return;
    const anioId = this.anioActual.id;
    const periodoId = this.periodoActual?.id;
    this.cargandoHijo = true;
    forkJoin({
      notas: periodoId
        ? this.acudienteService.notasHijo(this.hijoSeleccionado, anioId, periodoId)
        : of(null as unknown as MisNotasVista),
      asistencia: this.acudienteService.asistenciaHijo(this.hijoSeleccionado, anioId),
      horario: this.acudienteService.horarioHijo(this.hijoSeleccionado, anioId),
    }).subscribe({
      next: (r) => {
        this.notas = r.notas;
        this.asistencia = r.asistencia || [];
        this.horario = r.horario;
        this.calcular();
        this.cargandoHijo = false;
      },
      error: () => {
        this.error = 'No se pudo cargar la información del hijo seleccionado.';
        this.cargandoHijo = false;
      },
    });
  }

  private calcular(): void {
    this.notasPorMateria = (this.notas?.materias || [])
      .filter(m => m.notaFinal != null)
      .map(m => ({
        label: m.asignaturaNombre,
        value: m.notaFinal as number,
        color: m.aprueba ? '#2E9E6B' : '#CC4B3D',
      }));

    let p = 0, a = 0, t = 0, j = 0;
    for (const m of this.asistencia) {
      p += m.presente || 0; a += m.ausente || 0; t += m.tarde || 0; j += m.justificada || 0;
    }
    this.asistenciaGlobal = [
      { label: 'Presente', value: p, color: '#2E9E6B' },
      { label: 'Tarde', value: t, color: '#E0A82E' },
      { label: 'Ausente', value: a, color: '#CC4B3D' },
      { label: 'Justificada', value: j, color: '#2F6FB4' },
    ].filter(x => x.value > 0);

    const hoy = this.diaDeHoy();
    this.horarioHoy = (this.horario?.franjas || [])
      .filter(f => f.diaSemana === hoy)
      .sort((x, y) => (x.horaInicio || '').localeCompare(y.horaInicio || ''));
  }

  private diaDeHoy(): string {
    const map = ['DOMINGO', 'LUNES', 'MARTES', 'MIERCOLES', 'JUEVES', 'VIERNES', 'SABADO'];
    return map[new Date().getDay()];
  }

  get promedio(): string {
    return this.notas?.promedio != null ? String(this.notas.promedio) : '—';
  }
  get totalMaterias(): number {
    return this.notas?.materias?.length || 0;
  }
  get asistenciaPct(): string {
    let p = 0, total = 0;
    for (const m of this.asistencia) { p += m.presente || 0; total += m.total || 0; }
    return total ? Math.round((p / total) * 100) + '%' : '—';
  }
  iniciales(nombre: string): string {
    const parts = (nombre || '').trim().split(/\s+/);
    return ((parts[0]?.[0] || '') + (parts[1]?.[0] || '')).toUpperCase();
  }
}
