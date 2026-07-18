import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CargaService } from '../../../../core/services/carga.service';
import {
  AnioLectivoService,
  AnioLectivo,
} from '../../../../core/services/anio-lectivo.service';
import { GrupoService, Grupo } from '../../../../core/services/grupo.service';
import {
  AsignaturaService,
  Asignatura,
} from '../../../../core/services/asignatura.service';
import {
  ProfesorService,
  ProfesorResumen,
} from '../../../../core/services/profesor.service';

@Component({
  selector: 'app-carga-form',
  templateUrl: './carga-form.component.html',
  styleUrls: ['./carga-form.component.scss'],
})
export class CargaFormComponent implements OnInit {
  modoEdicion = false;
  cargaId: string | null = null;

  form = {
    anioLectivoId: '',
    grupoId: '',
    asignaturaId: '',
    profesorId: '',
    intensidadHorariaSemanal: null as number | null,
    fechaInicio: '',
    fechaFin: '',
    observaciones: '',
  };

  anios: AnioLectivo[] = [];
  grupos: Grupo[] = [];
  asignaturas: Asignatura[] = [];
  profesores: ProfesorResumen[] = [];

  cargando = false;
  guardando = false;
  error = '';

  constructor(
    private cargaService: CargaService,
    private anioService: AnioLectivoService,
    private grupoService: GrupoService,
    private asignaturaService: AsignaturaService,
    private profesorService: ProfesorService,
    private route: ActivatedRoute,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.cargaId = this.route.snapshot.paramMap.get('id');
    this.modoEdicion = !!this.cargaId;

    this.asignaturaService
      .listar()
      .subscribe({
        next: (d) => (this.asignaturas = d.filter((a) => a.activa)),
        error: () => {},
      });
    this.profesorService
      .listar()
      .subscribe({
        next: (d) => (this.profesores = d.filter((p) => p.estado === 'ACTIVO')),
        error: () => {},
      });

    this.anioService.listar().subscribe({
      next: (d) => {
        this.anios = d;
        if (!this.modoEdicion) {
          const actual = d.find((a) => a.esActual);
          if (actual) {
            this.form.anioLectivoId = actual.id;
            this.cargarGrupos();
          }
        }
      },
      error: () => {
        this.error = 'No se pudieron cargar los años lectivos.';
      },
    });

    if (this.modoEdicion) this.cargar();
  }

  cambioAnio(): void {
    this.form.grupoId = '';
    this.cargarGrupos();
  }

  cargarGrupos(): void {
    if (!this.form.anioLectivoId) {
      this.grupos = [];
      return;
    }
    this.grupoService.listarPorAnio(this.form.anioLectivoId).subscribe({
      next: (d) =>
        (this.grupos = d.filter(
          (g) => g.estado === 'ACTIVO' || g.id === this.form.grupoId,
        )),
      error: () => {
        this.error = 'No se pudieron cargar los grupos.';
      },
    });
  }

  cargar(): void {
    this.cargando = true;
    this.cargaService.obtener(this.cargaId!).subscribe({
      next: (c: any) => {
        this.form = {
          anioLectivoId: c.anioLectivoId ?? '',
          grupoId: c.grupoId ?? '',
          asignaturaId: c.asignaturaId ?? '',
          profesorId: c.profesorId ?? '',
          intensidadHorariaSemanal: c.intensidadHorariaSemanal ?? null,
          fechaInicio: c.fechaInicio ?? '',
          fechaFin: c.fechaFin ?? '',
          observaciones: c.observaciones ?? '',
        };
        this.cargarGrupos();
        this.cargando = false;
      },
      error: () => {
        this.error = 'No se pudo cargar la asignación.';
        this.cargando = false;
      },
    });
  }

  guardar(): void {
    this.error = '';
    if (
      !this.form.anioLectivoId ||
      !this.form.grupoId ||
      !this.form.asignaturaId ||
      !this.form.profesorId
    ) {
      this.error = 'Año, grupo, asignatura y profesor son obligatorios.';
      return;
    }
    this.guardando = true;
    const body = {
      anioLectivoId: this.form.anioLectivoId,
      grupoId: this.form.grupoId,
      asignaturaId: this.form.asignaturaId,
      profesorId: this.form.profesorId,
      intensidadHorariaSemanal: this.form.intensidadHorariaSemanal,
      fechaInicio: this.form.fechaInicio || null,
      fechaFin: this.form.fechaFin || null,
      observaciones: this.form.observaciones,
    };
    const p = this.modoEdicion
      ? this.cargaService.editar(this.cargaId!, body)
      : this.cargaService.crear(body);
    p.subscribe({
      next: () => this.router.navigate(['/dashboard/cargas']),
      error: (err) => {
        this.guardando = false;
        this.error = err?.error?.mensaje || 'No se pudo guardar.';
      },
    });
  }

  cancelar(): void {
    this.router.navigate(['/dashboard/cargas']);
  }
}
