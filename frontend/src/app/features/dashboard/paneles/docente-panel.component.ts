import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { forkJoin } from 'rxjs';

import { AuthService } from '../../../core/services/auth.service';
import { DocenteService, MiClase, MiFranja } from '../../../core/services/docente.service';
import { AnioLectivoService, AnioLectivo } from '../../../core/services/anio-lectivo.service';

import { KpiCardComponent } from '../shared/kpi-card.component';
import { BarChartComponent, BarItem } from '../shared/bar-chart.component';

@Component({
  selector: 'app-docente-panel',
  standalone: true,
  imports: [CommonModule, RouterModule, KpiCardComponent, BarChartComponent],
  templateUrl: './docente-panel.component.html',
  styleUrls: ['./panel.shared.scss'],
})
export class DocentePanelComponent implements OnInit {
  usuario = this.auth.getUsuarioActual();
  cargando = true;
  error = '';

  anioActual: AnioLectivo | null = null;
  clases: MiClase[] = [];
  horario: MiFranja[] = [];

  estudiantesPorClase: BarItem[] = [];
  clasesPorDia: BarItem[] = [];
  horarioHoy: MiFranja[] = [];

  readonly NOMBRE_DIA: Record<string, string> = {
    LUNES: 'Lunes', MARTES: 'Martes', MIERCOLES: 'Miércoles',
    JUEVES: 'Jueves', VIERNES: 'Viernes', SABADO: 'Sábado', DOMINGO: 'Domingo',
  };
  private readonly DIAS = ['LUNES', 'MARTES', 'MIERCOLES', 'JUEVES', 'VIERNES', 'SABADO', 'DOMINGO'];

  constructor(
    private auth: AuthService,
    private docenteService: DocenteService,
    private anioService: AnioLectivoService,
  ) {}

  ngOnInit(): void {
    this.anioService.listar().subscribe({
      next: (anios) => {
        this.anioActual = (anios || []).find(a => a.esActual) ?? (anios || [])[0] ?? null;
        if (!this.anioActual) { this.cargando = false; return; }
        forkJoin({
          clases: this.docenteService.misClases(this.anioActual.id),
          horario: this.docenteService.miHorario(this.anioActual.id),
        }).subscribe({
          next: (r) => {
            this.clases = r.clases || [];
            this.horario = r.horario || [];
            this.calcular();
            this.cargando = false;
          },
          error: () => {
            this.error = 'No se pudieron cargar tus clases.';
            this.cargando = false;
          },
        });
      },
      error: () => {
        this.error = 'No se pudieron cargar los años lectivos.';
        this.cargando = false;
      },
    });
  }

  private calcular(): void {
    // Estudiantes por clase
    this.estudiantesPorClase = this.clases
      .map(c => ({ label: c.asignaturaNombre, sub: c.grupoCodigo || c.grupoNombre, value: c.totalEstudiantes || 0 }))
      .sort((a, b) => b.value - a.value);

    // Clases (franjas) por día
    const conteo = new Map<string, number>();
    for (const f of this.horario) {
      conteo.set(f.diaSemana, (conteo.get(f.diaSemana) || 0) + 1);
    }
    const dias = this.DIAS.filter(d =>
      ['LUNES', 'MARTES', 'MIERCOLES', 'JUEVES', 'VIERNES'].includes(d) || (conteo.get(d) || 0) > 0);
    this.clasesPorDia = dias.map(d => ({ label: this.NOMBRE_DIA[d], value: conteo.get(d) || 0 }));

    // Horario de hoy
    const hoy = this.diaDeHoy();
    this.horarioHoy = this.horario
      .filter(f => f.diaSemana === hoy)
      .sort((a, b) => (a.horaInicio || '').localeCompare(b.horaInicio || ''));
  }

  private diaDeHoy(): string {
    const map = ['DOMINGO', 'LUNES', 'MARTES', 'MIERCOLES', 'JUEVES', 'VIERNES', 'SABADO'];
    return map[new Date().getDay()];
  }

  get totalEstudiantes(): number {
    return this.clases.reduce((a, c) => a + (c.totalEstudiantes || 0), 0);
  }
  get gruposDistintos(): number {
    return new Set(this.clases.map(c => c.grupoId)).size;
  }
  get horasSemana(): number {
    return this.clases.reduce((a, c) => a + (c.intensidadHorariaSemanal || 0), 0);
  }
}
