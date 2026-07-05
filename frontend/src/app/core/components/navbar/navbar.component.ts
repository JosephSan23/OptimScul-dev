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
  heroHeight = 0;

  // Estado de sesión
  logueado = false;
  usuario: any = null;
  roles: string[] = [];
  dropdownAbierto = false;

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    // Preguntar al AuthService si hay sesión
    this.logueado = this.authService.isLoggedIn();
    if (this.logueado) {
      this.usuario = this.authService.getUsuarioActual();
      this.roles = this.authService.getRoles();
    }

    if (typeof document === 'undefined') return;
    if (this.estatico) return;
    const hero = document.querySelector('.hero') as HTMLElement;
    if (hero) this.heroHeight = hero.offsetHeight;
  }

  // Iniciales para el avatar (ej: "JS" de Joseph Sánchez)
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

  @HostListener('window:scroll', [])
  onScroll(): void {
    if (this.estatico) return;
    if (typeof document === 'undefined') return;
    const hero = document.querySelector('.hero') as HTMLElement;
    if (hero) this.heroHeight = hero.offsetHeight;
    this.isScrolled = window.scrollY > (this.heroHeight - 80);
  }
}
