import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { JornadaService } from '../../../../core/services/jornada.service';

@Component({
  selector: 'app-jornada-form',
  templateUrl: './jornada-form.component.html',
  styleUrls: ['./jornada-form.component.scss']
})
export class JornadaFormComponent implements OnInit {
  modoEdicion = false; jornadaId: string | null = null;
  form = { codigo: '', nombre: '', descripcion: '', horaInicio: '', horaFin: '' };
  cargando = false; guardando = false; error = '';

  constructor(private jornadaService: JornadaService, private route: ActivatedRoute, private router: Router) {}

  ngOnInit(): void {
    this.jornadaId = this.route.snapshot.paramMap.get('id');
    this.modoEdicion = !!this.jornadaId;
    if (this.modoEdicion) this.cargar();
  }

  cargar(): void {
    this.cargando = true;
    this.jornadaService.obtener(this.jornadaId!).subscribe({
      next: (j) => {
        this.form = { codigo: j.codigo ?? '', nombre: j.nombre ?? '', descripcion: j.descripcion ?? '',
                      horaInicio: j.horaInicio ?? '', horaFin: j.horaFin ?? '' };
        this.cargando = false;
      },
      error: () => { this.error = 'No se pudo cargar la jornada.'; this.cargando = false; }
    });
  }

  guardar(): void {
    this.error = '';
    if (!this.form.codigo.trim() || !this.form.nombre.trim()) { this.error = 'Código y nombre son obligatorios.'; return; }
    this.guardando = true;
    const p = this.modoEdicion ? this.jornadaService.editar(this.jornadaId!, this.form) : this.jornadaService.crear(this.form);
    p.subscribe({
      next: () => this.router.navigate(['/dashboard/jornadas']),
      error: (err) => { this.guardando = false; this.error = err?.error?.mensaje || 'No se pudo guardar.'; }
    });
  }

  cancelar(): void { this.router.navigate(['/dashboard/jornadas']); }

  /* ══════════ preview en vivo de la franja horaria ══════════ */

  private minutos(hora?: string): number | null {
    if (!hora) return null;
    const [h, m] = hora.split(':').map(Number);
    if (isNaN(h)) return null;
    return h * 60 + (m || 0);
  }

  get tieneHorario(): boolean {
    return this.minutos(this.form.horaInicio) != null && this.minutos(this.form.horaFin) != null;
  }

  get cruzaMedianoche(): boolean {
    const i = this.minutos(this.form.horaInicio), f = this.minutos(this.form.horaFin);
    return i != null && f != null && f <= i;
  }

  get pctInicio(): number {
    const m = this.minutos(this.form.horaInicio);
    return m == null ? 0 : (m / 1440) * 100;
  }

  get pctAncho(): number {
    const i = this.minutos(this.form.horaInicio), f = this.minutos(this.form.horaFin);
    if (i == null || f == null) return 0;
    if (f <= i) return ((1440 - i) / 1440) * 100;
    return ((f - i) / 1440) * 100;
  }

  get pctAnchoMadrugada(): number {
    const f = this.minutos(this.form.horaFin);
    return f == null ? 0 : (f / 1440) * 100;
  }

  get duracionTexto(): string {
    const i = this.minutos(this.form.horaInicio), f = this.minutos(this.form.horaFin);
    if (i == null || f == null) return '';
    const total = f > i ? f - i : (1440 - i) + f;
    const h = Math.floor(total / 60), m = total % 60;
    return m ? `${h} h ${m} min` : `${h} h`;
  }
}
