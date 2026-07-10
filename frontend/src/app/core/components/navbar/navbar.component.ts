import { Component, OnInit, HostListener, Input, ViewEncapsulation } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class NavbarComponent implements OnInit {

  @Input() estatico = false;

  isScrolled = false;

  // Estado de sesión
  logueado = false;
  usuario: any = null;
  roles: string[] = [];
  dropdownAbierto = false;

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    this.logueado = this.authService.isLoggedIn();
    if (this.logueado) {
      this.usuario = this.authService.getUsuarioActual();
      this.roles = this.authService.getRoles();
    }
  }

  // Iniciales para el avatar (ej: "JO" de joseph-olarte10)
  get iniciales(): string {
    if (!this.usuario?.username) return '?';
    return this.usuario.username.substring(0, 2).toUpperCase();
  }

  get rolPrincipal(): string {
    return this.roles.length > 0 ? this.roles[0] : '';
  }

  toggleDropdown(event: Event): void {
    event.stopPropagation();
    this.dropdownAbierto = !this.dropdownAbierto;
  }

  @HostListener('document:click')
  cerrarDropdown(): void {
    this.dropdownAbierto = false;
  }

  cerrarSesion(): void {
    this.authService.logout();
    this.logueado = false;
    this.usuario = null;
    this.dropdownAbierto = false;
    this.router.navigate(['/']);
  }

  irALogin(): void { this.router.navigate(['/login']); }
  irARegistro(): void { this.router.navigate(['/registro']); }

  /* La cápsula ya no se invierte a navy al pasar el hero (no lo necesita:
     la misma cápsula clara funciona sobre oscuro y sobre claro), así que
     desaparece el cálculo de la altura del hero. Solo detectamos un poco
     de scroll para "asentar" la cápsula (más opacidad y sombra). */
  @HostListener('window:scroll', [])
  onScroll(): void {
    if (this.estatico) return;
    if (typeof window === 'undefined') return;
    this.isScrolled = window.scrollY > 16;
  }
}
