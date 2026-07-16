import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { GrupoService } from '../../../../core/services/grupo.service';
import {
  AnioLectivoService,
  AnioLectivo,
} from '../../../../core/services/anio-lectivo.service';
import { SedeService, Sede } from '../../../../core/services/sede.service';
import {
  JornadaService,
  Jornada,
} from '../../../../core/services/jornada.service';

@Component({
  selector: 'app-grupo-form',
  templateUrl: './grupo-form.component.html',
  styleUrls: ['./grupo-form.component.scss'],
})
export class GrupoFormComponent implements OnInit {
  modoEdicion = false;
  grupoId: string | null = null;
  gradoId: string | null = null;

  form = {
    anioLectivoId: '',
    sedeId: '',
    jornadaId: '',
    codigo: '',
    nombre: '',
    cupoMaximo: null as number | null,
    observaciones: '',
  };

  anios: AnioLectivo[] = [];
  sedes: Sede[] = [];
  jornadas: Jornada[] = [];

  cargando = false;
  guardando = false;
  error = '';

  constructor(
    private grupoService: GrupoService,
    private anioService: AnioLectivoService,
    private sedeService: SedeService,
    private jornadaService: JornadaService,
    private route: ActivatedRoute,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.grupoId = this.route.snapshot.paramMap.get('id'); // ruta de edición: /dashboard/grupos/:id
    this.gradoId = this.route.snapshot.paramMap.get('gradoId'); // ruta de creación: /dashboard/grados/:gradoId/grupos/nuevo
    this.modoEdicion = !!this.grupoId;
    this.cargarCatalogos();
    if (this.modoEdicion) this.cargar();
  }

  cargarCatalogos(): void {
    this.anioService.listar().subscribe({
      next: (d) => {
        this.anios = d;
        // al crear, preselecciona el año marcado como actual
        if (!this.modoEdicion && !this.form.anioLectivoId) {
          const actual = d.find((a) => a.esActual);
          if (actual) this.form.anioLectivoId = actual.id;
        }
      },
      error: () => {
        this.error = 'No se pudieron cargar los años lectivos.';
      },
    });
    this.sedeService
      .listar()
      .subscribe({ next: (d) => (this.sedes = d), error: () => {} });
    this.jornadaService
      .listar()
      .subscribe({ next: (d) => (this.jornadas = d), error: () => {} });
  }

  cargar(): void {
    this.cargando = true;
    this.grupoService.obtener(this.grupoId!).subscribe({
      next: (g) => {
        this.gradoId = g.gradoId;
        this.form = {
          anioLectivoId: g.anioLectivoId ?? '',
          sedeId: g.sedeId ?? '',
          jornadaId: g.jornadaId ?? '',
          codigo: g.codigo ?? '',
          nombre: g.nombre ?? '',
          cupoMaximo: g.cupoMaximo ?? null,
          observaciones: g.observaciones ?? '',
        };
        this.cargando = false;
      },
      error: () => {
        this.error = 'No se pudo cargar el grupo.';
        this.cargando = false;
      },
    });
  }

  guardar(): void {
    this.error = '';
    if (
      !this.form.codigo.trim() ||
      !this.form.nombre.trim() ||
      !this.form.anioLectivoId
    ) {
      this.error = 'Código, nombre y año lectivo son obligatorios.';
      return;
    }
    this.guardando = true;
    const body = {
      anioLectivoId: this.form.anioLectivoId,
      sedeId: this.form.sedeId || null,
      jornadaId: this.form.jornadaId || null,
      codigo: this.form.codigo,
      nombre: this.form.nombre,
      cupoMaximo: this.form.cupoMaximo,
      observaciones: this.form.observaciones,
    };
    const p = this.modoEdicion
      ? this.grupoService.editar(this.grupoId!, body)
      : this.grupoService.crear(this.gradoId!, body);
    p.subscribe({
      next: () => this.volver(),
      error: (err) => {
        this.guardando = false;
        this.error = err?.error?.mensaje || 'No se pudo guardar.';
      },
    });
  }

  volver(): void {
    if (this.gradoId)
      this.router.navigate(['/dashboard/grados', this.gradoId, 'grupos']);
    else this.router.navigate(['/dashboard/grados']);
  }
}
