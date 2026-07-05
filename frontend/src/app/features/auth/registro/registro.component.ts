import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { RegistroService } from '../../../core/services/registro.service';

@Component({
  selector: 'app-registro',
  templateUrl: './registro.component.html',
  styleUrls: ['./registro.component.scss']
})
export class RegistroComponent {

  form = {
    tipoDocumento: '',
    numeroDocumento: '',
    primerNombre: '',
    primerApellido: '',
    correo: '',
    password: '',
    confirmarPassword: ''
  };

  tiposDocumento = [
    { valor: 'CC', etiqueta: 'Cédula de Ciudadanía' },
    { valor: 'CE', etiqueta: 'Cédula de Extranjería' },
    { valor: 'PASAPORTE', etiqueta: 'Pasaporte' }
  ];

  cargando = false;
  error = '';

  constructor(private router: Router, private registroService: RegistroService) {}

  registrar(): void {
    this.error = '';

    if (!this.form.tipoDocumento || !this.form.numeroDocumento.trim() ||
        !this.form.primerNombre.trim() || !this.form.primerApellido.trim() ||
        !this.form.correo.trim() || !this.form.password) {
      this.error = 'Completa todos los campos obligatorios.';
      return;
    }
    if (this.form.password.length < 8) {
      this.error = 'La contraseña debe tener al menos 8 caracteres.';
      return;
    }
    if (this.form.password !== this.form.confirmarPassword) {
      this.error = 'Las contraseñas no coinciden.';
      return;
    }

    this.cargando = true;

    this.registroService.registrar({
      tipoDocumento: this.form.tipoDocumento,
      numeroDocumento: this.form.numeroDocumento,
      primerNombre: this.form.primerNombre,
      primerApellido: this.form.primerApellido,
      correo: this.form.correo,
      password: this.form.password
    }).subscribe({
      next: () => {
        this.cargando = false;
        // Cuenta creada → al login para que inicie sesión
        this.router.navigate(['/login'], { queryParams: { registrado: '1' } });
      },
      error: (err) => {
        this.cargando = false;
        this.error = err?.error?.mensaje || 'No se pudo crear la cuenta. Intenta de nuevo.';
      }
    });
  }

  volver(): void { this.router.navigate(['/login']); }
  irALogin(): void { this.router.navigate(['/login']); }
  proximamente(): void { this.error = 'El acceso con Google y Microsoft estará disponible pronto.'; }
}
