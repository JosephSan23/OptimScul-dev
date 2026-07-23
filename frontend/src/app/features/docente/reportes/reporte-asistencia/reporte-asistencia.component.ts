import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { DocenteService, ReporteAsistenciaFila, MiClase } from '../../../../core/services/docente.service';
import { descargarMatrizExcel } from '../../../../core/utils/matriz-excel.util';
import { AnioLectivoService } from '../../../../core/services/anio-lectivo.service';



@Component({
  selector: 'app-reporte-asistencia',
  templateUrl: './reporte-asistencia.component.html',
  styleUrls: ['./reporte-asistencia.component.scss']
})
export class ReporteAsistenciaComponent implements OnInit {

  clases: MiClase[] = [];
  cargaSeleccionada = '';
  filas: ReporteAsistenciaFila[] = [];
  cargando = false; error = '';

  constructor(
    private docenteService: DocenteService,
    private anioService: AnioLectivoService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    const cargaInicial = this.route.snapshot.paramMap.get('cargaId') ?? '';
    // cargar las clases del año actual para el selector
    this.anioService.listar().subscribe({
      next: (anios) => {
        const actual = anios.find((a: any) => a.esActual) ?? anios[0];
        if (!actual) return;
        this.docenteService.misClases(actual.id).subscribe({
          next: (c) => {
            this.clases = c;
            this.cargaSeleccionada = cargaInicial || (c[0]?.cargaId ?? '');
            if (this.cargaSeleccionada) this.cargar();
          },
          error: () => { this.error = 'No se pudieron cargar tus clases.'; }
        });
      },
      error: () => { this.error = 'No se pudieron cargar los años lectivos.'; }
    });
  }

  cambioClase(): void { this.cargar(); }

  cargar(): void {
    if (!this.cargaSeleccionada) return;
    this.cargando = true; this.error = '';
    this.docenteService.reporteAsistencia(this.cargaSeleccionada).subscribe({
      next: (d) => { this.filas = d; this.cargando = false; },
      error: (err) => {
        this.error = err?.status === 403 ? 'Esa clase no te pertenece.' : 'No se pudo cargar el reporte.';
        this.cargando = false;
      }
    });
  }

  // asistió = presente + tarde (se presentó); % sobre sus registros
  porcentaje(f: ReporteAsistenciaFila): number {
    if (!f.totalRegistros) return 0;
    return Math.round(((f.presente + f.tarde) / f.totalRegistros) * 100);
  }

  claseRiesgo(f: ReporteAsistenciaFila): string {
    const p = this.porcentaje(f);
    if (p >= 90) return 'ok';
    if (p >= 80) return 'medio';
    return 'bajo';
  }

  get totalSesiones(): number {
    return this.filas.reduce((max, f) => Math.max(max, f.totalRegistros), 0);
  }

  volver(): void {
    if (this.cargaSeleccionada) this.router.navigate(['/dashboard/mis-clases', this.cargaSeleccionada]);
    else this.router.navigate(['/dashboard/mis-clases']);
  }

  descargarMatriz(): void {
    if (!this.cargaSeleccionada) return;
    this.docenteService.matrizClase(this.cargaSeleccionada).subscribe({
      next: (m) => {
        const c = this.clases.find(x => x.cargaId === this.cargaSeleccionada);
        const nombre = c ? `asistencia_${c.asignaturaNombre}_${c.grupoNombre}.xlsx` : 'asistencia.xlsx';
        descargarMatrizExcel(m, nombre.replace(/\s+/g, '_'));
      },
      error: () => { this.error = 'No se pudo generar el Excel.'; }
    });
  }


}
