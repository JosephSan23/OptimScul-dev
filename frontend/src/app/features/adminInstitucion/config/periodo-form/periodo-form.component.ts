import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PeriodoService } from '../../../../core/services/periodo.service';
import {
  validarEsquema,
  hayErrores,
  validarCampo,
  Esquema,
  ErroresForm,
} from '../../../../core/validation/form-validator';
import {
  requerido,
  longitudMin,
  rangoNumerico,
  fechaNoAnteriorA,
} from '../../../../core/validation/validators';

@Component({
  selector: 'app-periodo-form',
  templateUrl: './periodo-form.component.html',
  styleUrls: ['./periodo-form.component.scss']
})
export class PeriodoFormComponent implements OnInit {
  modoEdicion = false;
  periodoId: string | null = null;
  anioId = '';

  form: { numero: number | null; nombre: string; descripcion: string; fechaInicio: string; fechaFin: string; peso: number | null; estado: string } = {
    numero: null, nombre: '', descripcion: '', fechaInicio: '', fechaFin: '', peso: null, estado: 'PLANEADO'
  };

  esquema: Esquema = {
    numero: [requerido('El número es obligatorio')],
    nombre: [requerido('Ingresa el nombre'), longitudMin(2)],
    fechaInicio: [requerido('Selecciona la fecha de inicio')],
    fechaFin: [
      requerido('Selecciona la fecha de fin'),
      fechaNoAnteriorA('fechaInicio', 'La fecha de fin no puede ser anterior a la de inicio'),
    ],
    peso: [rangoNumerico(0, 100, 'El peso debe estar entre 0 y 100')],
  };
  errores: ErroresForm = {};

  estados = ['PLANEADO', 'ACTIVO', 'CERRADO', 'ANULADO'];
  cargando = false; guardando = false; error = '';

  constructor(private periodoService: PeriodoService, private route: ActivatedRoute, private router: Router) {}

  ngOnInit(): void {
    this.periodoId = this.route.snapshot.paramMap.get('id');
    if (this.periodoId) {
      this.modoEdicion = true;
      this.cargar();
    } else {
      this.anioId = this.route.snapshot.paramMap.get('anioId') || '';
    }
  }

  validar(campo: string): void {
    const msg = validarCampo(campo, this.form, this.esquema);
    if (msg) this.errores[campo] = msg;
    else delete this.errores[campo];
  }

  cargar(): void {
    this.cargando = true;
    this.periodoService.obtener(this.periodoId!).subscribe({
      next: (p) => {
        this.anioId = p.anioLectivoId;
        this.form = {
          numero: p.numero ?? null, nombre: p.nombre ?? '', descripcion: p.descripcion ?? '',
          fechaInicio: p.fechaInicio ?? '', fechaFin: p.fechaFin ?? '', peso: p.peso ?? null, estado: p.estado ?? 'PLANEADO'
        };
        this.cargando = false;
      },
      error: () => { this.error = 'No se pudo cargar el periodo.'; this.cargando = false; }
    });
  }

  guardar(): void {
    this.error = '';
    this.errores = validarEsquema(this.form, this.esquema);
    if (hayErrores(this.errores)) {
      this.error = 'Revisa los campos marcados en rojo.';
      return;
    }
    this.guardando = true;
    const p = this.modoEdicion
      ? this.periodoService.editar(this.periodoId!, this.form)
      : this.periodoService.crear(this.anioId, this.form);
    p.subscribe({
      next: () => this.volver(),
      error: (err) => { this.guardando = false; this.error = err?.error?.mensaje || 'No se pudo guardar.'; }
    });
  }

  volver(): void { this.router.navigate(['/dashboard/anios-lectivos', this.anioId, 'periodos']); }
  cancelar(): void { this.volver(); }

  /* ══════════ anillo de peso en vivo ══════════ */
  get pesoValido(): number {
    const v = this.form.peso;
    if (v == null || isNaN(v)) return 0;
    return Math.min(100, Math.max(0, v));
  }

  /** conic-gradient para el anillo, se llena según el peso */
  get pesoAnillo(): string {
    const g = this.pesoValido * 3.6; // % → grados
    return `conic-gradient(var(--orange-500) ${g}deg, var(--navy-100) ${g}deg)`;
  }
}
