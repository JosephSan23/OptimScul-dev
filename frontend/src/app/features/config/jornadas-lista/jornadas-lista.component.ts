import { Component, OnInit } from '@angular/core';
import { JornadaService, Jornada } from '../../../core/services/jornada.service';

@Component({
  selector: 'app-jornadas-lista',
  templateUrl: './jornadas-lista.component.html',
  styleUrls: ['./jornadas-lista.component.scss']
})
export class JornadasListaComponent implements OnInit {
  jornadas: Jornada[] = [];
  cargando = false; error = ''; procesandoId: string | null = null;

  constructor(private jornadaService: JornadaService) {}

  ngOnInit(): void { this.cargar(); }

  cargar(): void {
    this.cargando = true; this.error = '';
    this.jornadaService.listar().subscribe({
      next: (d) => { this.jornadas = d; this.cargando = false; },
      error: () => { this.error = 'No se pudieron cargar las jornadas.'; this.cargando = false; }
    });
  }

  activar(j: Jornada): void {
    this.procesandoId = j.id;
    this.jornadaService.activar(j.id).subscribe({
      next: () => { this.procesandoId = null; this.cargar(); },
      error: () => { this.procesandoId = null; this.error = 'No se pudo activar.'; }
    });
  }

  inactivar(j: Jornada): void {
    this.procesandoId = j.id;
    this.jornadaService.inactivar(j.id).subscribe({
      next: () => { this.procesandoId = null; this.cargar(); },
      error: () => { this.procesandoId = null; this.error = 'No se pudo inactivar.'; }
    });
  }

  /* ══════════ helpers de la línea de tiempo ══════════
     Convierten "HH:mm" en posiciones % sobre un día de 24h
     para pintar la franja de cada jornada. */

  private minutos(hora?: string | null): number | null {
    if (!hora) return null;
    const [h, m] = hora.split(':').map(Number);
    if (isNaN(h)) return null;
    return h * 60 + (m || 0);
  }

  tieneHorario(j: Jornada): boolean {
    return this.minutos(j.horaInicio) != null && this.minutos(j.horaFin) != null;
  }

  /** posición izquierda de la franja (%) */
  pctInicio(j: Jornada): number {
    const m = this.minutos(j.horaInicio);
    return m == null ? 0 : (m / 1440) * 100;
  }

  /** true si la jornada cruza medianoche (ej. nocturna 18:00 → 02:00) */
  cruzaMedianoche(j: Jornada): boolean {
    const i = this.minutos(j.horaInicio), f = this.minutos(j.horaFin);
    return i != null && f != null && f <= i;
  }

  /** ancho de la franja (%) — si cruza medianoche, es el tramo hasta las 24h */
  pctAncho(j: Jornada): number {
    const i = this.minutos(j.horaInicio), f = this.minutos(j.horaFin);
    if (i == null || f == null) return 0;
    if (f <= i) return ((1440 - i) / 1440) * 100;
    return ((f - i) / 1440) * 100;
  }

  /** ancho del tramo que continúa desde las 0h (solo si cruza medianoche) */
  pctAnchoMadrugada(j: Jornada): number {
    const f = this.minutos(j.horaFin);
    return f == null ? 0 : (f / 1440) * 100;
  }

  duracionTexto(j: Jornada): string {
    const i = this.minutos(j.horaInicio), f = this.minutos(j.horaFin);
    if (i == null || f == null) return '';
    const total = f > i ? f - i : (1440 - i) + f;
    const h = Math.floor(total / 60), m = total % 60;
    return m ? `${h} h ${m} min` : `${h} h`;
  }

  /** icono según la hora de inicio: amanecer / sol / atardecer / luna */
  iconoJornada(j: Jornada): string {
    const i = this.minutos(j.horaInicio);
    if (i == null) return 'ti-clock';
    const h = i / 60;
    if (h >= 5 && h < 11) return 'ti-sunrise';
    if (h >= 11 && h < 14) return 'ti-sun';
    if (h >= 14 && h < 19) return 'ti-sunset-2';
    return 'ti-moon';
  }
}
