import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';

/**
 * Ruta índice de /dashboard. No renderiza nada: redirige al panel de inicio
 * que corresponde al rol/modo activo del usuario (o al del superadmin).
 * Así el enlace "Inicio" del layout lleva a cada quien a su dashboard.
 */
@Component({
  selector: 'app-inicio-redirect',
  standalone: true,
  template: '',
})
export class InicioRedirectComponent {
  constructor(auth: AuthService, router: Router) {
    router.navigateByUrl(auth.rutaInicio());
  }
}
