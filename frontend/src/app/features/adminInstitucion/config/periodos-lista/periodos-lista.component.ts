import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PeriodoService, Periodo } from '../../../../core/services/periodo.service';

@Component({
  selector: 'app-periodos-lista',
  templateUrl: './periodos-lista.component.html',
  styleUrls: ['./periodos-lista.component.scss']
})
export class PeriodosListaComponent implements OnInit {
  anioId = '';
  periodos: Periodo[] = [];
  cargando = false; error = '';

  constructor(private periodoService: PeriodoService, private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.anioId = this.route.snapshot.paramMap.get('anioId') || '';
    this.cargar();
  }

  cargar(): void {
    this.cargando = true; this.error = '';
    this.periodoService.listarPorAnio(this.anioId).subscribe({
      next: (d) => { this.periodos = d; this.cargando = false; },
      error: () => { this.error = 'No se pudieron cargar los periodos.'; this.cargando = false; }
    });
  }

  /* ══════════ vistas derivadas ══════════ */

  /** periodos ordenados por número */
  get ordenados(): Periodo[] {
    return [...this.periodos].sort((a, b) => (a.numero ?? 0) - (b.numero ?? 0));
  }

  /* ---------- presupuesto de peso ---------- */

  get hayPesos(): boolean {
    return this.periodos.some(p => p.peso != null);
  }

  get pesoTotal(): number {
    return this.periodos.reduce((s, p) => s + (p.peso ?? 0), 0);
  }

  get pesoRestante(): number {
    return Math.max(0, 100 - this.pesoTotal);
  }

  get pesoExcedido(): number {
    return Math.max(0, this.pesoTotal - 100);
  }

  get pesoCompleto(): boolean {
    return this.pesoTotal === 100;
  }

  /** dos colores de marca alternados para distinguir segmentos vecinos */
  colorSegmento(i: number): string {
    return i % 2 === 0 ? 'var(--orange-500)' : 'var(--navy-700)';
  }

  /** número con cero a la izquierda: 1 → "01" */
  dosDigitos(n?: number | null): string {
    return String(n ?? 0).padStart(2, '0');
  }
}
