import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

export interface DonutItem {
  label: string;
  value: number;
  color: string;
}

interface Segmento {
  color: string;
  dash: string;
  offset: number;
}

/**
 * Gráfico de dona SVG sin dependencias.
 * Segmentos con separación de 2px (surface gap), leyenda con etiqueta directa,
 * valor y porcentaje. El centro muestra el total.
 */
@Component({
  selector: 'app-donut-chart',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="donut" *ngIf="total > 0; else vacio">
      <div class="donut__chart">
        <svg viewBox="0 0 120 120" role="img" [attr.aria-label]="ariaLabel">
          <circle class="donut__bg" cx="60" cy="60" [attr.r]="radio"
                  fill="none" stroke="var(--line-soft)" [attr.stroke-width]="grosor"></circle>
          <circle *ngFor="let s of segmentos"
                  cx="60" cy="60" [attr.r]="radio" fill="none"
                  [attr.stroke]="s.color" [attr.stroke-width]="grosor"
                  [attr.stroke-dasharray]="s.dash"
                  [attr.stroke-dashoffset]="s.offset"
                  transform="rotate(-90 60 60)"
                  stroke-linecap="butt"></circle>
          <text x="60" y="55" text-anchor="middle" class="donut__total">{{ total }}</text>
          <text x="60" y="71" text-anchor="middle" class="donut__cap">{{ centro }}</text>
        </svg>
      </div>
      <ul class="donut__legend">
        <li *ngFor="let it of items">
          <span class="dot" [style.background]="it.color"></span>
          <span class="lg-label">{{ it.label }}</span>
          <span class="lg-value">{{ it.value }}</span>
          <span class="lg-pct">{{ pct(it.value) }}%</span>
        </li>
      </ul>
    </div>
    <ng-template #vacio>
      <p class="donut__empty">Sin datos para mostrar.</p>
    </ng-template>
  `,
  styles: [`
    .donut {
      display: flex;
      align-items: center;
      gap: 22px;
      flex-wrap: wrap;
    }
    .donut__chart { width: 150px; flex-shrink: 0; }
    .donut__chart svg { width: 100%; height: auto; display: block; }
    .donut__total {
      font-family: var(--font-display);
      font-weight: 800;
      font-size: 22px;
      fill: var(--navy-900);
    }
    .donut__cap {
      font-family: var(--font-body);
      font-weight: 600;
      font-size: 8px;
      fill: var(--ink-soft);
      text-transform: uppercase;
      letter-spacing: .08em;
    }
    .donut__legend {
      list-style: none;
      margin: 0;
      padding: 0;
      flex: 1;
      min-width: 180px;
      display: flex;
      flex-direction: column;
      gap: 9px;
    }
    .donut__legend li {
      display: flex;
      align-items: center;
      gap: 9px;
      font-size: .86rem;
    }
    .donut__legend .dot {
      width: 11px;
      height: 11px;
      border-radius: 4px;
      flex-shrink: 0;
    }
    .lg-label { color: var(--ink); flex: 1; }
    .lg-value {
      font-family: var(--font-display);
      font-weight: 800;
      color: var(--navy-900);
    }
    .lg-pct {
      color: var(--ink-soft);
      font-size: .78rem;
      width: 44px;
      text-align: right;
    }
    .donut__empty {
      color: var(--ink-soft);
      font-size: .88rem;
      padding: 18px 0;
      text-align: center;
    }
  `]
})
export class DonutChartComponent {
  @Input() items: DonutItem[] = [];
  @Input() centro = '';

  readonly radio = 48;
  readonly grosor = 16;
  private readonly gap = 2; // px de separación entre segmentos

  get circunferencia(): number {
    return 2 * Math.PI * this.radio;
  }
  get total(): number {
    return (this.items || []).reduce((a, b) => a + (b.value || 0), 0);
  }
  get ariaLabel(): string {
    return (this.items || [])
      .map(i => `${i.label}: ${i.value}`)
      .join(', ');
  }
  pct(v: number): number {
    return this.total ? Math.round(((v || 0) / this.total) * 100) : 0;
  }
  get segmentos(): Segmento[] {
    const C = this.circunferencia;
    const total = this.total || 1;
    let acumulado = 0;
    const visibles = (this.items || []).filter(i => (i.value || 0) > 0);
    return visibles.map(i => {
      const largo = (i.value / total) * C;
      const dibujo = Math.max(0, largo - this.gap);
      const seg: Segmento = {
        color: i.color,
        dash: `${dibujo} ${C - dibujo}`,
        offset: -acumulado,
      };
      acumulado += largo;
      return seg;
    });
  }
}
