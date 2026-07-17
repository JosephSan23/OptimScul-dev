import { Component, OnInit } from '@angular/core';
import {
  AsignaturaService,
  Asignatura,
} from '../../../../core/services/asignatura.service';

@Component({
  selector: 'app-asignaturas-lista',
  templateUrl: './asignaturas-lista.component.html',
  styleUrls: ['./asignaturas-lista.component.scss'],
})
export class AsignaturasListaComponent implements OnInit {
  asignaturas: Asignatura[] = [];
  cargando = false;
  error = '';
  procesandoId: string | null = null;

  constructor(private asignaturaService: AsignaturaService) {}

  ngOnInit(): void {
    this.cargar();
  }

  cargar(): void {
    this.cargando = true;
    this.error = '';
    this.asignaturaService.listar().subscribe({
      next: (d) => {
        this.asignaturas = d;
        this.cargando = false;
      },
      error: () => {
        this.error = 'No se pudieron cargar las asignaturas.';
        this.cargando = false;
      },
    });
  }

  activar(a: Asignatura): void {
    this.procesandoId = a.id;
    this.asignaturaService.activar(a.id).subscribe({
      next: () => {
        this.procesandoId = null;
        this.cargar();
      },
      error: () => {
        this.procesandoId = null;
        this.error = 'No se pudo activar.';
      },
    });
  }

  inactivar(a: Asignatura): void {
    this.procesandoId = a.id;
    this.asignaturaService.inactivar(a.id).subscribe({
      next: () => {
        this.procesandoId = null;
        this.cargar();
      },
      error: () => {
        this.procesandoId = null;
        this.error = 'No se pudo inactivar.';
      },
    });
  }
}
