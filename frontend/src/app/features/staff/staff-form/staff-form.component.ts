import { Component, EventEmitter, Output } from '@angular/core';
import { StaffService, StaffRequest } from '../../../core/services/staff.service';

@Component({
  selector: 'app-staff-form',
  templateUrl: './staff-form.component.html',
  styleUrls: ['./staff-form.component.scss']
})
export class StaffFormComponent {

  @Output() guardado = new EventEmitter<void>();
  @Output() cancelado = new EventEmitter<void>();

  form: StaffRequest = {
    rolCodigo: '', tipoDocumento: '', numeroDocumento: '',
    primerNombre: '', primerApellido: '', correo: ''
  };

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

  guardando = false;
  error = '';
  exito = '';

  constructor(private staffService: StaffService) {}

  guardar(): void {
    this.error = '';
    this.exito = '';
    if (!this.form.rolCodigo || !this.form.tipoDocumento || !this.form.numeroDocumento.trim() ||
        !this.form.primerNombre.trim() || !this.form.primerApellido.trim() || !this.form.correo.trim()) {
      this.error = 'Completa todos los campos.';
      return;
    }
    this.guardando = true;
    this.staffService.crear({ ...this.form }).subscribe({
      next: (res) => {
        this.guardando = false;
        this.exito = res.mensaje;
        this.form = { rolCodigo: '', tipoDocumento: '', numeroDocumento: '',
                      primerNombre: '', primerApellido: '', correo: '' };
        this.guardado.emit();
      },
      error: (err) => {
        this.guardando = false;
        this.error = err?.error?.mensaje || 'No se pudo crear el usuario.';
      }
    });
  }

  cancelar(): void { this.cancelado.emit(); }
}
