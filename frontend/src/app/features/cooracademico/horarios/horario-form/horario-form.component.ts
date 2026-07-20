import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HorarioService } from '../../../../core/services/horario.service';
import { CargaService, CargaResumen } from '../../../../core/services/carga.service';
import { SedeService, Sede } from '../../../../core/services/sede.service';

@Component({
  selector: 'app-horario-form',
  templateUrl: './horario-form.component.html',
  styleUrls: ['./horario-form.component.scss']
})
export class HorarioFormComponent implements OnInit {

  readonly DIAS = [
    { valor: 'LUNES', nombre: 'Lunes' }, { valor: 'MARTES', nombre: 'Martes' },
    { valor: 'MIERCOLES', nombre: 'Miércoles' }, { valor: 'JUEVES', nombre: 'Jueves' },
    { valor: 'VIERNES', nombre: 'Viernes' }, { valor: 'SABADO', nombre: 'Sábado' },
    { valor: 'DOMINGO', nombre: 'Domingo' }
  ];

  modoEdicion = false; horarioId: string | null = null;
  anioId = ''; grupoId = '';

  form = { cargaAcademicaId: '', sedeId: '', diaSemana: '', horaInicio: '', horaFin: '', aula: '' };

  cargasDelGrupo: CargaResumen[] = [];
  sedes: Sede[] = [];

  cargando = false; guardando = false; error = '';

  constructor(
    private horarioService: HorarioService,
    private cargaService: CargaService,
    private sedeService: SedeService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.horarioId = this.route.snapshot.paramMap.get('id');
    this.modoEdicion = !!this.horarioId;
    this.anioId = this.route.snapshot.queryParamMap.get('anio') ?? '';
    this.grupoId = this.route.snapshot.queryParamMap.get('grupo') ?? '';

    this.sedeService.listar().subscribe({ next: (d) => this.sedes = d, error: () => {} });

    if (this.modoEdicion) this.cargar();
    else if (this.anioId && this.grupoId) this.cargarCargas();
    else this.error = 'Falta el contexto de año y grupo. Vuelve al horario y entra desde "Nueva franja".';
  }

  cargarCargas(): void {
    this.cargaService.listarPorAnio(this.anioId).subscribe({
      next: (d) => {
        this.cargasDelGrupo = d.filter(c =>
          c.grupoId === this.grupoId && (c.estado === 'ACTIVA' || c.id === this.form.cargaAcademicaId));
      },
      error: () => { this.error = 'No se pudieron cargar las asignaciones del grupo.'; }
    });
  }

  cargar(): void {
    this.cargando = true;
    this.horarioService.obtener(this.horarioId!).subscribe({
      next: (h: any) => {
        this.form = {
          cargaAcademicaId: h.cargaAcademicaId ?? '', sedeId: h.sedeId ?? '',
          diaSemana: h.diaSemana ?? '',
          horaInicio: (h.horaInicio ?? '').substring(0, 5),
          horaFin: (h.horaFin ?? '').substring(0, 5),
          aula: h.aula ?? ''
        };
        // recuperar el contexto año/grupo desde la carga (para el select y el "volver")
        this.cargaService.obtener(h.cargaAcademicaId).subscribe({
          next: (c: any) => {
            this.anioId = c.anioLectivoId; this.grupoId = c.grupoId;
            this.cargarCargas();
            this.cargando = false;
          },
          error: () => { this.cargando = false; }
        });
      },
      error: () => { this.error = 'No se pudo cargar la franja.'; this.cargando = false; }
    });
  }

  guardar(): void {
    this.error = '';
    if (!this.form.cargaAcademicaId || !this.form.diaSemana || !this.form.horaInicio || !this.form.horaFin) {
      this.error = 'Asignación, día y horas son obligatorios.'; return;
    }
    if (this.form.horaInicio >= this.form.horaFin) {
      this.error = 'La hora de inicio debe ser anterior a la hora de fin.'; return;
    }
    this.guardando = true;
    const body = {
      cargaAcademicaId: this.form.cargaAcademicaId,
      sedeId: this.form.sedeId || null,
      diaSemana: this.form.diaSemana,
      horaInicio: this.form.horaInicio,
      horaFin: this.form.horaFin,
      aula: this.form.aula
    };
    const p = this.modoEdicion ? this.horarioService.editar(this.horarioId!, body) : this.horarioService.crear(body);
    p.subscribe({
      next: () => this.volver(),
      error: (err) => { this.guardando = false; this.error = err?.error?.mensaje || 'No se pudo guardar.'; }
    });
  }

  volver(): void {
    this.router.navigate(['/dashboard/horarios'], {
      queryParams: { anio: this.anioId, grupo: this.grupoId }
    });
  }
}
