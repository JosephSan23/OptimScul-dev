import { Component, EventEmitter, Input, OnChanges, OnInit, Output } from '@angular/core';
import { AdministradorService, Administrador, AdministradorRequest } from '../../../core/services/administrador.service';
import { InstitucionService, Institucion } from '../../../core/services/institucion.service';
import { validarEsquema, hayErrores, validarCampo, Esquema, ErroresForm } from '../../../core/validation/form-validator';
import { requerido, documentoPorTipo, soloLetras, longitudMin, correo } from '../../../core/validation/validators';

@Component({
  selector: 'app-administrador-form',
  templateUrl: './administrador-form.component.html',
  styleUrls: ['./administrador-form.component.scss']
})
export class AdministradorFormComponent implements OnInit, OnChanges {

  @Input() administrador: Administrador | null = null;   // null = crear; con valor = editar

  @Output() guardado = new EventEmitter<void>();
  @Output() cancelado = new EventEmitter<void>();

  form: AdministradorRequest = {
    tipoDocumento: '', numeroDocumento: '', primerNombre: '',
    primerApellido: '', correo: '', institucionId: ''
  };

  esquema: Esquema = {
    tipoDocumento: [requerido('Selecciona el tipo de documento')],
    numeroDocumento: [requerido('Ingresa el número de documento'), documentoPorTipo()],
    primerNombre: [requerido('Ingresa el primer nombre'), soloLetras(), longitudMin(2)],
    primerApellido: [requerido('Ingresa el primer apellido'), soloLetras(), longitudMin(2)],
    correo: [requerido('Ingresa el correo'), correo()],
    institucionId: [requerido('Selecciona una institución')]
  };
  errores: ErroresForm = {};

  tiposDocumento = [
    { valor: 'CC', etiqueta: 'Cédula de Ciudadanía' },
    { valor: 'CE', etiqueta: 'Cédula de Extranjería' },
    { valor: 'PASAPORTE', etiqueta: 'Pasaporte' }
  ];

  instituciones: Institucion[] = [];
  guardando = false;
  error = '';
  exito = '';

  constructor(private administradorService: AdministradorService,
              private institucionService: InstitucionService) {}

  ngOnInit(): void {
    this.institucionService.listar().subscribe({
      next: (data) => this.instituciones = data,
      error: () => this.error = 'No se pudieron cargar las instituciones.'
    });
  }

  // Se dispara al abrir edición (cambia el [administrador])
  ngOnChanges(): void {
    if (this.administrador) {
      this.form = {
        tipoDocumento: this.administrador.tipoDocumento || '',
        numeroDocumento: this.administrador.numeroDocumento,
        primerNombre: this.administrador.primerNombre,
        primerApellido: this.administrador.primerApellido,
        correo: this.administrador.correo,
        institucionId: this.administrador.institucionId
      };
      this.error = '';
      this.exito = '';
      this.errores = {};
    }
  }

  get esEdicion(): boolean { return this.administrador !== null; }

  validar(campo: string): void {
    const msg = validarCampo(campo, this.form, this.esquema);
    if (msg) this.errores[campo] = msg;
    else delete this.errores[campo];
  }

  onTipoDocumentoChange(): void {
    this.validar('tipoDocumento');
    if (this.form.numeroDocumento) this.validar('numeroDocumento');
  }

  guardar(): void {
    this.error = '';
    this.exito = '';

    this.errores = validarEsquema(this.form, this.esquema);
    if (hayErrores(this.errores)) {
      this.error = 'Revisa los campos marcados en rojo.';
      return;
    }

    this.guardando = true;
    const peticion = this.esEdicion
      ? this.administradorService.editar(this.administrador!.usuarioId, { ...this.form })
      : this.administradorService.crear({ ...this.form });

    peticion.subscribe({
      next: (res) => {
        this.guardando = false;
        this.exito = res.mensaje;
        if (!this.esEdicion) {
          this.form = { tipoDocumento: '', numeroDocumento: '', primerNombre: '',
                        primerApellido: '', correo: '', institucionId: '' };
          this.errores = {};
        }
        this.guardado.emit();
      },
      error: (err) => {
        this.guardando = false;
        this.error = err?.error?.mensaje || 'No se pudo guardar.';
      }
    });
  }

  cancelar(): void { this.cancelado.emit(); }
}
