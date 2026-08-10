import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MatriculaService } from '../../../../core/services/matricula.service';
import {
  AnioLectivoService,
  AnioLectivo,
} from '../../../../core/services/anio-lectivo.service';
import { GrupoService, Grupo } from '../../../../core/services/grupo.service';
import {
  EstudianteService,
  Estudiante,
} from '../../../../core/services/estudiante.service';
import {
  validarEsquema,
  hayErrores,
  validarCampo,
  Esquema,
  ErroresForm,
} from '../../../../core/validation/form-validator';
import { requerido, fechaNoFutura } from '../../../../core/validation/validators';

@Component({
  selector: 'app-matricula-form',
  templateUrl: './matricula-form.component.html',
  styleUrls: ['./matricula-form.component.scss'],
})
export class MatriculaFormComponent implements OnInit {
  readonly TIPOS = ['NUEVA', 'RENOVACION', 'TRASLADO', 'REINTEGRO'];

  form = {
    estudianteId: '',
    anioLectivoId: '',
    tipo: 'NUEVA',
    grupoId: '',
    fechaMatricula: '',
    observaciones: '',
  };

  esquema: Esquema = {
    estudianteId: [requerido('Selecciona el estudiante')],
    anioLectivoId: [requerido('Selecciona el año lectivo')],
    tipo: [requerido('Selecciona el tipo')],
    fechaMatricula: [fechaNoFutura('La fecha de matrícula no puede ser futura')],
  };
  errores: ErroresForm = {};

  anios: AnioLectivo[] = [];
  estudiantes: Estudiante[] = [];
  grupos: Grupo[] = [];
  guardando = false;
  error = '';

  constructor(
    private matriculaService: MatriculaService,
    private anioService: AnioLectivoService,
    private grupoService: GrupoService,
    private estudianteService: EstudianteService,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.estudianteService.listar().subscribe({
      next: (d) => (this.estudiantes = d.filter((e) => e.estado === 'ACTIVO')),
      error: () => {},
    });
    this.anioService.listar().subscribe({
      next: (d) => {
        this.anios = d;
        const actual = d.find((a) => a.esActual);
        if (actual) {
          this.form.anioLectivoId = actual.id;
          this.cargarGrupos();
        }
      },
      error: () => {
        this.error = 'No se pudieron cargar los años lectivos.';
      },
    });
  }

  validar(campo: string): void {
    const msg = validarCampo(campo, this.form, this.esquema);
    if (msg) this.errores[campo] = msg;
    else delete this.errores[campo];
  }

  cambioAnio(): void {
    this.form.grupoId = '';
    this.validar('anioLectivoId');
    this.cargarGrupos();
  }

  cargarGrupos(): void {
    if (!this.form.anioLectivoId) {
      this.grupos = [];
      return;
    }
    this.grupoService.listarPorAnio(this.form.anioLectivoId).subscribe({
      next: (d) => (this.grupos = d.filter((g) => g.estado === 'ACTIVO')),
      error: () => {},
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
    const body = {
      estudianteId: this.form.estudianteId,
      anioLectivoId: this.form.anioLectivoId,
      tipo: this.form.tipo,
      grupoId: this.form.grupoId || null,
      fechaMatricula: this.form.fechaMatricula || null,
      observaciones: this.form.observaciones,
    };
    this.matriculaService.crear(body).subscribe({
      next: () => this.router.navigate(['/dashboard/matriculas']),
      error: (err) => {
        this.guardando = false;
        this.error = err?.error?.mensaje || 'No se pudo crear la matrícula.';
      },
    });
  }

  cancelar(): void {
    this.router.navigate(['/dashboard/matriculas']);
  }
}
