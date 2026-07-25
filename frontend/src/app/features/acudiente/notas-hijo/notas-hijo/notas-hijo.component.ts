import { Component, OnInit } from '@angular/core';
import { AcudienteService, Hijo } from '../../../../core/services/rol/acudiente.service';
import { MisNotasVista } from '../../../../core/services/rol/estudiante.service';
import { AnioLectivoService, AnioLectivo } from '../../../../core/services/anio-lectivo.service';
import { PeriodoService, Periodo } from '../../../../core/services/periodo.service';

@Component({
  selector: 'app-notas-hijo',
  templateUrl: './notas-hijo.component.html',
  styleUrls: ['./notas-hijo.component.scss']
})
export class NotasHijoComponent implements OnInit {

  hijos: Hijo[] = [];
  hijoSeleccionado = '';
  anios: AnioLectivo[] = [];
  periodos: Periodo[] = [];
  anioSeleccionado = '';
  periodoSeleccionado = '';
  vista: MisNotasVista | null = null;
  cargando = false; error = '';

  constructor(
    private acudienteService: AcudienteService,
    private anioService: AnioLectivoService,
    private periodoService: PeriodoService
  ) {}

  ngOnInit(): void {
    this.acudienteService.hijos().subscribe({
      next: (h) => {
        this.hijos = h;
        if (h.length) this.hijoSeleccionado = h[0].estudianteId;
        this.anioService.listar().subscribe({
          next: (d) => {
            this.anios = d;
            const actual = d.find(a => a.esActual) ?? d[0];
            if (actual) { this.anioSeleccionado = actual.id; this.cargarPeriodos(); }
          },
          error: () => { this.error = 'No se pudieron cargar los años lectivos.'; }
        });
      },
      error: () => { this.error = 'No se pudieron cargar tus hijos.'; }
    });
  }

  cambioAnio(): void { this.periodoSeleccionado = ''; this.vista = null; this.cargarPeriodos(); }

  cargarPeriodos(): void {
    this.periodoService.listarPorAnio(this.anioSeleccionado).subscribe({
      next: (d) => { this.periodos = d; if (d.length) { this.periodoSeleccionado = d[0].id; this.cargar(); } },
      error: () => { this.error = 'No se pudieron cargar los periodos.'; }
    });
  }

  cambioHijo(): void { this.cargar(); }
  cambioPeriodo(): void { this.cargar(); }

  cargar(): void {
    if (!this.hijoSeleccionado || !this.anioSeleccionado || !this.periodoSeleccionado) return;
    this.cargando = true; this.error = '';
    this.acudienteService.notasHijo(this.hijoSeleccionado, this.anioSeleccionado, this.periodoSeleccionado).subscribe({
      next: (v) => { this.vista = v; this.cargando = false; },
      error: () => { this.error = 'No se pudieron cargar las notas.'; this.cargando = false; }
    });
  }
}
