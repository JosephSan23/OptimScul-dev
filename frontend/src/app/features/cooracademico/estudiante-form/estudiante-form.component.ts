import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import {
  EstudianteService,
  EditarEstudianteRequest,
} from '../../../core/services/estudiante.service';

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
    if (
      !this.form.tipoDocumento ||
      !this.form.numeroDocumento.trim() ||
      !this.form.primerNombre.trim() ||
      !this.form.primerApellido.trim()
    ) {
      this.error = 'Tipo/número de documento y nombres son obligatorios.';
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
