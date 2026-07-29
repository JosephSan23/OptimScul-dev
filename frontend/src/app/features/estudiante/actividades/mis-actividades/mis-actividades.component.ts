import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ActividadEstudianteService, MiActividad } from '../../../../core/services/rol/estudiante/actividad-estudiante.service';
import { AnioLectivoService, AnioLectivo } from '../../../../core/services/anio-lectivo.service';

@Component({
  selector: 'app-mis-actividades',
  templateUrl: './mis-actividades.component.html',
  styleUrls: ['./mis-actividades.component.scss']
})
export class MisActividadesComponent implements OnInit {

  anios: AnioLectivo[] = [];
  anioSeleccionado = '';
  actividades: MiActividad[] = [];
  cargando = false; error = '';

  constructor(
    private actividadService: ActividadEstudianteService,
    private anioService: AnioLectivoService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.anioService.listar().subscribe({
      next: (d) => {
        this.anios = d;
        const actual = d.find(a => a.esActual) ?? d[0];
        if (actual) { this.anioSeleccionado = actual.id; this.cargar(); }
      },
      error: () => { this.error = 'No se pudieron cargar los años lectivos.'; }
    });
  }

  cambioAnio(): void { this.cargar(); }

  cargar(): void {
    if (!this.anioSeleccionado) return;
    this.cargando = true; this.error = '';
    this.actividadService.misActividades(this.anioSeleccionado).subscribe({
      next: (d) => { this.actividades = d; this.cargando = false; },
      error: () => { this.error = 'No se pudieron cargar tus actividades.'; this.cargando = false; }
    });
  }

  abrir(a: MiActividad): void {
    this.router.navigate(['/dashboard/estudiante/actividades', a.actividadId]);
  }

  claseEstado(estado: string): string {
    switch (estado) {
      case 'CALIFICADA':
      case 'ENTREGADA':        return 'estado-activo';
      case 'ENTREGADA_TARDE':  return 'tag warn';
      default:                 return 'estado-inactivo';
    }
  }

  etiquetaEstado(estado: string): string {
    const m: Record<string, string> = {
      PENDIENTE: 'Pendiente', ENTREGADA: 'Entregada',
      ENTREGADA_TARDE: 'Entregada tarde', NO_ENTREGADA: 'No entregada', CALIFICADA: 'Calificada'
    };
    return m[estado] ?? estado;
  }
}
