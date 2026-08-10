import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ActividadService } from '../../../../../core/services/actividad.service';
import {
  validarEsquema,
  hayErrores,
  validarCampo,
  Esquema,
  ErroresForm,
} from '../../../../../core/validation/form-validator';
import {
  requerido,
  longitudMin,
  rangoNumerico,
  fechaNoAnteriorA,
} from '../../../../../core/validation/validators';

@Component({
  selector: 'app-actividad-form',
  templateUrl: './actividad-form.component.html',
  styleUrls: ['./actividad-form.component.scss']
})
export class ActividadFormComponent implements OnInit {

  readonly TIPOS = [
    { valor: 'TAREA', nombre: 'Tarea' }, { valor: 'QUIZ', nombre: 'Quiz' },
    { valor: 'EXAMEN', nombre: 'Examen' }, { valor: 'TRABAJO', nombre: 'Trabajo' },
    { valor: 'EXPOSICION', nombre: 'Exposición' }, { valor: 'PROYECTO', nombre: 'Proyecto' },
    { valor: 'RECUPERACION', nombre: 'Recuperación' }, { valor: 'OTRA', nombre: 'Otra' }
  ];

  modoEdicion = false;
  cargaId!: string; anio = ''; actividadId: string | null = null;
  form = {
    periodoAcademicoId: '', tipo: 'TAREA', titulo: '', descripcion: '',
    fechaEntrega: '', fechaCierre: '',
    porcentaje: null as number | null, notaMaxima: null as number | null,
    permiteEntregaTardia: false
  };

  esquema: Esquema = {
    titulo: [requerido('Ingresa el título'), longitudMin(3)],
    periodoAcademicoId: [requerido('Falta el periodo académico')],
    porcentaje: [rangoNumerico(0, 100, 'El peso debe estar entre 0 y 100')],
    notaMaxima: [rangoNumerico(0, 100, 'La nota máxima debe estar entre 0 y 100')],
    // La fecha de cierre no puede ir antes de la de entrega.
    fechaCierre: [fechaNoAnteriorA('fechaEntrega', 'La fecha de cierre no puede ser anterior a la de entrega')],
  };
  errores: ErroresForm = {};

  cargando = false; guardando = false; error = '';

  constructor(private actividadService: ActividadService, private route: ActivatedRoute, private router: Router) {}

  ngOnInit(): void {
    this.cargaId = this.route.snapshot.paramMap.get('cargaId')!;
    this.anio = this.route.snapshot.queryParamMap.get('anio') ?? '';
    this.actividadId = this.route.snapshot.paramMap.get('actividadId');
    this.modoEdicion = !!this.actividadId;
    if (this.modoEdicion) this.cargar();
    else this.form.periodoAcademicoId = this.route.snapshot.queryParamMap.get('periodo') ?? '';
  }

  validar(campo: string): void {
    const msg = validarCampo(campo, this.form, this.esquema);
    if (msg) this.errores[campo] = msg;
    else delete this.errores[campo];
  }

  cargar(): void {
    this.cargando = true;
    this.actividadService.obtener(this.actividadId!).subscribe({
      next: (a) => {
        this.form = {
          periodoAcademicoId: a.periodoAcademicoId ?? '', tipo: a.tipo ?? 'TAREA',
          titulo: a.titulo ?? '', descripcion: a.descripcion ?? '',
          fechaEntrega: a.fechaEntrega ?? '', fechaCierre: a.fechaCierre ?? '',
          porcentaje: a.porcentaje ?? null, notaMaxima: a.notaMaxima ?? null,
          permiteEntregaTardia: a.permiteEntregaTardia ?? false
        };
        this.cargando = false;
      },
      error: () => { this.error = 'No se pudo cargar la actividad.'; this.cargando = false; }
    });
  }

  guardar(): void {
    this.error = '';
    this.errores = validarEsquema(this.form, this.esquema);
    if (hayErrores(this.errores)) { this.error = 'Revisa los campos marcados en rojo.'; return; }
    this.guardando = true;
    const body = {
      periodoAcademicoId: this.form.periodoAcademicoId,
      tipo: this.form.tipo,
      titulo: this.form.titulo,
      descripcion: this.form.descripcion,
      fechaEntrega: this.form.fechaEntrega || null,
      fechaCierre: this.form.fechaCierre || null,
      porcentaje: this.form.porcentaje,
      notaMaxima: this.form.notaMaxima,
      permiteEntregaTardia: this.form.permiteEntregaTardia
    };
    const p = this.modoEdicion ? this.actividadService.editar(this.actividadId!, body) : this.actividadService.crear(this.cargaId, body);
    p.subscribe({
      next: () => this.volver(),
      error: (err) => { this.guardando = false; this.error = err?.error?.mensaje || 'No se pudo guardar.'; }
    });
  }

  volver(): void {
    this.router.navigate(['/dashboard/mis-clases', this.cargaId, 'actividades'], { queryParams: { anio: this.anio } });
  }
}
