import { Component, OnInit } from '@angular/core';
import {
  AnioLectivoService,
  AnioLectivo,
} from '../../../core/services/anio-lectivo.service';

@Component({
  selector: 'app-anios-lectivos-lista',
  templateUrl: './anios-lectivos-lista.component.html',
  styleUrls: ['./anios-lectivos-lista.component.scss'],
})
export class AniosLectivosListaComponent implements OnInit {
  anios: AnioLectivo[] = [];
  cargando = false;
  error = '';
  procesandoId: string | null = null;
  constructor(private anioService: AnioLectivoService) {}
  ngOnInit(): void {
    this.cargar();
  }
  cargar(): void {
    this.cargando = true;
    this.error = '';
    this.anioService.listar().subscribe({
      next: (d) => {
        this.anios = d;
        this.cargando = false;
      },
      error: () => {
        this.error = 'No se pudieron cargar los años lectivos.';
        this.cargando = false;
      },
    });
  }
  marcarActual(a: AnioLectivo): void {
    this.procesandoId = a.id;
    this.anioService.marcarActual(a.id).subscribe({
      next: () => {
        this.procesandoId = null;
        this.cargar();
      },
      error: () => {
        this.procesandoId = null;
        this.error = 'No se pudo marcar como actual.';
      },
    });
  }
}
