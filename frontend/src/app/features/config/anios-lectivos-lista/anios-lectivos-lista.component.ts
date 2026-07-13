import { Component, OnInit } from '@angular/core';
import {
  AnioLectivoService,
  AnioLectivo,
} from '../../../core/services/anio-lectivo.service';

@Component({
  selector: 'app-anios-lectivos-lista',
  templateUrl: './anios-lectivos-lista.component.html',
  styleUrls: ['./anios-lectivos-lista.component.scss'],
})
export class AniosLectivosListaComponent implements OnInit {
  anios: AnioLectivo[] = [];
  cargando = false;
  error = '';
  procesandoId: string | null = null;

  constructor(private anioService: AnioLectivoService) {}

  ngOnInit(): void {
    this.cargar();
  }

  cargar(): void {
    this.cargando = true;
    this.error = '';
    this.anioService.listar().subscribe({
      next: (d) => {
        this.anios = d;
        this.cargando = false;
      },
      error: () => {
        this.error = 'No se pudieron cargar los años lectivos.';
        this.cargando = false;
      },
    });
  }

  marcarActual(a: AnioLectivo): void {
    this.procesandoId = a.id;
    this.anioService.marcarActual(a.id).subscribe({
      next: () => {
        this.procesandoId = null;
        this.cargar();
      },
      error: () => {
        this.procesandoId = null;
        this.error = 'No se pudo marcar como actual.';
      },
    });
  }

  /* ══════════ vistas derivadas ══════════ */

  /** el año vigente — protagonista de la vista */
  get actual(): AnioLectivo | undefined {
    return this.anios.find((a) => a.esActual);
  }

  /** los demás, del más reciente al más antiguo */
  get otros(): AnioLectivo[] {
    return this.anios
      .filter((a) => !a.esActual)
      .sort((x, y) => (y.anio ?? 0) - (x.anio ?? 0));
  }

  /* ══════════ progreso del año escolar ══════════ */

  private aFecha(s?: string | null): Date | null {
    if (!s) return null;
    const d = new Date(s + 'T00:00:00');
    return isNaN(d.getTime()) ? null : d;
  }

  tieneFechas(a: AnioLectivo): boolean {
    return this.aFecha(a.fechaInicio) != null && this.aFecha(a.fechaFin) != null;
  }

  /** % del año escolar ya transcurrido (0–100) */
  pctTranscurrido(a: AnioLectivo): number {
    const ini = this.aFecha(a.fechaInicio), fin = this.aFecha(a.fechaFin);
    if (!ini || !fin || fin <= ini) return 0;
    const hoy = new Date();
    const pct = ((hoy.getTime() - ini.getTime()) / (fin.getTime() - ini.getTime())) * 100;
    return Math.min(100, Math.max(0, Math.round(pct)));
  }

  /** días que faltan para terminar (null si no aplica) */
  diasRestantes(a: AnioLectivo): number | null {
    const fin = this.aFecha(a.fechaFin);
    if (!fin) return null;
    const dias = Math.ceil((fin.getTime() - Date.now()) / 86400000);
    return Math.max(0, dias);
  }

  /** ¿el año aún no comienza? */
  noHaIniciado(a: AnioLectivo): boolean {
    const ini = this.aFecha(a.fechaInicio);
    return !!ini && ini.getTime() > Date.now();
  }
}
