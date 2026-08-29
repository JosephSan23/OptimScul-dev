import { Component, HostListener } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { NotificacionService } from '../../services/notificacion.service';
import { ChatSocketService } from '../../services/chat-socket.service';
import { PerfilService } from '../../services/perfil.service';
import { Subject, of } from 'rxjs';
import { debounceTime, distinctUntilChanged, switchMap, catchError } from 'rxjs/operators';
import { BusquedaService, ResultadoBusqueda } from '../../services/busqueda.service';

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
  mostrarCambioPassword = false;

  // --- Cabecera: nombre del colegio ---
  nombreInstitucion = '';

  // --- Buscador global ---
  terminoBusqueda = '';
  resultados: ResultadoBusqueda | null = null;
  panelBusquedaAbierto = false;
  buscando = false;
  private busqueda$ = new Subject<string>();

  constructor(private authService: AuthService, private notis: NotificacionService, private socket: ChatSocketService, private router: Router, private perfilService: PerfilService, private busquedaService: BusquedaService) {}

  ngOnInit(): void {
    this.notis.contador().subscribe(r => this.noLeidas = r.noLeidas);

    // Nombre del colegio del usuario logueado (cae en silencio si falla)
    this.busquedaService.miInstitucion().subscribe({
      next: i => this.nombreInstitucion = i.nombreCorto || i.nombre,
      error: () => {}
    });

    // Buscador con debounce: espera a que el usuario deje de escribir
    this.busqueda$.pipe(
      debounceTime(250),
      distinctUntilChanged(),
      switchMap(q => {
        const t = q.trim();
        if (t.length < 2) { this.panelBusquedaAbierto = false; return of<ResultadoBusqueda | null>(null); }
        this.buscando = true;
        return this.busquedaService.buscar(t).pipe(catchError(() => of<ResultadoBusqueda | null>(null)));
      })
    ).subscribe(res => {
      this.buscando = false;
      this.resultados = res;
      this.panelBusquedaAbierto = res != null;
    });
    this.socket.conectar();
    this.socket.notificaciones.subscribe(n => {
      this.notificaciones.unshift(n);
      this.noLeidas++;
    });
    if (this.authService.getTipoContexto() === 'INSTITUCION') {
    this.perfilService.perfil().subscribe(p => this.mostrarCambioPassword = p.requiereCambioPassword);
  }
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

  irAPerfil(): void { this.router.navigate(['/dashboard/perfil']); }  // inyecta Router si no está

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
    this.panelBusquedaAbierto = false;
  }

  // --- Buscador ---
  onBuscar(termino: string): void {
    this.busqueda$.next(termino);
  }

  get hayResultados(): boolean {
    return !!this.resultados &&
      (this.resultados.estudiantes.length > 0 ||
       this.resultados.personal.length > 0 ||
       this.resultados.asignaturas.length > 0);
  }

  /** El buscador solo está disponible para quienes gestionan estudiantes/asignaturas. */
  get puedeBuscar(): boolean {
    return this.enModo('COORDINADOR_ACADEMICO') || this.enModo('ADMIN_INSTITUCION');
  }

  irAEstudiante(id: string): void {
    this.cerrarBusqueda();
    this.router.navigate(['/dashboard/estudiantes', id]);
  }

  irAPersonal(id: string): void {
    this.cerrarBusqueda();
    this.router.navigate(['/dashboard/staff', id]);
  }

  irAAsignatura(id: string): void {
    this.cerrarBusqueda();
    this.router.navigate(['/dashboard/asignaturas', id]);
  }

  cerrarBusqueda(): void {
    this.panelBusquedaAbierto = false;
    this.terminoBusqueda = '';
    this.resultados = null;
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
