import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import {
  AcudienteService,
  EditarAcudienteRequest,
} from '../../../../core/services/acudiente.service';

@Component({
  selector: 'app-acudiente-form',
  templateUrl: './acudiente-form.component.html',
  styleUrls: ['./acudiente-form.component.scss'],
})
export class AcudienteFormComponent implements OnInit {
  modoEdicion = false;
  estudianteId = '';
  vinculoId: string | null = null;
  form: any = {
    tipoDocumento: '',
    numeroDocumento: '',
    primerNombre: '',
    segundoNombre: '',
    primerApellido: '',
    segundoApellido: '',
    correo: '',
    telefono: '',
    ocupacion: '',
    empresa: '',
    estado: 'ACTIVO',
    parentesco: '',
    esPrincipal: false,
    autorizadoRecogida: false,
    autorizadoInfoAcademica: false,
  };
  tiposDocumento = [
    { valor: 'CC', etiqueta: 'Cédula de Ciudadanía' },
    { valor: 'CE', etiqueta: 'Cédula de Extranjería' },
    { valor: 'PASAPORTE', etiqueta: 'Pasaporte' },
  ];
  parentescos = [
    'MADRE',
    'PADRE',
    'ABUELA',
    'ABUELO',
    'TIA',
    'TIO',
    'HERMANA',
    'HERMANO',
    'PRIMA',
    'PRIMO',
    'ACUDIENTE_LEGAL',
    'OTRO',
  ];
  estados = ['ACTIVO', 'INACTIVO'];
  cargando = false;
  guardando = false;
  error = '';
  exito = '';

  constructor(
    private acudienteService: AcudienteService,
    private route: ActivatedRoute,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.estudianteId = this.route.snapshot.paramMap.get('estudianteId') || '';
    this.vinculoId = this.route.snapshot.paramMap.get('vinculoId');
    this.modoEdicion = !!this.vinculoId;
    if (this.modoEdicion) this.cargar();
  }

  cargar(): void {
    this.cargando = true;
    this.acudienteService.obtener(this.vinculoId!).subscribe({
      next: (d) => {
        this.form = {
          tipoDocumento: d.tipoDocumento ?? '',
          numeroDocumento: d.numeroDocumento ?? '',
          primerNombre: d.primerNombre ?? '',
          segundoNombre: d.segundoNombre ?? '',
          primerApellido: d.primerApellido ?? '',
          segundoApellido: d.segundoApellido ?? '',
          correo: d.correo ?? '',
          telefono: d.telefono ?? '',
          ocupacion: d.ocupacion ?? '',
          empresa: d.empresa ?? '',
          estado: d.estado ?? 'ACTIVO',
          parentesco: d.parentesco ?? '',
          esPrincipal: !!d.esPrincipal,
          autorizadoRecogida: !!d.autorizadoRecogida,
          autorizadoInfoAcademica: !!d.autorizadoInfoAcademica,
        };
        this.cargando = false;
      },
      error: () => {
        this.error = 'No se pudo cargar el acudiente.';
        this.cargando = false;
      },
    });
  }

  guardar(): void {
    this.error = '';
    this.exito = '';
    if (
      !this.form.tipoDocumento ||
      !this.form.numeroDocumento.trim() ||
      !this.form.primerNombre.trim() ||
      !this.form.primerApellido.trim() ||
      !this.form.parentesco
    ) {
      this.error = 'Documento, nombres y parentesco son obligatorios.';
      return;
    }
    this.guardando = true;
    if (this.modoEdicion) {
      this.acudienteService
        .editar(this.vinculoId!, this.form as EditarAcudienteRequest)
        .subscribe({
          next: () => this.volver(),
          error: (err) => {
            this.guardando = false;
            this.error = err?.error?.mensaje || 'No se pudo guardar.';
          },
        });
    } else {
      this.acudienteService
        .crearVincular(this.estudianteId, {
          tipoDocumento: this.form.tipoDocumento,
          numeroDocumento: this.form.numeroDocumento,
          primerNombre: this.form.primerNombre,
          primerApellido: this.form.primerApellido,
          correo: this.form.correo,
          ocupacion: this.form.ocupacion,
          empresa: this.form.empresa,
          parentesco: this.form.parentesco,
          esPrincipal: this.form.esPrincipal,
          autorizadoRecogida: this.form.autorizadoRecogida,
          autorizadoInfoAcademica: this.form.autorizadoInfoAcademica,
        })
        .subscribe({
          next: (res) => {
            this.guardando = false;
            this.exito = res.mensaje;
          },
          error: (err) => {
            this.guardando = false;
            this.error =
              err?.error?.mensaje || 'No se pudo crear el acudiente.';
          },
        });
    }
  }

  volver(): void {
    this.router.navigate([
      '/dashboard/estudiantes',
      this.estudianteId,
      'acudientes',
    ]);
  }
}
