import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { forkJoin } from 'rxjs';

import { AuthService } from '../../../core/services/auth.service';
import { EstudianteService, Estudiante } from '../../../core/services/estudiante.service';
import { GradoService, Grado } from '../../../core/services/grado.service';
import { AreaService, Area } from '../../../core/services/area.service';
import { AsignaturaService, Asignatura } from '../../../core/services/asignatura.service';
import { AnioLectivoService, AnioLectivo } from '../../../core/services/anio-lectivo.service';
import { MatriculaService, MatriculaResumen } from '../../../core/services/matricula.service';
import { CargaService, CargaResumen } from '../../../core/services/carga.service';
import { GrupoService, Grupo } from '../../../core/services/grupo.service';

import { KpiCardComponent } from '../shared/kpi-card.component';
import { BarChartComponent, BarItem } from '../shared/bar-chart.component';
import { DonutChartComponent, DonutItem } from '../shared/donut-chart.component';

@Component({
  selector: 'app-coordinador-panel',
  standalone: true,
  imports: [CommonModule, RouterModule, KpiCardComponent, BarChartComponent, DonutChartComponent],
  templateUrl: './coordinador-panel.component.html',
  styleUrls: ['./panel.shared.scss'],
})
export class CoordinadorPanelComponent implements OnInit {
  usuario = this.auth.getUsuarioActual();
  cargando = true;
  error = '';

  anioActual: AnioLectivo | null = null;
  estudiantes: Estudiante[] = [];
  grados: Grado[] = [];
  areas: Area[] = [];
  asignaturas: Asignatura[] = [];
  matriculas: MatriculaResumen[] = [];
  cargas: CargaResumen[] = [];
  grupos: Grupo[] = [];

  matriculasPorEstado: DonutItem[] = [];
  estudiantesPorGrado: BarItem[] = [];
  asignaturasPorArea: BarItem[] = [];
  matriculasRecientes: MatriculaResumen[] = [];

  accesos = [
    { label: 'Estudiantes',   icon: 'ti-users',           ruta: '/dashboard/estudiantes' },
    { label: 'Grados/grupos', icon: 'ti-stairs',          ruta: '/dashboard/grados' },
    { label: 'Áreas',         icon: 'ti-category',        ruta: '/dashboard/areas' },
    { label: 'Asignaturas',   icon: 'ti-book-2',          ruta: '/dashboard/asignaturas' },
    { label: 'Carga acad.',   icon: 'ti-chalkboard',      ruta: '/dashboard/cargas' },
    { label: 'Horarios',      icon: 'ti-calendar-time',   ruta: '/dashboard/horarios' },
    { label: 'Matrículas',    icon: 'ti-clipboard-check', ruta: '/dashboard/matriculas' },
  ];

  constructor(
    private auth: AuthService,
    private estudianteService: EstudianteService,
    private gradoService: GradoService,
    private areaService: AreaService,
    private asignaturaService: AsignaturaService,
    private anioService: AnioLectivoService,
    private matriculaService: MatriculaService,
    private cargaService: CargaService,
    private grupoService: GrupoService,
  ) {}

  ngOnInit(): void {
    forkJoin({
      estudiantes: this.estudianteService.listar(),
      grados: this.gradoService.listar(),
      areas: this.areaService.listar(),
      asignaturas: this.asignaturaService.listar(),
      anios: this.anioService.listar(),
    }).subscribe({
      next: (r) => {
        this.estudiantes = r.estudiantes || [];
        this.grados = r.grados || [];
        this.areas = r.areas || [];
        this.asignaturas = r.asignaturas || [];
        this.anioActual = (r.anios || []).find(a => a.esActual) ?? (r.anios || [])[0] ?? null;
        this.calcularBase();
        if (this.anioActual) {
          this.cargarPorAnio(this.anioActual.id);
        } else {
          this.cargando = false;
        }
      },
      error: () => {
        this.error = 'No se pudo cargar la información del panel.';
        this.cargando = false;
      },
    });
  }

  private cargarPorAnio(anioId: string): void {
    forkJoin({
      matriculas: this.matriculaService.listarPorAnio(anioId),
      cargas: this.cargaService.listarPorAnio(anioId),
      grupos: this.grupoService.listarPorAnio(anioId),
    }).subscribe({
      next: (r) => {
        this.matriculas = r.matriculas || [];
        this.cargas = r.cargas || [];
        this.grupos = r.grupos || [];
        this.calcularAnio();
        this.cargando = false;
      },
      error: () => {
        this.error = 'No se pudieron cargar los datos del año lectivo.';
        this.cargando = false;
      },
    });
  }

  private calcularBase(): void {
    this.asignaturasPorArea = this.areas
      .map(a => ({ label: a.nombre, value: a.totalAsignaturas || 0 }))
      .sort((a, b) => b.value - a.value);
  }

  private calcularAnio(): void {
    // Matrículas por estado
    const porEstado = new Map<string, number>();
    for (const m of this.matriculas) {
      const k = m.estado || 'DESCONOCIDO';
      porEstado.set(k, (porEstado.get(k) || 0) + 1);
    }
    this.matriculasPorEstado = [...porEstado.entries()].map(([k, value]) => ({
      label: this.nombreEstado(k),
      value,
      color: this.colorEstado(k),
    }));

    // Estudiantes por grado (según matrículas del año)
    const porGrado = new Map<string, number>();
    for (const m of this.matriculas) {
      const k = m.gradoNombre || 'Sin asignar';
      porGrado.set(k, (porGrado.get(k) || 0) + 1);
    }
    this.estudiantesPorGrado = [...porGrado.entries()]
      .map(([label, value]) => ({ label, value }))
      .sort((a, b) => (a.label > b.label ? 1 : -1));

    // Matrículas recientes
    this.matriculasRecientes = [...this.matriculas]
      .sort((a, b) => (b.fechaMatricula || '').localeCompare(a.fechaMatricula || ''))
      .slice(0, 6);
  }

  get estudiantesActivos(): number {
    return this.estudiantes.filter(e => e.estado === 'ACTIVO').length;
  }
  get totalMatriculados(): number {
    return this.matriculas.filter(m => m.estado === 'MATRICULADO').length;
  }

  nombreEstado(e: string): string {
    const m: Record<string, string> = {
      MATRICULADO: 'Matriculado', PREMATRICULA: 'Prematrícula',
      RETIRADO: 'Retirado', CANCELADO: 'Cancelado', APROBADO: 'Aprobado', REPROBADO: 'Reprobado',
    };
    return m[e] ?? e;
  }
  colorEstado(e: string): string {
    const m: Record<string, string> = {
      MATRICULADO: '#2E9E6B', PREMATRICULA: '#E0A82E',
      RETIRADO: '#8A94A6', CANCELADO: '#CC4B3D',
    };
    return m[e] ?? '#2F6FB4';
  }
  claseEstado(e: string): string {
    const m: Record<string, string> = {
      MATRICULADO: 'estado-activo', PREMATRICULA: 'estado-pendiente_activacion',
      RETIRADO: 'estado-retirado', CANCELADO: 'estado-bloqueado',
    };
    return m[e] ?? 'estado-inactivo';
  }
}
