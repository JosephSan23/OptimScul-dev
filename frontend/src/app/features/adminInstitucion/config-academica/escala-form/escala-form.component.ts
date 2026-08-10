import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { EscalaService } from '../../../../core/services/escala.service';
import { ConfigAcademicaService } from '../../../../core/services/config-academica.service';
import {
  validarEsquema,
  hayErrores,
  validarCampo,
  Esquema,
  ErroresForm,
} from '../../../../core/validation/form-validator';
import {
  requerido,
  rangoNumerico,
} from '../../../../core/validation/validators';


@Component({
  selector: 'app-escala-form',
  templateUrl: './escala-form.component.html',
  styleUrls: ['./escala-form.component.scss'],
})
export class EscalaFormComponent implements OnInit {
  modoEdicion = false;
  escalaId: string | null = null;
  form = {
    nombre: '',
    abreviatura: '',
    notaMinima: null as number | null,
    notaMaxima: null as number | null,
    aprueba: false,
    orden: null as number | null,
  };
  cargando = false;
  guardando = false;
  error = '';
  rangoMin = 0;
  rangoMax = 5;
  rangoCargado = false;

  esquema: Esquema = {
    nombre: [requerido('El nombre es obligatorio')],
    notaMinima: [
      requerido('La nota mínima es obligatoria'),
      rangoNumerico(0, 100, 'La nota mínima debe ser un número entre 0 y 100'),
    ],
    notaMaxima: [
      requerido('La nota máxima es obligatoria'),
      rangoNumerico(0, 100, 'La nota máxima debe ser un número entre 0 y 100'),
      (valor, todos) => {
        if (valor == null || todos?.['notaMinima'] == null) return null;
        return Number(valor) > Number(todos['notaMinima'])
          ? null
          : 'La nota máxima debe ser mayor que la mínima.';
      },
    ],
    orden: [
      requerido('El orden es obligatorio'),
      rangoNumerico(1, 50, 'El orden debe ser un número entre 1 y 50'),
    ],
  };
  errores: ErroresForm = {};

  constructor(
    private escalaService: EscalaService,
    private route: ActivatedRoute,
    private router: Router,
    private configService: ConfigAcademicaService
  ) {}

  ngOnInit(): void {
    this.escalaId = this.route.snapshot.paramMap.get('id');
    this.modoEdicion = !!this.escalaId;
    if (this.modoEdicion) this.cargar();
    this.configService.obtener().subscribe({
      next: (c) => { this.rangoMin = c.notaMinima; this.rangoMax = c.notaMaxima; this.rangoCargado = true; },
      error: () => {}
    });
  }

  validar(campo: string): void {
    const msg = validarCampo(campo, this.form, this.esquema);
    if (msg) this.errores[campo] = msg;
    else delete this.errores[campo];
  }

  cargar(): void {
    this.cargando = true;
    this.escalaService.obtener(this.escalaId!).subscribe({
      next: (e) => {
        this.form = {
          nombre: e.nombre ?? '',
          abreviatura: e.abreviatura ?? '',
          notaMinima: e.notaMinima,
          notaMaxima: e.notaMaxima,
          aprueba: e.aprueba,
          orden: e.orden,
        };
        this.cargando = false;
      },
      error: () => {
        this.error = 'No se pudo cargar la banda.';
        this.cargando = false;
      },
    });
  }

  guardar(): void {
    this.error = '';
    this.errores = validarEsquema(this.form, this.esquema);
    if (hayErrores(this.errores)) {
      this.error = 'Revisa los campos marcados en rojo.';
      return;
    }
    if (this.rangoCargado && (this.form.notaMinima! < this.rangoMin || this.form.notaMaxima! > this.rangoMax)) {
      this.error = `La banda debe estar entre ${this.rangoMin} y ${this.rangoMax} (rango de la configuración).`;
      return;
    }
    this.guardando = true;
    const body = {
      nombre: this.form.nombre,
      abreviatura: this.form.abreviatura,
      notaMinima: this.form.notaMinima,
      notaMaxima: this.form.notaMaxima,
      aprueba: this.form.aprueba,
      orden: this.form.orden,
    };
    const p = this.modoEdicion
      ? this.escalaService.editar(this.escalaId!, body)
      : this.escalaService.crear(body);
    p.subscribe({
      next: () => this.router.navigate(['/dashboard/escalas']),
      error: (err) => {
        this.guardando = false;
        this.error = err?.error?.mensaje || 'No se pudo guardar.';
      },
    });
  }

  cancelar(): void {
    this.router.navigate(['/dashboard/escalas']);
  }
}
