import { Component, OnInit } from '@angular/core';
import { EstudianteService, MisNotasVista } from '../../../../core/services/rol/estudiante.service';
import { AnioLectivoService, AnioLectivo } from '../../../../core/services/anio-lectivo.service';
import { PeriodoService, Periodo } from '../../../../core/services/periodo.service';

@Component({
  selector: 'app-mis-notas',
  templateUrl: './mis-notas.component.html',
  styleUrls: ['./mis-notas.component.scss']
})
export class MisNotasComponent implements OnInit {

  anios: AnioLectivo[] = [];
  periodos: Periodo[] = [];
  anioSeleccionado = '';
  periodoSeleccionado = '';
  vista: MisNotasVista | null = null;
  cargando = false; error = '';

  constructor(
    private estudianteService: EstudianteService,
    private anioService: AnioLectivoService,
    private periodoService: PeriodoService
  ) {}

  ngOnInit(): void {
    this.anioService.listar().subscribe({
      next: (d) => {
        this.anios = d;
        const actual = d.find(a => a.esActual) ?? d[0];
        if (actual) { this.anioSeleccionado = actual.id; this.cargarPeriodos(); }
      },
      error: () => { this.error = 'No se pudieron cargar los años lectivos.'; }
    });
  }

  cambioAnio(): void { this.periodoSeleccionado = ''; this.vista = null; this.cargarPeriodos(); }

  cargarPeriodos(): void {
    this.periodoService.listarPorAnio(this.anioSeleccionado).subscribe({
      next: (d) => { this.periodos = d; if (d.length) { this.periodoSeleccionado = d[0].id; this.cargar(); } },
      error: () => { this.error = 'No se pudieron cargar los periodos.'; }
    });
  }

  cambioPeriodo(): void { this.cargar(); }

  cargar(): void {
    if (!this.anioSeleccionado || !this.periodoSeleccionado) return;
    this.cargando = true; this.error = '';
    this.estudianteService.misNotas(this.anioSeleccionado, this.periodoSeleccionado).subscribe({
      next: (v) => { this.vista = v; this.cargando = false; },
      error: () => { this.error = 'No se pudieron cargar tus notas.'; this.cargando = false; }
    });
  }
}
