import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ActividadService, ConsolidadoVista, ConsolidadoFila } from '../../../../../../core/services/actividad.service';

@Component({
  selector: 'app-consolidado',
  templateUrl: './consolidado.component.html',
  styleUrls: ['./consolidado.component.scss']
})
export class ConsolidadoComponent implements OnInit {

  cargaId!: string; anio = ''; periodoId = '';
  vista: ConsolidadoVista | null = null;
  cargando = false; error = '';

  constructor(private actividadService: ActividadService, private route: ActivatedRoute, private router: Router) {}

  ngOnInit(): void {
    this.cargaId = this.route.snapshot.paramMap.get('cargaId')!;
    this.anio = this.route.snapshot.queryParamMap.get('anio') ?? '';
    this.periodoId = this.route.snapshot.queryParamMap.get('periodo') ?? '';
    if (!this.periodoId) { this.error = 'Falta el periodo. Vuelve a actividades.'; return; }
    this.cargar();
  }

  cargar(): void {
    this.cargando = true; this.error = '';
    this.actividadService.consolidado(this.cargaId, this.periodoId).subscribe({
      next: (v) => { this.vista = v; this.cargando = false; },
      error: (err) => {
        this.error = err?.status === 403 ? 'Esa clase no te pertenece.' : 'No se pudo cargar el consolidado.';
        this.cargando = false;
      }
    });
  }

  get pesoIncompleto(): boolean {
    return this.vista != null && this.vista.sumaPorcentajes !== 100;
  }

  volver(): void {
    this.router.navigate(['/dashboard/mis-clases', this.cargaId, 'actividades'], { queryParams: { anio: this.anio } });
  }
}
