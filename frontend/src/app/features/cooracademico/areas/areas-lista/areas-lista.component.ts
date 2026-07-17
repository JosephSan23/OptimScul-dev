import { Component, OnInit } from '@angular/core';
import { AreaService, Area } from '../../../../core/services/area.service';

@Component({
  selector: 'app-areas-lista',
  templateUrl: './areas-lista.component.html',
  styleUrls: ['./areas-lista.component.scss'],
})
export class AreasListaComponent implements OnInit {
  areas: Area[] = [];
  cargando = false;
  error = '';
  procesandoId: string | null = null;

  constructor(private areaService: AreaService) {}

  ngOnInit(): void {
    this.cargar();
  }

  cargar(): void {
    this.cargando = true;
    this.error = '';
    this.areaService.listar().subscribe({
      next: (d) => {
        this.areas = d;
        this.cargando = false;
      },
      error: () => {
        this.error = 'No se pudieron cargar las áreas.';
        this.cargando = false;
      },
    });
  }

  activar(a: Area): void {
    this.procesandoId = a.id;
    this.areaService.activar(a.id).subscribe({
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

  inactivar(a: Area): void {
    this.procesandoId = a.id;
    this.areaService.inactivar(a.id).subscribe({
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
