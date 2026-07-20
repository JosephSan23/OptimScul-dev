import { Component, OnInit } from '@angular/core';
import {
  MatriculaService,
  MatriculaResumen,
} from '../../../../core/services/matricula.service';
import {
  AnioLectivoService,
  AnioLectivo,
} from '../../../../core/services/anio-lectivo.service';
import { GrupoService, Grupo } from '../../../../core/services/grupo.service';
import { FiltroTabla } from '../../../../core/components/tabla-paginada/tabla-paginada.component';

@Component({
  selector: 'app-matriculas-lista',
  templateUrl: './matriculas-lista.component.html',
  styleUrls: ['./matriculas-lista.component.scss'],
})
export class MatriculasListaComponent implements OnInit {
  anios: AnioLectivo[] = [];
  anioSeleccionado = '';
  matriculas: MatriculaResumen[] = [];
  cargando = false;
  error = '';
  procesandoId: string | null = null;

  // modal asignar grupo
  gruposDelAnio: Grupo[] = [];
  matriculaAsignando: MatriculaResumen | null = null;
  grupoElegido = '';

  filtrosTabla: FiltroTabla[] = [
    { campo: 'gradoNombre', etiqueta: 'Grado' },
    { campo: 'estado', etiqueta: 'Estado' },
    { campo: 'tipo', etiqueta: 'Tipo' },
  ];

  constructor(
    private matriculaService: MatriculaService,
    private anioService: AnioLectivoService,
    private grupoService: GrupoService,
  ) {}

  ngOnInit(): void {
    this.anioService.listar().subscribe({
      next: (d) => {
        this.anios = d;
        const actual = d.find((a) => a.esActual) ?? d[0];
        if (actual) {
          this.anioSeleccionado = actual.id;
          this.cargar();
        }
      },
      error: () => {
        this.error = 'No se pudieron cargar los años lectivos.';
      },
    });
  }

  cambioAnio(): void {
    this.cargar();
  }

  cargar(): void {
    if (!this.anioSeleccionado) return;
    this.cargando = true;
    this.error = '';
    this.matriculaService.listarPorAnio(this.anioSeleccionado).subscribe({
      next: (d) => {
        this.matriculas = d;
        this.cargando = false;
      },
      error: () => {
        this.error = 'No se pudieron cargar las matrículas.';
        this.cargando = false;
      },
    });
  }

  // ── modal asignar grupo ──
  abrirAsignar(m: MatriculaResumen): void {
    this.matriculaAsignando = m;
    this.grupoElegido = m.grupoId ?? '';
    if (this.gruposDelAnio.length === 0) {
      this.grupoService.listarPorAnio(this.anioSeleccionado).subscribe({
        next: (d) =>
          (this.gruposDelAnio = d.filter((g) => g.estado === 'ACTIVO')),
        error: () => {},
      });
    }
  }

  cerrarAsignar(): void {
    this.matriculaAsignando = null;
    this.grupoElegido = '';
  }

  confirmarAsignar(): void {
    if (!this.matriculaAsignando || !this.grupoElegido) return;
    const id = this.matriculaAsignando.id;
    this.procesandoId = id;
    this.matriculaService.asignarGrupo(id, this.grupoElegido).subscribe({
      next: () => {
        this.procesandoId = null;
        this.cerrarAsignar();
        this.cargar();
      },
      error: (err) => {
        this.procesandoId = null;
        this.error = err?.error?.mensaje || 'No se pudo asignar el grupo.';
      },
    });
  }

  private accion(id: string, obs: any): void {
    this.procesandoId = id;
    obs.subscribe({
      next: () => {
        this.procesandoId = null;
        this.cargar();
      },
      error: (err: any) => {
        this.procesandoId = null;
        this.error = err?.error?.mensaje || 'No se pudo completar la acción.';
      },
    });
  }

  retirar(m: MatriculaResumen): void {
    this.accion(m.id, this.matriculaService.retirar(m.id));
  }
  cancelar(m: MatriculaResumen): void {
    this.accion(m.id, this.matriculaService.cancelar(m.id));
  }
  reactivar(m: MatriculaResumen): void {
    this.accion(m.id, this.matriculaService.reactivar(m.id));
  }

  vigente(estado: string): boolean {
    return estado === 'PREMATRICULA' || estado === 'MATRICULADO';
  }
}
