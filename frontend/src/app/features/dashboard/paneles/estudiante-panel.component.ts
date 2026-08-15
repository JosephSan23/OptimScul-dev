import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { forkJoin } from 'rxjs';

import { AuthService } from '../../../core/services/auth.service';
import { EstudianteService as EstudianteRolService, MisNotasVista, MiHorarioVista, AsistenciaMateria, FranjaHorario } from '../../../core/services/rol/estudiante.service';
import { ActividadEstudianteService, MiActividad } from '../../../core/services/rol/estudiante/actividad-estudiante.service';
import { AnioLectivoService, AnioLectivo } from '../../../core/services/anio-lectivo.service';
import { PeriodoService, Periodo } from '../../../core/services/periodo.service';

import { KpiCardComponent } from '../shared/kpi-card.component';
import { BarChartComponent, BarItem } from '../shared/bar-chart.component';
import { DonutChartComponent, DonutItem } from '../shared/donut-chart.component';

@Component({
  selector: 'app-estudiante-panel',
  standalone: true,
  imports: [CommonModule, RouterModule, KpiCardComponent, BarChartComponent, DonutChartComponent],
  templateUrl: './estudiante-panel.component.html',
  styleUrls: ['./panel.shared.scss'],
})
export class EstudiantePanelComponent implements OnInit {
  usuario = this.auth.getUsuarioActual();
  cargando = true;
  error = '';

  anioActual: AnioLectivo | null = null;
  periodoActual: Periodo | null = null;
  notas: MisNotasVista | null = null;
  horario: MiHorarioVista | null = null;
  asistencia: AsistenciaMateria[] = [];
  actividades: MiActividad[] = [];

  notasPorMateria: BarItem[] = [];
  asistenciaGlobal: DonutItem[] = [];
  proximasActividades: MiActividad[] = [];
  horarioHoy: FranjaHorario[] = [];

  readonly NOMBRE_DIA: Record<string, string> = {
    LUNES: 'Lunes', MARTES: 'Martes', MIERCOLES: 'Miércoles',
    JUEVES: 'Jueves', VIERNES: 'Viernes', SABADO: 'Sábado', DOMINGO: 'Domingo',
  };

  constructor(
    private auth: AuthService,
    private estudianteService: EstudianteRolService,
    private actividadService: ActividadEstudianteService,
    private anioService: AnioLectivoService,
    private periodoService: PeriodoService,
  ) {}

  ngOnInit(): void {
    this.anioService.listar().subscribe({
      next: (anios) => {
        this.anioActual = (anios || []).find(a => a.esActual) ?? (anios || [])[0] ?? null;
        if (!this.anioActual) { this.cargando = false; return; }
        this.periodoService.listarPorAnio(this.anioActual.id).subscribe({
          next: (periodos) => {
            this.periodoActual = (periodos || [])[0] ?? null;
            this.cargarDatos();
          },
          error: () => { this.cargarDatos(); },
        });
      },
      error: () => {
        this.error = 'No se pudieron cargar los años lectivos.';
        this.cargando = false;
      },
    });
  }

  private cargarDatos(): void {
    const anioId = this.anioActual!.id;
    const periodoId = this.periodoActual?.id;
    forkJoin({
      notas: periodoId
        ? this.estudianteService.misNotas(anioId, periodoId)
        : Promise.resolve(null as unknown as MisNotasVista),
      horario: this.estudianteService.miHorario(anioId),
      asistencia: this.estudianteService.miAsistencia(anioId),
      actividades: this.actividadService.misActividades(anioId),
    }).subscribe({
      next: (r) => {
        this.notas = r.notas;
        this.horario = r.horario;
        this.asistencia = r.asistencia || [];
        this.actividades = r.actividades || [];
        this.calcular();
        this.cargando = false;
      },
      error: () => {
        this.error = 'No se pudo cargar tu información.';
        this.cargando = false;
      },
    });
  }

  private calcular(): void {
    // Notas por materia (solo con nota registrada)
    this.notasPorMateria = (this.notas?.materias || [])
      .filter(m => m.notaFinal != null)
      .map(m => ({
        label: m.asignaturaNombre,
        value: m.notaFinal as number,
        color: m.aprueba ? '#2E9E6B' : '#CC4B3D',
      }));

    // Asistencia global
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

    // Próximas actividades pendientes
    this.proximasActividades = this.actividades
      .filter(x => x.estadoEntrega === 'PENDIENTE' || x.estadoEntrega === 'NO_ENTREGADA')
      .sort((x, y) => (x.fechaEntrega || '').localeCompare(y.fechaEntrega || ''))
      .slice(0, 6);

    // Horario de hoy
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
  get pendientes(): number {
    return this.actividades.filter(x => x.estadoEntrega === 'PENDIENTE' || x.estadoEntrega === 'NO_ENTREGADA').length;
  }

  claseActividad(estado: string): string {
    if (estado === 'CALIFICADA' || estado === 'ENTREGADA') return 'estado-activo';
    if (estado === 'ENTREGADA_TARDE') return 'estado-pendiente_activacion';
    return 'estado-inactivo';
  }
  etiquetaActividad(estado: string): string {
    const m: Record<string, string> = {
      PENDIENTE: 'Pendiente', ENTREGADA: 'Entregada',
      ENTREGADA_TARDE: 'Entregada tarde', NO_ENTREGADA: 'No entregada', CALIFICADA: 'Calificada',
    };
    return m[estado] ?? estado;
  }
}
