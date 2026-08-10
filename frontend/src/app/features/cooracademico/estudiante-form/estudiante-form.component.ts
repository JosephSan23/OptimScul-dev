import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import {
  EstudianteService,
  EditarEstudianteRequest,
} from '../../../core/services/estudiante.service';
import {
  validarEsquema,
  hayErrores,
  validarCampo,
  Esquema,
  ErroresForm,
} from '../../../core/validation/form-validator';
import {
  requerido,
  documentoPorTipo,
  soloLetras,
  longitudMin,
  correo,
  telefonoCo,
  fechaNacimiento,
} from '../../../core/validation/validators';

@Component({
  selector: 'app-estudiante-form',
  templateUrl: './estudiante-form.component.html',
  styleUrls: ['./estudiante-form.component.scss'],
})
export class EstudianteFormComponent implements OnInit {
  modoEdicion = false;
  estudianteId: string | null = null;
  codigo = '';
  form: any = {
    tipoDocumento: '',
    numeroDocumento: '',
    primerNombre: '',
    segundoNombre: '',
    primerApellido: '',
    segundoApellido: '',
    correo: '',
    telefono: '',
    fechaNacimiento: '',
    direccion: '',
    ciudad: '',
    fechaIngreso: '',
    estado: 'ACTIVO',
    observaciones: '',
  };

  // ── Validación reutilizable ──────────────────────────────
  // Un solo lugar donde viven las reglas de este formulario.
  // Los validadores de formato ignoran el valor vacío, así que
  // los campos opcionales solo se validan cuando traen contenido.
  esquema: Esquema = {
    tipoDocumento: [requerido('Selecciona el tipo de documento')],
    // El número se valida SEGÚN el tipo elegido (RC/TI/CC numéricos con
    // rangos reales; CE/Pasaporte alfanuméricos). Ver REGLAS_DOCUMENTO.
    numeroDocumento: [requerido('Ingresa el número de documento'), documentoPorTipo()],
    primerNombre: [
      requerido('Ingresa el primer nombre'),
      soloLetras(),
      longitudMin(2),
    ],
    segundoNombre: [soloLetras()],
    primerApellido: [
      requerido('Ingresa el primer apellido'),
      soloLetras(),
      longitudMin(2),
    ],
    segundoApellido: [soloLetras()],
    correo: [correo()],
    telefono: [telefonoCo()],
    // No futura y con edad máxima de 100 años.
    fechaNacimiento: [fechaNacimiento()],
  };
  errores: ErroresForm = {};

  tiposDocumento = [
    { valor: 'RC', etiqueta: 'Registro Civil' },
    { valor: 'TI', etiqueta: 'Tarjeta de Identidad' },
    { valor: 'CC', etiqueta: 'Cédula de Ciudadanía' },
    { valor: 'CE', etiqueta: 'Cédula de Extranjería' },
    { valor: 'PASAPORTE', etiqueta: 'Pasaporte' },
  ];
  estados = ['ACTIVO', 'RETIRADO', 'GRADUADO', 'INACTIVO'];
  cargando = false;
  guardando = false;
  error = '';
  exito = '';

  constructor(
    private estudianteService: EstudianteService,
    private route: ActivatedRoute,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.estudianteId = this.route.snapshot.paramMap.get('id');
    this.modoEdicion = !!this.estudianteId;
    if (this.modoEdicion) this.cargar();
  }

  // Revalida un campo al salir de él (feedback inmediato sin ser molesto).
  validar(campo: string): void {
    const msg = validarCampo(campo, this.form, this.esquema);
    if (msg) this.errores[campo] = msg;
    else delete this.errores[campo];
  }

  // Al cambiar el tipo de documento, el formato válido del número cambia,
  // así que revalidamos el número si ya tenía contenido.
  onTipoDocumentoChange(): void {
    this.validar('tipoDocumento');
    if (this.form.numeroDocumento) this.validar('numeroDocumento');
  }

  cargar(): void {
    this.cargando = true;
    this.estudianteService.obtener(this.estudianteId!).subscribe({
      next: (d) => {
        this.codigo = d.codigoEstudiante;
        this.form = {
          tipoDocumento: d.tipoDocumento ?? '',
          numeroDocumento: d.numeroDocumento ?? '',
          primerNombre: d.primerNombre ?? '',
          segundoNombre: d.segundoNombre ?? '',
          primerApellido: d.primerApellido ?? '',
          segundoApellido: d.segundoApellido ?? '',
          correo: d.correo ?? '',
          telefono: d.telefono ?? '',
          fechaNacimiento: d.fechaNacimiento ?? '',
          direccion: d.direccion ?? '',
          ciudad: d.ciudad ?? '',
          fechaIngreso: d.fechaIngreso ?? '',
          estado: d.estado ?? 'ACTIVO',
          observaciones: d.observaciones ?? '',
        };
        this.cargando = false;
      },
      error: () => {
        this.error = 'No se pudo cargar el estudiante.';
        this.cargando = false;
      },
    });
  }

  guardar(): void {
    this.error = '';
    this.exito = '';

    // Validación centralizada: una sola línea reemplaza el if manual.
    this.errores = validarEsquema(this.form, this.esquema);
    if (hayErrores(this.errores)) {
      this.error = 'Revisa los campos marcados en rojo.';
      return;
    }

    this.guardando = true;
    if (this.modoEdicion) {
      this.estudianteService
        .editar(this.estudianteId!, this.form as EditarEstudianteRequest)
        .subscribe({
          next: () => this.router.navigate(['/dashboard/estudiantes']),
          error: (err) => {
            this.guardando = false;
            this.error = err?.error?.mensaje || 'No se pudo guardar.';
          },
        });
    } else {
      this.estudianteService
        .crear({
          tipoDocumento: this.form.tipoDocumento,
          numeroDocumento: this.form.numeroDocumento,
          primerNombre: this.form.primerNombre,
          primerApellido: this.form.primerApellido,
          correo: this.form.correo,
          fechaIngreso: this.form.fechaIngreso,
          observaciones: this.form.observaciones,
        })
        .subscribe({
          next: (res) => {
            this.guardando = false;
            this.exito = res.mensaje;
            this.errores = {};
            this.form = {
              tipoDocumento: '',
              numeroDocumento: '',
              primerNombre: '',
              segundoNombre: '',
              primerApellido: '',
              segundoApellido: '',
              correo: '',
              telefono: '',
              fechaNacimiento: '',
              direccion: '',
              ciudad: '',
              fechaIngreso: '',
              estado: 'ACTIVO',
              observaciones: '',
            };
          },
          error: (err) => {
            this.guardando = false;
            this.error =
              err?.error?.mensaje || 'No se pudo crear el estudiante.';
          },
        });
    }
  }

  volver(): void {
    this.router.navigate(['/dashboard/estudiantes']);
  }
}
