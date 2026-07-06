import { Component, EventEmitter, Input, OnChanges, OnInit, Output } from '@angular/core';
import { AdministradorService, Administrador, AdministradorRequest } from '../../../core/services/administrador.service';
import { InstitucionService, Institucion } from '../../../core/services/institucion.service';

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
    }
  }

  get esEdicion(): boolean { return this.administrador !== null; }

  guardar(): void {
    this.error = '';
    this.exito = '';

    if (!this.form.tipoDocumento || !this.form.numeroDocumento.trim() ||
        !this.form.primerNombre.trim() || !this.form.primerApellido.trim() ||
        !this.form.correo.trim() || !this.form.institucionId) {
      this.error = 'Completa todos los campos.';
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
