import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ActividadService, Actividad } from '../../../../../core/services/actividad.service';
import { PeriodoService, Periodo } from '../../../../../core/services/periodo.service';

@Component({
  selector: 'app-actividades-lista',
  templateUrl: './actividades-lista.component.html',
  styleUrls: ['./actividades-lista.component.scss']
})
export class ActividadesListaComponent implements OnInit {

  cargaId!: string;
  anio = '';
  periodos: Periodo[] = [];
  periodoSeleccionado = '';
  actividades: Actividad[] = [];
  cargando = false; error = ''; procesandoId: string | null = null;

  readonly TIPOS_LABEL: Record<string, string> = {
    TAREA: 'Tarea', QUIZ: 'Quiz', EXAMEN: 'Examen', TRABAJO: 'Trabajo',
    EXPOSICION: 'Exposición', PROYECTO: 'Proyecto', RECUPERACION: 'Recuperación', OTRA: 'Otra'
  };

  constructor(
    private actividadService: ActividadService,
    private periodoService: PeriodoService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.cargaId = this.route.snapshot.paramMap.get('cargaId')!;
    this.anio = this.route.snapshot.queryParamMap.get('anio') ?? '';
    if (!this.anio) { this.error = 'Falta el año. Vuelve a la clase y entra desde "Actividades y notas".'; return; }
    this.periodoService.listarPorAnio(this.anio).subscribe({
      next: (d) => {
        this.periodos = d;
        if (d.length) { this.periodoSeleccionado = d[0].id; this.cargar(); }
      },
      error: () => { this.error = 'No se pudieron cargar los periodos.'; }
    });
  }

  cambioPeriodo(): void { this.cargar(); }

  cargar(): void {
    if (!this.periodoSeleccionado) return;
    this.cargando = true; this.error = '';
    this.actividadService.listar(this.cargaId, this.periodoSeleccionado).subscribe({
      next: (d) => { this.actividades = d; this.cargando = false; },
      error: () => { this.error = 'No se pudieron cargar las actividades.'; this.cargando = false; }
    });
  }

  tipoTexto(t: string): string { return this.TIPOS_LABEL[t] ?? t; }

  nueva(): void {
    this.router.navigate(['/dashboard/mis-clases', this.cargaId, 'actividades', 'nueva'],
      { queryParams: { anio: this.anio, periodo: this.periodoSeleccionado } });
  }

  editar(a: Actividad): void {
    this.router.navigate(['/dashboard/mis-clases', this.cargaId, 'actividades', a.id, 'editar'],
      { queryParams: { anio: this.anio } });
  }

  calificar(a: Actividad): void {
    this.router.navigate(['/dashboard/actividades', a.id, 'calificar'], { queryParams: { anio: this.anio, carga: this.cargaId } });
  }

  private accion(id: string, obs: any): void {
    this.procesandoId = id;
    obs.subscribe({
      next: () => { this.procesandoId = null; this.cargar(); },
      error: (err: any) => { this.procesandoId = null; this.error = err?.error?.mensaje || 'No se pudo completar la acción.'; }
    });
  }

  cerrar(a: Actividad): void   { this.accion(a.id, this.actividadService.cerrar(a.id)); }
  publicar(a: Actividad): void { this.accion(a.id, this.actividadService.publicar(a.id)); }
  anular(a: Actividad): void   { this.accion(a.id, this.actividadService.anular(a.id)); }
  eliminar(a: Actividad): void {
    this.procesandoId = a.id;
    this.actividadService.eliminarActividad(a.id).subscribe({
      next: () => { this.procesandoId = null; this.cargar(); },
      error: (err) => { this.procesandoId = null; this.error = err?.error?.mensaje || 'No se pudo eliminar.'; }
    });
  }

  volver(): void {
    this.router.navigate(['/dashboard/mis-clases', this.cargaId], { queryParams: { anio: this.anio } });
  }
}
