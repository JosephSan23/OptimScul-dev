import { Component, HostListener } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { NotificacionService } from '../../services/notificacion.service';
import { ChatSocketService } from '../../services/chat-socket.service';

@Component({
  selector: 'app-layout',
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.scss']
})
export class LayoutComponent {

  usuario = this.authService.getUsuarioActual();
  submenuAbierto: string | null = null;   // qué submenú está abierto (null = ninguno)
  dropdownAbierto = false;                 // si el menú de usuario está visible
  noLeidas = 0;
  notificaciones: any[] = [];
  panelNotisAbierto = false;

  constructor(private authService: AuthService, private notis: NotificacionService, private socket: ChatSocketService) {}

  ngOnInit(): void {
    this.notis.contador().subscribe(r => this.noLeidas = r.noLeidas);
    this.socket.conectar();
    this.socket.notificaciones.subscribe(n => {
      this.notificaciones.unshift(n);
      this.noLeidas++;
    });
  }

  toggleNotis(): void {
    this.panelNotisAbierto = !this.panelNotisAbierto;
    if (this.panelNotisAbierto) this.notis.bandeja().subscribe(l => this.notificaciones = l);
  }

  marcarTodas(): void {
    this.notis.marcarTodas().subscribe(() => {
      this.noLeidas = 0;
      this.notificaciones.forEach(n => n.estado = 'LEIDA');
    });
  }

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
    this.panelNotisAbierto = false;
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
    { label: 'Config. académica', icon: 'ti ti-adjustments', ruta: '/dashboard/config-academica', roles: ['ADMIN_INSTITUCION'] },
    { label: 'Escala valorativa', icon: 'ti ti-award',       ruta: '/dashboard/escalas',          roles: ['ADMIN_INSTITUCION'] },
    { label: 'Estudiantes',       icon: 'ti ti-users',               ruta: '/dashboard/estudiantes',     roles: ['COORDINADOR_ACADEMICO'] },
    { label: 'Grados y grupos',   icon: 'ti ti-stairs',              ruta: '/dashboard/grados',         roles: ['COORDINADOR_ACADEMICO'] },
    { label: 'Áreas',             icon: 'ti ti-category',            ruta: '/dashboard/areas',         roles: ['COORDINADOR_ACADEMICO'] },
    { label: 'Asignaturas',       icon: 'ti ti-book-2',            ruta: '/dashboard/asignaturas',         roles: ['COORDINADOR_ACADEMICO'] },
    { label: 'Carga académica',   icon: 'ti ti-chalkboard',         ruta: '/dashboard/cargas',          roles: ['COORDINADOR_ACADEMICO'] },
    { label: 'Horarios', icon: 'ti ti-calendar-time', ruta: '/dashboard/horarios', roles: ['COORDINADOR_ACADEMICO'] },
    { label: 'Matrículas', icon: 'ti ti-clipboard-check', ruta: '/dashboard/matriculas', roles: ['COORDINADOR_ACADEMICO'] },
    { label: 'Mis clases', icon: 'ti ti-chalkboard', ruta: '/dashboard/mis-clases', roles: ['DOCENTE'] },
    { label: 'Asistencia por clase', icon: 'ti ti-table', ruta: '/dashboard/reportes/matriz', roles: ['COORDINADOR_ACADEMICO'] },
    { label: 'Mis notas', icon: 'ti ti-report-analytics', ruta: '/dashboard/estudiante/notas', roles: ['ESTUDIANTE'] },
    { label: 'Mi horario',    icon: 'ti ti-calendar-time',    ruta: '/dashboard/estudiante/horario',    roles: ['ESTUDIANTE'] },
    { label: 'Mi asistencia', icon: 'ti ti-checklist',         ruta: '/dashboard/estudiante/asistencia', roles: ['ESTUDIANTE'] },
    { label: 'Notas de mis hijos', icon: 'ti ti-report-analytics', ruta: '/dashboard/acudiente/notas', roles: ['ACUDIENTE'] },
    { label: 'Horario',    icon: 'ti ti-calendar-time', ruta: '/dashboard/acudiente/horario',    roles: ['ACUDIENTE'] },
    { label: 'Asistencia', icon: 'ti ti-checklist',      ruta: '/dashboard/acudiente/asistencia', roles: ['ACUDIENTE'] },
    { label: 'Mis actividades', icon: 'ti ti-checkup-list', ruta: '/dashboard/estudiante/actividades', roles: ['ESTUDIANTE'] },
    { label: 'Mensajes', icon: 'ti ti-message-circle', ruta: '/dashboard/chat', roles: ['DOCENTE', 'ESTUDIANTE', 'ACUDIENTE'] },

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
