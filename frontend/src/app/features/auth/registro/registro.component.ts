import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { RegistroService } from '../../../core/services/registro.service';
import { validarEsquema, hayErrores, validarCampo, Esquema, ErroresForm } from '../../../core/validation/form-validator';
import { requerido, documentoPorTipo, soloLetras, longitudMin, correo, coincideCon } from '../../../core/validation/validators';

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

  esquema: Esquema = {
    tipoDocumento: [requerido('Selecciona el tipo de documento')],
    numeroDocumento: [requerido('Ingresa el número de documento'), documentoPorTipo()],
    primerNombre: [requerido('Ingresa el primer nombre'), soloLetras(), longitudMin(2)],
    primerApellido: [requerido('Ingresa el primer apellido'), soloLetras(), longitudMin(2)],
    correo: [requerido('Ingresa el correo'), correo()],
    password: [requerido('Ingresa la contraseña'), longitudMin(8, 'La contraseña debe tener al menos 8 caracteres')],
    confirmarPassword: [requerido('Confirma la contraseña'), coincideCon('password', 'Las contraseñas no coinciden')]
  };
  errores: ErroresForm = {};

  tiposDocumento = [
    { valor: 'CC', etiqueta: 'Cédula de Ciudadanía' },
    { valor: 'CE', etiqueta: 'Cédula de Extranjería' },
    { valor: 'PASAPORTE', etiqueta: 'Pasaporte' }
  ];

  cargando = false;
  error = '';

  constructor(private router: Router, private registroService: RegistroService) {}

  validar(campo: string): void {
    const msg = validarCampo(campo, this.form, this.esquema);
    if (msg) this.errores[campo] = msg;
    else delete this.errores[campo];
  }

  onTipoDocumentoChange(): void {
    this.validar('tipoDocumento');
    if (this.form.numeroDocumento) this.validar('numeroDocumento');
  }

  registrar(): void {
    this.error = '';

    this.errores = validarEsquema(this.form, this.esquema);
    if (hayErrores(this.errores)) {
      this.error = 'Revisa los campos marcados en rojo.';
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
