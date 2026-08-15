import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

/**
 * Tarjeta KPI reutilizable del sistema OptimScul.
 * Muestra un valor grande con icono, etiqueta y una pista opcional.
 * Los colores salen de los tokens del sistema (styles.scss).
 */
@Component({
  selector: 'app-kpi-card',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="kpi">
      <div class="kpi__icon" [ngClass]="'tone-' + tone">
        <i class="ti {{ icon }}"></i>
      </div>
      <div class="kpi__body">
        <span class="kpi__value">
          {{ value }}<small *ngIf="unit">{{ unit }}</small>
        </span>
        <span class="kpi__label">{{ label }}</span>
        <span class="kpi__hint" *ngIf="hint">{{ hint }}</span>
      </div>
    </div>
  `,
  styles: [`
    .kpi {
      display: flex;
      align-items: center;
      gap: 16px;
      background: var(--paper);
      border: 1px solid var(--line);
      border-radius: var(--r-card);
      box-shadow: var(--shadow-1);
      padding: 18px 20px;
      min-height: 92px;
      transition: transform .2s ease, box-shadow .2s ease;
    }
    .kpi:hover { transform: translateY(-2px); box-shadow: var(--shadow-2); }

    .kpi__icon {
      width: 48px;
      height: 48px;
      border-radius: 14px;
      flex-shrink: 0;
      display: grid;
      place-items: center;
      box-shadow: var(--clay-ico);
    }
    .kpi__icon i { font-size: 24px; line-height: 1; }

    .tone-navy   { background: var(--navy-100);   color: var(--navy-700); }
    .tone-orange { background: var(--orange-100); color: var(--orange-600); }
    .tone-green  { background: var(--green-100);  color: var(--green-500); }
    .tone-blue   { background: var(--data-blue-soft); color: var(--data-blue); }
    .tone-red    { background: var(--red-100);    color: var(--red-500); }

    .kpi__body { display: flex; flex-direction: column; min-width: 0; }
    .kpi__value {
      font-family: var(--font-display);
      font-weight: 800;
      font-size: 1.7rem;
      color: var(--navy-900);
      line-height: 1.05;
      letter-spacing: -.01em;
    }
    .kpi__value small {
      font-size: .95rem;
      font-weight: 700;
      color: var(--ink-soft);
      margin-left: 3px;
    }
    .kpi__label {
      font-size: .82rem;
      font-weight: 700;
      color: var(--ink-soft);
      margin-top: 2px;
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
    }
    .kpi__hint {
      font-size: .74rem;
      color: var(--ink-soft);
      margin-top: 3px;
      opacity: .9;
    }
  `]
})
export class KpiCardComponent {
  @Input() icon = 'ti-chart-bar';
  @Input() label = '';
  @Input() value: string | number = '—';
  @Input() unit = '';
  @Input() hint = '';
  @Input() tone: 'navy' | 'orange' | 'green' | 'blue' | 'red' = 'navy';
}
