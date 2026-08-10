import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import {
  AcudienteService,
  AcudienteDeEstudiante,
  EditarAcudienteRequest,
} from '../../../../core/services/acudiente.service';
import { validarEsquema, hayErrores, validarCampo, Esquema, ErroresForm } from '../../../../core/validation/form-validator';
import { requerido, documentoPorTipo, soloLetras, longitudMin, correo, telefonoCo } from '../../../../core/validation/validators';

@Component({
  selector: 'app-acudiente-form',
  templateUrl: './acudiente-form.component.html',
  styleUrls: ['./acudiente-form.component.scss'],
})
export class AcudienteFormComponent implements OnInit {
  modoEdicion = false;
  estudianteId = '';
  vinculoId: string | null = null;
  // Bloqueo del check "principal" (primer acudiente, o único principal en edición)
  bloquearPrincipal = false;
  notaPrincipal = '';
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
  };

  esquema: Esquema = {
    tipoDocumento: [requerido('Selecciona el tipo de documento')],
    numeroDocumento: [requerido('Ingresa el número de documento'), documentoPorTipo()],
    primerNombre: [requerido('Ingresa el primer nombre'), soloLetras(), longitudMin(2)],
    primerApellido: [requerido('Ingresa el primer apellido'), soloLetras(), longitudMin(2)],
    segundoNombre: [soloLetras()],
    segundoApellido: [soloLetras()],
    correo: [correo()],
    telefono: [telefonoCo()],
    parentesco: [requerido('Selecciona el parentesco')],
  };
  errores: ErroresForm = {};

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
    this.cargarContexto();
  }

  validar(campo: string): void {
    const msg = validarCampo(campo, this.form, this.esquema);
    if (msg) this.errores[campo] = msg;
    else delete this.errores[campo];
  }

  onTipoDocumentoChange(): void {
    this.validar('tipoDocumento');
    if (this.form.numeroDocumento) this.validar('numeroDocumento');
  }

  // Trae los acudientes actuales del estudiante para decidir el bloqueo del "principal".
  cargarContexto(): void {
    if (!this.estudianteId) return;
    this.acudienteService.listarPorEstudiante(this.estudianteId).subscribe({
      next: (lista: AcudienteDeEstudiante[]) => {
        if (this.modoEdicion) {
          const propio = lista.find((a) => a.vinculoId === this.vinculoId);
          const otroPrincipal = lista.some(
            (a) => a.vinculoId !== this.vinculoId && a.esPrincipal,
          );
          if (propio?.esPrincipal && !otroPrincipal) {
            this.bloquearPrincipal = true;
            this.form.esPrincipal = true;
            this.notaPrincipal =
              'Es el único principal. Marca a otro acudiente como principal para poder cambiarlo.';
          }
        } else if (lista.length === 0) {
          this.bloquearPrincipal = true;
          this.form.esPrincipal = true;
          this.notaPrincipal =
            'El primer acudiente del estudiante queda como principal.';
        }
      },
      error: () => {
        // Si falla, el backend igual aplica la regla; el formulario sigue usable.
      },
    });
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
        };
        // Si el contexto ya determinó que es el único principal, mantenerlo marcado.
        if (this.bloquearPrincipal) this.form.esPrincipal = true;
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
    this.errores = validarEsquema(this.form, this.esquema);
    if (hayErrores(this.errores)) {
      this.error = 'Revisa los campos marcados en rojo.';
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
