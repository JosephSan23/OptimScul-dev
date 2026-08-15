import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

export interface BarItem {
  label: string;
  value: number;
  color?: string;   // opcional; por defecto usa el azul de datos
  sub?: string;     // subtítulo opcional (ej. grupo/código)
}

/**
 * Gráfico de barras horizontales, sin dependencias.
 * Cada barra lleva etiqueta de categoría + valor directo (secondary encoding),
 * escala a un solo eje contra el máximo del conjunto.
 */
@Component({
  selector: 'app-bar-chart',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="bars" *ngIf="items?.length; else vacio">
      <div class="bar-row" *ngFor="let it of items"
           [attr.title]="it.label + ': ' + it.value + unit">
        <div class="bar-row__head">
          <span class="bar-row__label">
            {{ it.label }}
            <small *ngIf="it.sub">{{ it.sub }}</small>
          </span>
          <span class="bar-row__value">{{ it.value }}{{ unit }}</span>
        </div>
        <div class="bar-row__track">
          <div class="bar-row__fill"
               [style.width.%]="pct(it.value)"
               [style.background]="it.color || defaultColor">
          </div>
        </div>
      </div>
    </div>
    <ng-template #vacio>
      <p class="bars__empty">Sin datos para mostrar.</p>
    </ng-template>
  `,
  styles: [`
    .bars { display: flex; flex-direction: column; gap: 14px; }
    .bar-row__head {
      display: flex;
      align-items: baseline;
      justify-content: space-between;
      margin-bottom: 5px;
      gap: 12px;
    }
    .bar-row__label {
      font-size: .84rem;
      font-weight: 600;
      color: var(--ink);
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
    }
    .bar-row__label small {
      color: var(--ink-soft);
      font-weight: 500;
      margin-left: 6px;
    }
    .bar-row__value {
      font-family: var(--font-display);
      font-weight: 800;
      font-size: .9rem;
      color: var(--navy-900);
      flex-shrink: 0;
    }
    .bar-row__track {
      height: 12px;
      background: var(--line-soft);
      border-radius: 999px;
      overflow: hidden;
    }
    .bar-row__fill {
      height: 100%;
      border-radius: 999px;
      min-width: 6px;
      transition: width .5s cubic-bezier(.4,0,.2,1);
    }
    .bars__empty {
      color: var(--ink-soft);
      font-size: .88rem;
      padding: 18px 0;
      text-align: center;
    }
  `]
})
export class BarChartComponent {
  @Input() items: BarItem[] = [];
  @Input() unit = '';
  @Input() defaultColor = 'var(--data-blue)';

  get max(): number {
    return Math.max(1, ...(this.items || []).map(i => i.value || 0));
  }
  pct(v: number): number {
    return Math.round(((v || 0) / this.max) * 100);
  }
}
