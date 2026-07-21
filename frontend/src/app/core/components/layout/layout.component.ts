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

  get esSuperAdmin(): boolean {
    return this.authService.esSuperAdmin();
  }


  get rolPrincipal(): string {
    return this.usuario?.roles?.[0] ?? '';
  }

  get modo(): string | null { return this.authService.getModo(); }

  get otrosModos(): string[] {
    return this.authService.getModosDisponibles().filter(m => m !== this.modo);
  }

  enModo(modo: string): boolean { return this.authService.enModo(modo); }

  cambiarModo(modo: string): void { this.authService.cambiarModo(modo); }

  nombreModo(modo: string): string {
    const nombres: Record<string, string> = {
      'ADMIN_INSTITUCION': 'Administrador', 'COORDINADOR_ACADEMICO': 'Coordinador',
      'DOCENTE': 'Profesor', 'ESTUDIANTE': 'Estudiante', 'ACUDIENTE': 'Acudiente'
    };
    return nombres[modo] ?? modo;
  }

  tieneRol(rol: string): boolean {
    return this.authService.tieneRol(rol);
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

  menu = [
    { label: 'Administradores',   icon: 'ti ti-user-shield',         ruta: '/dashboard/administradores', soloSuperAdmin: true },
    { label: 'Instituciones',     icon: 'ti ti-building-community',   ruta: '/dashboard/instituciones',   soloSuperAdmin: true },
    { label: 'Solicitudes',       icon: 'ti ti-inbox',               ruta: '/dashboard/solicitudes',     soloSuperAdmin: true },
    { label: 'Personal',          icon: 'ti ti-users',               ruta: '/dashboard/staff',           roles: ['ADMIN_INSTITUCION'] },
    { label: 'Sedes',             icon: 'ti ti-building',            ruta: '/dashboard/sedes',           roles: ['ADMIN_INSTITUCION'] },
    { label: 'Jornadas',          icon: 'ti ti-clock-hour-4',        ruta: '/dashboard/jornadas',        roles: ['ADMIN_INSTITUCION'] },
    { label: 'Año lectivo',       icon: 'ti ti-calendar-event',      ruta: '/dashboard/anios-lectivos',  roles: ['ADMIN_INSTITUCION'] },
    { label: 'Datos del colegio', icon: 'ti ti-school',              ruta: '/dashboard/institucion',     roles: ['ADMIN_INSTITUCION'] },
    { label: 'Estudiantes',       icon: 'ti ti-users',               ruta: '/dashboard/estudiantes',     roles: ['COORDINADOR_ACADEMICO'] },
    { label: 'Grados y grupos',   icon: 'ti ti-stairs',              ruta: '/dashboard/grados',         roles: ['COORDINADOR_ACADEMICO'] },
    { label: 'Áreas',             icon: 'ti ti-category',            ruta: '/dashboard/areas',         roles: ['COORDINADOR_ACADEMICO'] },
    { label: 'Asignaturas',       icon: 'ti ti-book-2',            ruta: '/dashboard/asignaturas',         roles: ['COORDINADOR_ACADEMICO'] },
    { label: 'Carga académica',   icon: 'ti ti-chalkboard',         ruta: '/dashboard/cargas',          roles: ['COORDINADOR_ACADEMICO'] },
    { label: 'Horarios', icon: 'ti ti-calendar-time', ruta: '/dashboard/horarios', roles: ['COORDINADOR_ACADEMICO'] },
    { label: 'Matrículas', icon: 'ti ti-clipboard-check', ruta: '/dashboard/matriculas', roles: ['COORDINADOR_ACADEMICO'] },
    { label: 'Mis clases', icon: 'ti ti-chalkboard', ruta: '/dashboard/mis-clases', roles: ['DOCENTE'] },
  ];

  private puedeVer(item: any): boolean {
    if (item.soloSuperAdmin) return this.esSuperAdmin;
    if (!item.roles || item.roles.length === 0) return true;
    return item.roles.some((r: string) => this.enModo(r));
  }

  get menuVisible() {
    return this.menu.filter(i => this.puedeVer(i));
  }
}
