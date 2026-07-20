import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MatriculaService } from '../../../../core/services/matricula.service';
import {
  AnioLectivoService,
  AnioLectivo,
} from '../../../../core/services/anio-lectivo.service';
import { GrupoService, Grupo } from '../../../../core/services/grupo.service';
import {
  EstudianteService,
  Estudiante,
} from '../../../../core/services/estudiante.service';

@Component({
  selector: 'app-matricula-form',
  templateUrl: './matricula-form.component.html',
  styleUrls: ['./matricula-form.component.scss'],
})
export class MatriculaFormComponent implements OnInit {
  readonly TIPOS = ['NUEVA', 'RENOVACION', 'TRASLADO', 'REINTEGRO'];

  form = {
    estudianteId: '',
    anioLectivoId: '',
    tipo: 'NUEVA',
    grupoId: '',
    fechaMatricula: '',
    observaciones: '',
  };
  anios: AnioLectivo[] = [];
  estudiantes: Estudiante[] = [];
  grupos: Grupo[] = [];
  guardando = false;
  error = '';

  constructor(
    private matriculaService: MatriculaService,
    private anioService: AnioLectivoService,
    private grupoService: GrupoService,
    private estudianteService: EstudianteService,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.estudianteService.listar().subscribe({
      next: (d) => (this.estudiantes = d.filter((e) => e.estado === 'ACTIVO')),
      error: () => {},
    });
    this.anioService.listar().subscribe({
      next: (d) => {
        this.anios = d;
        const actual = d.find((a) => a.esActual);
        if (actual) {
          this.form.anioLectivoId = actual.id;
          this.cargarGrupos();
        }
      },
      error: () => {
        this.error = 'No se pudieron cargar los años lectivos.';
      },
    });
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
      next: (d) => (this.grupos = d.filter((g) => g.estado === 'ACTIVO')),
      error: () => {},
    });
  }

  guardar(): void {
    this.error = '';
    if (
      !this.form.estudianteId ||
      !this.form.anioLectivoId ||
      !this.form.tipo
    ) {
      this.error = 'Estudiante, año y tipo son obligatorios.';
      return;
    }
    this.guardando = true;
    const body = {
      estudianteId: this.form.estudianteId,
      anioLectivoId: this.form.anioLectivoId,
      tipo: this.form.tipo,
      grupoId: this.form.grupoId || null,
      fechaMatricula: this.form.fechaMatricula || null,
      observaciones: this.form.observaciones,
    };
    this.matriculaService.crear(body).subscribe({
      next: () => this.router.navigate(['/dashboard/matriculas']),
      error: (err) => {
        this.guardando = false;
        this.error = err?.error?.mensaje || 'No se pudo crear la matrícula.';
      },
    });
  }

  cancelar(): void {
    this.router.navigate(['/dashboard/matriculas']);
  }
}
