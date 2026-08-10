import { Component, OnInit } from '@angular/core';
import {
  InstitucionConfigService,
  InstitucionConfigRequest,
} from '../../../../core/services/institucion-config.service';
import {
  validarEsquema,
  hayErrores,
  validarCampo,
  Esquema,
  ErroresForm,
} from '../../../../core/validation/form-validator';
import { requerido, correo, telefonoCo } from '../../../../core/validation/validators';

@Component({
  selector: 'app-datos-colegio',
  templateUrl: './datos-colegio.component.html',
  styleUrls: ['./datos-colegio.component.scss'],
})
export class DatosColegioComponent implements OnInit {
  // solo lectura
  info = { codigo: '', tipoInstitucion: '', estado: '', dominioCorreo: '' };

  form: InstitucionConfigRequest = {
    nombre: '',
    nombreCorto: '',
    descripcion: '',
    nit: '',
    dane: '',
    resolucionFuncionamiento: '',
    correoContacto: '',
    telefonoContacto: '',
    sitioWeb: '',
    direccionPrincipal: '',
    ciudad: '',
    departamento: '',
    pais: '',
    zonaHoraria: '',
    moneda: '',
  };

  esquema: Esquema = {
    nombre: [requerido('El nombre es obligatorio')],
    correoContacto: [correo()],
    telefonoContacto: [telefonoCo()],
  };
  errores: ErroresForm = {};

  cargando = false;
  guardando = false;
  error = '';
  exito = '';

  constructor(private institucionConfigService: InstitucionConfigService) {}

  ngOnInit(): void {
    this.cargar();
  }

  validar(campo: string): void {
    const msg = validarCampo(campo, this.form, this.esquema);
    if (msg) this.errores[campo] = msg;
    else delete this.errores[campo];
  }

  cargar(): void {
    this.cargando = true;
    this.institucionConfigService.miInstitucion().subscribe({
      next: (i) => {
        this.info = {
          codigo: i.codigo,
          tipoInstitucion: i.tipoInstitucion,
          estado: i.estado,
          dominioCorreo: i.dominioCorreo ?? '—',
        };
        this.form = {
          nombre: i.nombre ?? '',
          nombreCorto: i.nombreCorto ?? '',
          descripcion: i.descripcion ?? '',
          nit: i.nit ?? '',
          dane: i.dane ?? '',
          resolucionFuncionamiento: i.resolucionFuncionamiento ?? '',
          correoContacto: i.correoContacto ?? '',
          telefonoContacto: i.telefonoContacto ?? '',
          sitioWeb: i.sitioWeb ?? '',
          direccionPrincipal: i.direccionPrincipal ?? '',
          ciudad: i.ciudad ?? '',
          departamento: i.departamento ?? '',
          pais: i.pais ?? '',
          zonaHoraria: i.zonaHoraria ?? '',
          moneda: i.moneda ?? '',
        };
        this.cargando = false;
      },
      error: () => {
        this.error = 'No se pudieron cargar los datos.';
        this.cargando = false;
      },
    });
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
    this.institucionConfigService.guardar(this.form).subscribe({
      next: () => {
        this.guardando = false;
        this.exito = 'Datos actualizados.';
      },
      error: (err) => {
        this.guardando = false;
        this.error = err?.error?.mensaje || 'No se pudo guardar.';
      },
    });
  }
}
