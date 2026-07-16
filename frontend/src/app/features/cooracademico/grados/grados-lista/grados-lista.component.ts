import { Component, OnInit } from '@angular/core';
import { GradoService, Grado } from '../../../../core/services/grado.service';

@Component({
  selector: 'app-grados-lista',
  templateUrl: './grados-lista.component.html',
  styleUrls: ['./grados-lista.component.scss'],
})
export class GradosListaComponent implements OnInit {
  grados: Grado[] = [];
  cargando = false;
  error = '';
  procesandoId: string | null = null;

  readonly nivelesOrden = [
    'PREESCOLAR',
    'PRIMARIA',
    'SECUNDARIA',
    'MEDIA',
    'TECNICA',
    'OTRO',
  ];

  constructor(private gradoService: GradoService) {}

  ngOnInit(): void {
    this.cargar();
  }

  cargar(): void {
    this.cargando = true;
    this.error = '';
    this.gradoService.listar().subscribe({
      next: (d) => {
        this.grados = d;
        this.cargando = false;
      },
      error: () => {
        this.error = 'No se pudieron cargar los grados.';
        this.cargando = false;
      },
    });
  }

  activar(g: Grado): void {
    this.procesandoId = g.id;
    this.gradoService.activar(g.id).subscribe({
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

  inactivar(g: Grado): void {
    this.procesandoId = g.id;
    this.gradoService.inactivar(g.id).subscribe({
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

  nivelTexto(n: string): string {
    return n ? n.charAt(0) + n.slice(1).toLowerCase() : '';
  }
}
