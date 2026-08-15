import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { forkJoin } from 'rxjs';

import { AuthService } from '../../../core/services/auth.service';
import { InstitucionConfigService, InstitucionConfig } from '../../../core/services/institucion-config.service';
import { StaffService, Staff } from '../../../core/services/staff.service';
import { SedeService, Sede } from '../../../core/services/sede.service';
import { JornadaService, Jornada } from '../../../core/services/jornada.service';
import { GradoService, Grado } from '../../../core/services/grado.service';
import { AreaService, Area } from '../../../core/services/area.service';
import { AsignaturaService, Asignatura } from '../../../core/services/asignatura.service';
import { AnioLectivoService, AnioLectivo } from '../../../core/services/anio-lectivo.service';
import { PeriodoService } from '../../../core/services/periodo.service';

import { KpiCardComponent } from '../shared/kpi-card.component';
import { BarChartComponent, BarItem } from '../shared/bar-chart.component';
import { DonutChartComponent, DonutItem } from '../shared/donut-chart.component';

@Component({
  selector: 'app-admin-panel',
  standalone: true,
  imports: [CommonModule, RouterModule, KpiCardComponent, BarChartComponent, DonutChartComponent],
  templateUrl: './admin-panel.component.html',
  styleUrls: ['./panel.shared.scss'],
})
export class AdminPanelComponent implements OnInit {
  usuario = this.auth.getUsuarioActual();
  cargando = true;
  error = '';

  institucion: InstitucionConfig | null = null;
  anioActual: AnioLectivo | null = null;

  staff: Staff[] = [];
  sedes: Sede[] = [];
  jornadas: Jornada[] = [];
  grados: Grado[] = [];
  areas: Area[] = [];
  asignaturas: Asignatura[] = [];
  totalPeriodos = 0;

  personalPorRol: BarItem[] = [];
  personalEstado: DonutItem[] = [];
  gruposPorGrado: BarItem[] = [];
  staffReciente: Staff[] = [];

  accesos = [
    { label: 'Personal',          icon: 'ti-users',           ruta: '/dashboard/staff' },
    { label: 'Sedes',             icon: 'ti-building',        ruta: '/dashboard/sedes' },
    { label: 'Jornadas',          icon: 'ti-clock-hour-4',    ruta: '/dashboard/jornadas' },
    { label: 'Año lectivo',       icon: 'ti-calendar-event',  ruta: '/dashboard/anios-lectivos' },
    { label: 'Config. académica', icon: 'ti-adjustments',     ruta: '/dashboard/config-academica' },
    { label: 'Escala valorativa', icon: 'ti-award',           ruta: '/dashboard/escalas' },
  ];

  constructor(
    private auth: AuthService,
    private institucionService: InstitucionConfigService,
    private staffService: StaffService,
    private sedeService: SedeService,
    private jornadaService: JornadaService,
    private gradoService: GradoService,
    private areaService: AreaService,
    private asignaturaService: AsignaturaService,
    private anioService: AnioLectivoService,
    private periodoService: PeriodoService,
  ) {}

  ngOnInit(): void {
    forkJoin({
      institucion: this.institucionService.miInstitucion(),
      staff: this.staffService.listar(),
      sedes: this.sedeService.listar(),
      jornadas: this.jornadaService.listar(),
      grados: this.gradoService.listar(),
      areas: this.areaService.listar(),
      asignaturas: this.asignaturaService.listar(),
      anios: this.anioService.listar(),
    }).subscribe({
      next: (r) => {
        this.institucion = r.institucion;
        this.staff = r.staff || [];
        this.sedes = r.sedes || [];
        this.jornadas = r.jornadas || [];
        this.grados = r.grados || [];
        this.areas = r.areas || [];
        this.asignaturas = r.asignaturas || [];
        this.anioActual = (r.anios || []).find(a => a.esActual) ?? (r.anios || [])[0] ?? null;
        this.calcular();
        this.cargando = false;
        if (this.anioActual) {
          this.periodoService.listarPorAnio(this.anioActual.id).subscribe({
            next: (p) => (this.totalPeriodos = (p || []).length),
            error: () => {},
          });
        }
      },
      error: () => {
        this.error = 'No se pudo cargar la información del panel.';
        this.cargando = false;
      },
    });
  }

  private calcular(): void {
    // Personal por rol
    const porRol = new Map<string, number>();
    for (const s of this.staff) {
      const k = s.rolNombre || 'Sin rol';
      porRol.set(k, (porRol.get(k) || 0) + 1);
    }
    this.personalPorRol = [...porRol.entries()]
      .map(([label, value]) => ({ label, value }))
      .sort((a, b) => b.value - a.value);

    // Personal por estado
    const porEstado = new Map<string, number>();
    for (const s of this.staff) {
      const k = s.estado || 'DESCONOCIDO';
      porEstado.set(k, (porEstado.get(k) || 0) + 1);
    }
    this.personalEstado = [...porEstado.entries()].map(([k, value]) => ({
      label: this.nombreEstado(k),
      value,
      color: this.colorEstado(k),
    }));

    // Grupos por grado (dato real de Grado.totalGrupos)
    this.gruposPorGrado = this.grados
      .map(g => ({ label: g.nombre, value: g.totalGrupos || 0 }))
      .sort((a, b) => (a.label > b.label ? 1 : -1));

    // Personal reciente
    this.staffReciente = [...this.staff]
      .sort((a, b) => (b.createdAt || '').localeCompare(a.createdAt || ''))
      .slice(0, 6);
  }

  get personalActivo(): number {
    return this.staff.filter(s => s.estado === 'ACTIVO').length;
  }
  get sedesActivas(): number {
    return this.sedes.filter(s => s.estado === 'ACTIVO').length;
  }

  nombreEstado(e: string): string {
    const m: Record<string, string> = {
      ACTIVO: 'Activo', INACTIVO: 'Inactivo',
      PENDIENTE_ACTIVACION: 'Pendiente', BLOQUEADO: 'Bloqueado',
    };
    return m[e] ?? e;
  }
  colorEstado(e: string): string {
    const m: Record<string, string> = {
      ACTIVO: '#2E9E6B', INACTIVO: '#8A94A6',
      PENDIENTE_ACTIVACION: '#E0A82E', BLOQUEADO: '#CC4B3D',
    };
    return m[e] ?? '#2F6FB4';
  }
  claseEstado(e: string): string {
    const m: Record<string, string> = {
      ACTIVO: 'estado-activo', INACTIVO: 'estado-inactivo',
      PENDIENTE_ACTIVACION: 'estado-pendiente_activacion', BLOQUEADO: 'estado-bloqueado',
    };
    return m[e] ?? 'estado-inactivo';
  }
  iniciales(s: Staff): string {
    return ((s.primerNombre?.[0] || '') + (s.primerApellido?.[0] || '')).toUpperCase();
  }
}
