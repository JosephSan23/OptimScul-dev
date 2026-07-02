import { Component } from '@angular/core';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  credentials = { usernameOrEmail: '', password: '' };
  errorMsg    = '';
  cargando    = false;
  mostrarPass = false;

  constructor(private authService: AuthService) {}

  login(): void {
    if (!this.credentials.usernameOrEmail || !this.credentials.password) {
      this.errorMsg = 'Por favor completa todos los campos.';
      return;
    }

    this.cargando = true;
    this.errorMsg = '';

    this.authService.login(this.credentials).subscribe({
      next: () => {
        this.cargando = false;
        // La redirección la maneja AuthService según tipoContexto y roles
      },
      error: (err) => {
        this.cargando = false;
        this.errorMsg = err.error?.mensaje ?? 'Error al iniciar sesión. Intenta de nuevo.';
      }
    });
  }
}
