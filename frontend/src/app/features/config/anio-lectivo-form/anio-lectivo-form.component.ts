import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AnioLectivoService } from '../../../core/services/anio-lectivo.service';

@Component({
  selector: 'app-anio-lectivo-form',
  templateUrl: './anio-lectivo-form.component.html',
  styleUrls: ['./anio-lectivo-form.component.scss'],
})
export class AnioLectivoFormComponent implements OnInit {
  modoEdicion = false;
  anioId: string | null = null;
  form: {
    anio: number | null;
    nombre: string;
    descripcion: string;
    fechaInicio: string;
    fechaFin: string;
    estado: string;
  } = {
    anio: null,
    nombre: '',
    descripcion: '',
    fechaInicio: '',
    fechaFin: '',
    estado: 'PLANEACION',
  };
  estados = ['PLANEACION', 'ACTIVO', 'CERRADO', 'CANCELADO'];
  cargando = false;
  guardando = false;
  error = '';

  constructor(
    private anioService: AnioLectivoService,
    private route: ActivatedRoute,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.anioId = this.route.snapshot.paramMap.get('id');
    this.modoEdicion = !!this.anioId;
    if (this.modoEdicion) this.cargar();
  }

  cargar(): void {
    this.cargando = true;
    this.anioService.obtener(this.anioId!).subscribe({
      next: (a) => {
        this.form = {
          anio: a.anio ?? null,
          nombre: a.nombre ?? '',
          descripcion: a.descripcion ?? '',
          fechaInicio: a.fechaInicio ?? '',
          fechaFin: a.fechaFin ?? '',
          estado: a.estado ?? 'PLANEACION',
        };
        this.cargando = false;
      },
      error: () => {
        this.error = 'No se pudo cargar el año lectivo.';
        this.cargando = false;
      },
    });
  }

  guardar(): void {
    this.error = '';
    if (
      !this.form.anio ||
      !this.form.nombre.trim() ||
      !this.form.fechaInicio ||
      !this.form.fechaFin
    ) {
      this.error = 'Año, nombre y fechas son obligatorios.';
      return;
    }
    if (this.fechasInvertidas) {
      this.error = 'La fecha de fin debe ser posterior a la de inicio.';
      return;
    }
    this.guardando = true;
    const p = this.modoEdicion
      ? this.anioService.editar(this.anioId!, this.form)
      : this.anioService.crear(this.form);
    p.subscribe({
      next: () => this.router.navigate(['/dashboard/anios-lectivos']),
      error: (err) => {
        this.guardando = false;
        this.error = err?.error?.mensaje || 'No se pudo guardar.';
      },
    });
  }

  cancelar(): void {
    this.router.navigate(['/dashboard/anios-lectivos']);
  }

  /* ══════════ preview del rango en vivo ══════════ */

  private aFecha(s?: string): Date | null {
    if (!s) return null;
    const d = new Date(s + 'T00:00:00');
    return isNaN(d.getTime()) ? null : d;
  }

  get tieneRango(): boolean {
    return this.aFecha(this.form.fechaInicio) != null && this.aFecha(this.form.fechaFin) != null;
  }

  get fechasInvertidas(): boolean {
    const i = this.aFecha(this.form.fechaInicio), f = this.aFecha(this.form.fechaFin);
    return !!i && !!f && f <= i;
  }

  /** duración del año en días / meses aproximados */
  get duracionTexto(): string {
    const i = this.aFecha(this.form.fechaInicio), f = this.aFecha(this.form.fechaFin);
    if (!i || !f || f <= i) return '';
    const dias = Math.round((f.getTime() - i.getTime()) / 86400000);
    const meses = Math.round(dias / 30.4);
    return `${dias} días · ~${meses} meses`;
  }
}
