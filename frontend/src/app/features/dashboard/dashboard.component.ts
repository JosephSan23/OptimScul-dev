import { Component } from '@angular/core';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html'
})
export class DashboardComponent {

  usuario = this.authService.getUsuarioActual();

  constructor(private authService: AuthService) {}

  cerrarSesion(): void {
    this.authService.logout();
  }
}
