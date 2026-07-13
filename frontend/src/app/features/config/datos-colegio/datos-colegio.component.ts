import { Component, OnInit } from '@angular/core';
import {
  InstitucionConfigService,
  InstitucionConfigRequest,
} from '../../../core/services/institucion-config.service';

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

  cargando = false;
  guardando = false;
  error = '';
  exito = '';

  constructor(private institucionConfigService: InstitucionConfigService) {}

  ngOnInit(): void {
    this.cargar();
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
    if (!this.form.nombre?.trim()) {
      this.error = 'El nombre es obligatorio.';
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
