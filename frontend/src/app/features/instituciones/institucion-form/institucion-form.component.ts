import { Component, Input, Output, EventEmitter, OnInit } from '@angular/core';
import { InstitucionService, Institucion, InstitucionRequest } from '../../../core/services/institucion.service';
import {
  validarEsquema,
  hayErrores,
  validarCampo,
  Esquema,
  ErroresForm,
} from '../../../core/validation/form-validator';
import {
  requerido,
  longitudMin,
  correo,
  telefonoCo,
} from '../../../core/validation/validators';

@Component({
  selector: 'app-institucion-form',
  templateUrl: './institucion-form.component.html',
  styleUrls: ['./institucion-form.component.scss']
})
export class InstitucionFormComponent implements OnInit {

  // ENTRA: la institución a editar. Si es null, es modo "crear".
  @Input() institucion: Institucion | null = null;

  // SALEN: avisos hacia el padre
  @Output() guardado = new EventEmitter<void>();   // "ya guardé, refresca"
  @Output() cancelado = new EventEmitter<void>();   // "cerré sin guardar"

  form: InstitucionRequest = { codigo: '', nombre: '', tipoInstitucion: 'COLEGIO' };
  tipos = ['COLEGIO', 'JARDIN', 'INSTITUTO', 'ACADEMIA', 'OTRA'];
  guardando = false;
  errorForm = '';

  esquema: Esquema = {
    codigo: [requerido('El código es obligatorio')],
    nombre: [requerido('El nombre es obligatorio'), longitudMin(2)],
    tipoInstitucion: [requerido('Selecciona el tipo')],
    correoContacto: [correo()],
    telefonoContacto: [telefonoCo()],
  };
  errores: ErroresForm = {};

  constructor(private institucionService: InstitucionService) {}

  ngOnInit(): void {
    this.errores = {};
    // Si me pasaron una institución, precargo sus datos en el formulario
    if (this.institucion) {
      this.form = {
        codigo: this.institucion.codigo,
        nombre: this.institucion.nombre,
        nombreCorto: this.institucion.nombreCorto,
        tipoInstitucion: this.institucion.tipoInstitucion,
        nit: this.institucion.nit,
        dane: this.institucion.dane,
        resolucionFuncionamiento: this.institucion.resolucionFuncionamiento,
        descripcion: this.institucion.descripcion,
        correoContacto: this.institucion.correoContacto,
        telefonoContacto: this.institucion.telefonoContacto,
        sitioWeb: this.institucion.sitioWeb,
        dominioCorreo: this.institucion.dominioCorreo,
        direccionPrincipal: this.institucion.direccionPrincipal,
        ciudad: this.institucion.ciudad,
        departamento: this.institucion.departamento,
        pais: this.institucion.pais,
        logoUrl: this.institucion.logoUrl,
        zonaHoraria: this.institucion.zonaHoraria,
        moneda: this.institucion.moneda
      };
    }
  }

  get esEdicion(): boolean {
    return this.institucion !== null;
  }

  validar(campo: string): void {
    const msg = validarCampo(campo, this.form, this.esquema);
    if (msg) this.errores[campo] = msg;
    else delete this.errores[campo];
  }

  guardar(): void {
    this.errorForm = '';
    this.errores = validarEsquema(this.form, this.esquema);
    if (hayErrores(this.errores)) {
      this.errorForm = 'Revisa los campos marcados en rojo.';
      return;
    }
    this.guardando = true;

    const peticion = this.esEdicion
      ? this.institucionService.editar(this.institucion!.id, this.form)
      : this.institucionService.crear(this.form);

    peticion.subscribe({
      next: () => {
        this.guardando = false;
        this.guardado.emit();   // avisa al padre: "ya guardé"
      },
      error: (err) => {
        this.guardando = false;
        this.errorForm = err?.error?.mensaje || 'No se pudo guardar.';
      }
    });
  }

  cancelar(): void {
    this.cancelado.emit();   // avisa al padre: "cerré"
  }

  etiquetaTipo(tipo: string): string {
    switch (tipo) {
      case 'COLEGIO':   return 'Colegio';
      case 'JARDIN':    return 'Jardín';
      case 'INSTITUTO': return 'Instituto';
      case 'ACADEMIA':  return 'Academia';
      default:          return 'Otra';
    }
  }
}
