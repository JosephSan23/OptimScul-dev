import { Component, HostListener } from '@angular/core';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-layout',
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.scss']
})
export class LayoutComponent {

  usuario = this.authService.getUsuarioActual();
  submenuAbierto: string | null = null;   // qué submenú está abierto (null = ninguno)
  dropdownAbierto = false;                 // si el menú de usuario está visible

  constructor(private authService: AuthService) {}

  get iniciales(): string {
    return (this.usuario?.username ?? '').substring(0, 2).toUpperCase();
  }

  get rolPrincipal(): string {
    return this.usuario?.roles?.[0] ?? '';
  }

  toggleSubmenu(nombre: string): void {
    this.submenuAbierto = this.submenuAbierto === nombre ? null : nombre;
  }

  cerrarSubmenus(): void {
    this.submenuAbierto = null;
  }

  toggleDropdown(event: MouseEvent): void {
    event.stopPropagation();
    this.dropdownAbierto = !this.dropdownAbierto;
  }

  @HostListener('document:click')
  cerrarDropdown(): void {
    this.dropdownAbierto = false;
  }

  cerrarSesion(): void {
    this.authService.logout();
  }
}
