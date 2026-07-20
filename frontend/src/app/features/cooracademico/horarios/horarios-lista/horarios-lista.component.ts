import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HorarioService, HorarioResumen } from '../../../../core/services/horario.service';
import { AnioLectivoService, AnioLectivo } from '../../../../core/services/anio-lectivo.service';
import { GrupoService, Grupo } from '../../../../core/services/grupo.service';

@Component({
  selector: 'app-horarios-lista',
  templateUrl: './horarios-lista.component.html',
  styleUrls: ['./horarios-lista.component.scss']
})
export class HorariosListaComponent implements OnInit {

  readonly DIAS = ['LUNES', 'MARTES', 'MIERCOLES', 'JUEVES', 'VIERNES', 'SABADO', 'DOMINGO'];
  readonly NOMBRE_DIA: Record<string, string> = {
    LUNES: 'Lunes', MARTES: 'Martes', MIERCOLES: 'Miércoles', JUEVES: 'Jueves',
    VIERNES: 'Viernes', SABADO: 'Sábado', DOMINGO: 'Domingo'
  };

  anios: AnioLectivo[] = [];
  grupos: Grupo[] = [];
  anioSeleccionado = '';
  grupoSeleccionado = '';
  horarios: HorarioResumen[] = [];

  cargando = false; error = ''; procesandoId: string | null = null;

  constructor(
    private horarioService: HorarioService,
    private anioService: AnioLectivoService,
    private grupoService: GrupoService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    const qpAnio = this.route.snapshot.queryParamMap.get('anio');
    const qpGrupo = this.route.snapshot.queryParamMap.get('grupo');

    this.anioService.listar().subscribe({
      next: (d) => {
        this.anios = d;
        const inicial = (qpAnio && d.find(a => a.id === qpAnio)) ? qpAnio
                      : (d.find(a => a.esActual) ?? d[0])?.id ?? '';
        if (inicial) {
          this.anioSeleccionado = inicial;
          this.cargarGrupos(qpGrupo);
        }
      },
      error: () => { this.error = 'No se pudieron cargar los años lectivos.'; }
    });
  }

  cambioAnio(): void {
    this.grupoSeleccionado = '';
    this.horarios = [];
    this.cargarGrupos(null);
  }

  cargarGrupos(preseleccion: string | null): void {
    this.grupoService.listarPorAnio(this.anioSeleccionado).subscribe({
      next: (d) => {
        this.grupos = d;
        if (preseleccion && d.find(g => g.id === preseleccion)) {
          this.grupoSeleccionado = preseleccion;
          this.cargar();
        }
      },
      error: () => { this.error = 'No se pudieron cargar los grupos.'; }
    });
  }

  cambioGrupo(): void { this.cargar(); }

  cargar(): void {
    if (!this.grupoSeleccionado) return;
    this.cargando = true; this.error = '';
    this.sincronizarUrl();
    this.horarioService.listarPorGrupo(this.grupoSeleccionado).subscribe({
      next: (d) => { this.horarios = d; this.cargando = false; },
      error: () => { this.error = 'No se pudo cargar el horario.'; this.cargando = false; }
    });
  }

  private sincronizarUrl(): void {
    this.router.navigate([], {
      relativeTo: this.route,
      queryParams: { anio: this.anioSeleccionado, grupo: this.grupoSeleccionado },
      replaceUrl: true
    });
  }

  franjasDe(dia: string): HorarioResumen[] {
    return this.horarios.filter(h => h.diaSemana === dia);   // ya vienen ordenadas por hora
  }

  get diasConClase(): string[] {
    return this.DIAS.filter(d => this.franjasDe(d).length > 0);
  }

  irANueva(): void {
    this.router.navigate(['/dashboard/horarios/nuevo'], {
      queryParams: { anio: this.anioSeleccionado, grupo: this.grupoSeleccionado }
    });
  }

  private accion(id: string, obs: any): void {
    this.procesandoId = id;
    obs.subscribe({
      next: () => { this.procesandoId = null; this.cargar(); },
      error: (err: any) => {
        this.procesandoId = null;
        this.error = err?.error?.mensaje || 'No se pudo completar la acción.';
      }
    });
  }

  activar(h: HorarioResumen): void   { this.accion(h.id, this.horarioService.activar(h.id)); }
  inactivar(h: HorarioResumen): void { this.accion(h.id, this.horarioService.inactivar(h.id)); }
}
