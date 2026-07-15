import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import {
  AcudienteService,
  AcudienteDeEstudiante,
} from '../../../../core/services/acudiente.service';

@Component({
  selector: 'app-acudientes-lista',
  templateUrl: './acudientes-lista.component.html',
  styleUrls: ['./acudientes-lista.component.scss'],
})
export class AcudientesListaComponent implements OnInit {
  estudianteId = '';
  acudientes: AcudienteDeEstudiante[] = [];
  cargando = false;
  error = '';
  procesandoId: string | null = null;

  constructor(
    private acudienteService: AcudienteService,
    private route: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.estudianteId = this.route.snapshot.paramMap.get('estudianteId') || '';
    this.cargar();
  }

  cargar(): void {
    this.cargando = true;
    this.error = '';
    this.acudienteService.listarPorEstudiante(this.estudianteId).subscribe({
      next: (d) => {
        this.acudientes = d;
        this.cargando = false;
      },
      error: () => {
        this.error = 'No se pudieron cargar los acudientes.';
        this.cargando = false;
      },
    });
  }

  desvincular(a: AcudienteDeEstudiante): void {
    if (
      !confirm(
        `¿Desvincular a ${a.primerNombre} ${a.primerApellido} de este estudiante?`,
      )
    )
      return;
    this.procesandoId = a.vinculoId;
    this.acudienteService.desvincular(a.vinculoId).subscribe({
      next: () => {
        this.procesandoId = null;
        this.cargar();
      },
      error: () => {
        this.procesandoId = null;
        this.error = 'No se pudo desvincular.';
      },
    });
  }
}
