import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { StaffService, EditarStaffRequest } from '../../../../core/services/staff.service';
import { validarEsquema, hayErrores, validarCampo, Esquema, ErroresForm } from '../../../../core/validation/form-validator';
import { requerido, documentoPorTipo, soloLetras, longitudMin, correo, telefonoCo, fechaNacimiento } from '../../../../core/validation/validators';

@Component({
  selector: 'app-staff-form',
  templateUrl: './staff-form.component.html',
  styleUrls: ['./staff-form.component.scss']
})
export class StaffFormComponent implements OnInit {

  modoEdicion = false;
  usuarioId: string | null = null;

  // datos de solo lectura (solo en edición)
  username = ''; emailLogin = ''; estado = '';

  form = {
    rolCodigo: '', tipoDocumento: '', numeroDocumento: '',
    primerNombre: '', segundoNombre: '', primerApellido: '', segundoApellido: '',
    correo: '', telefono: '', telefonoAlternativo: '',
    fechaNacimiento: '', sexo: '', nacionalidad: '',
    direccion: '', barrio: '', ciudad: '', departamento: '', pais: '', observaciones: ''
  };

  esquema: Esquema = {
    rolCodigo: [requerido('Selecciona el rol')],
    tipoDocumento: [requerido('Selecciona el tipo de documento')],
    numeroDocumento: [requerido('Ingresa el número de documento'), documentoPorTipo()],
    primerNombre: [requerido('Ingresa el primer nombre'), soloLetras(), longitudMin(2)],
    primerApellido: [requerido('Ingresa el primer apellido'), soloLetras(), longitudMin(2)],
    segundoNombre: [soloLetras()],
    segundoApellido: [soloLetras()],
    correo: [requerido('Ingresa el correo'), correo()],
    telefono: [telefonoCo()],
    telefonoAlternativo: [telefonoCo()],
    fechaNacimiento: [fechaNacimiento()]
  };
  errores: ErroresForm = {};

  tiposDocumento = [
    { valor: 'CC', etiqueta: 'Cédula de Ciudadanía' },
    { valor: 'CE', etiqueta: 'Cédula de Extranjería' },
    { valor: 'PASAPORTE', etiqueta: 'Pasaporte' }
  ];
  roles = [
    { valor: 'RECTOR', etiqueta: 'Rector' },
    { valor: 'COORDINADOR_ACADEMICO', etiqueta: 'Coordinador académico' },
    { valor: 'SECRETARIA', etiqueta: 'Secretaría' },
    { valor: 'DOCENTE', etiqueta: 'Docente' },
    { valor: 'TESORERIA', etiqueta: 'Tesorería' }
  ];
  // Ajusta estos valores a tu enum Sexo real
  sexos = ['MASCULINO', 'FEMENINO', 'OTRO'];

  cargando = false;
  guardando = false;
  error = '';
  fotoUrl = '';

  constructor(private staffService: StaffService,
              private route: ActivatedRoute,
              private router: Router) {}

  ngOnInit(): void {
    this.usuarioId = this.route.snapshot.paramMap.get('id');
    this.modoEdicion = !!this.usuarioId;
    if (this.modoEdicion) this.cargar();
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

  cargar(): void {
    this.cargando = true;
    this.staffService.obtener(this.usuarioId!).subscribe({
      next: (d) => {
        this.username = d.username; this.emailLogin = d.emailLogin; this.estado = d.estado;
        this.form = {
          rolCodigo: d.rolCodigo ?? '', tipoDocumento: d.tipoDocumento ?? '', numeroDocumento: d.numeroDocumento ?? '',
          primerNombre: d.primerNombre ?? '', segundoNombre: d.segundoNombre ?? '',
          primerApellido: d.primerApellido ?? '', segundoApellido: d.segundoApellido ?? '',
          correo: d.correo ?? '', telefono: d.telefono ?? '', telefonoAlternativo: d.telefonoAlternativo ?? '',
          fechaNacimiento: d.fechaNacimiento ?? '', sexo: d.sexo ?? '', nacionalidad: d.nacionalidad ?? '',
          direccion: d.direccion ?? '', barrio: d.barrio ?? '', ciudad: d.ciudad ?? '',
          departamento: d.departamento ?? '', pais: d.pais ?? '', observaciones: d.observaciones ?? ''
        };
        this.cargando = false;
      },
      error: () => { this.error = 'No se pudo cargar el usuario.'; this.cargando = false; }
    });
  }

  guardar(): void {
    this.error = '';
    this.errores = validarEsquema(this.form, this.esquema);
    if (hayErrores(this.errores)) {
      this.error = 'Revisa los campos marcados en rojo.';
      return;
    }
    this.guardando = true;

    const peticion = this.modoEdicion
      ? this.staffService.editar(this.usuarioId!, this.form as EditarStaffRequest)
      : this.staffService.crear({
          rolCodigo: this.form.rolCodigo, tipoDocumento: this.form.tipoDocumento,
          numeroDocumento: this.form.numeroDocumento, primerNombre: this.form.primerNombre,
          primerApellido: this.form.primerApellido, correo: this.form.correo
        });

    peticion.subscribe({
      next: () => this.router.navigate(['/dashboard/staff']),
      error: (err) => { this.guardando = false; this.error = err?.error?.mensaje || 'No se pudo guardar.'; }
    });
  }

  get nombreCompleto(): string {
    const n = `${this.form.primerNombre} ${this.form.primerApellido}`.trim();
    return n || 'Usuario';
  }
  get iniciales(): string {
    const a = (this.form.primerNombre || '').charAt(0);
    const b = (this.form.primerApellido || '').charAt(0);
    return ((a + b) || '?').toUpperCase();
  }

  cancelar(): void { this.router.navigate(['/dashboard/staff']); }
}
