import { Component, OnInit } from '@angular/core';
import { ReporteService, MatrizAsistencia } from '../../../../core/services/reporte.service';
import { AnioLectivoService, AnioLectivo } from '../../../../core/services/anio-lectivo.service';
import { CargaService, CargaResumen } from '../../../../core/services/carga.service';
import { descargarMatrizExcel } from '../../../../core/utils/matriz-excel.util';

@Component({
  selector: 'app-matriz-asistencia',
  templateUrl: './matriz-asistencia.component.html',
  styleUrls: ['./matriz-asistencia.component.scss']
})
export class MatrizAsistenciaComponent implements OnInit {

  anios: AnioLectivo[] = [];
  anioSeleccionado = '';
  clases: CargaResumen[] = [];
  cargaSeleccionada = '';
  matriz: MatrizAsistencia | null = null;
  cargando = false; error = '';

  constructor(
    private reporteService: ReporteService,
    private anioService: AnioLectivoService,
    private cargaService: CargaService
  ) {}

  ngOnInit(): void {
    this.anioService.listar().subscribe({
      next: (d) => {
        this.anios = d;
        const actual = d.find(a => a.esActual) ?? d[0];
        if (actual) { this.anioSeleccionado = actual.id; this.cargarClases(); }
      },
      error: () => { this.error = 'No se pudieron cargar los años lectivos.'; }
    });
  }

  cambioAnio(): void { this.cargaSeleccionada = ''; this.matriz = null; this.cargarClases(); }

  cargarClases(): void {
    this.cargaService.listarPorAnio(this.anioSeleccionado).subscribe({
      next: (d) => { this.clases = d.filter(c => c.estado === 'ACTIVA'); },
      error: () => { this.error = 'No se pudieron cargar las clases.'; }
    });
  }

  cambioClase(): void { this.cargarMatriz(); }

  cargarMatriz(): void {
    if (!this.cargaSeleccionada) { this.matriz = null; return; }
    this.cargando = true; this.error = '';
    this.reporteService.matrizClase(this.cargaSeleccionada).subscribe({
      next: (m) => { this.matriz = m; this.cargando = false; },
      error: (err) => {
        this.error = err?.status === 403 ? 'Esa clase no pertenece a tu institución.' : 'No se pudo cargar la matriz.';
        this.cargando = false;
      }
    });
  }

  get claseActual(): CargaResumen | undefined {
    return this.clases.find(c => c.id === this.cargaSeleccionada);
  }

  sigla(marca: string): string {
    return { PRESENTE: 'P', AUSENTE: 'A', TARDE: 'T', JUSTIFICADA: 'J' }[marca] ?? '·';
  }
  claseCelda(marca: string): string {
    return { PRESENTE: 'm-presente', AUSENTE: 'm-ausente', TARDE: 'm-tarde', JUSTIFICADA: 'm-justificada' }[marca] ?? 'm-vacia';
  }

  descargar(): void {
    if (!this.matriz) return;
    const c = this.claseActual;
    const nombre = c ? `asistencia_${c.asignaturaNombre}_${c.grupoNombre}.xlsx` : 'asistencia.xlsx';
    descargarMatrizExcel(this.matriz, nombre.replace(/\s+/g, '_'));
  }
}
