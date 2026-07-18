import { Component, OnInit } from '@angular/core';
import {
  CargaService,
  CargaResumen,
} from '../../../../core/services/carga.service';
import {
  AnioLectivoService,
  AnioLectivo,
} from '../../../../core/services/anio-lectivo.service';
import { FiltroTabla } from '../../../../core/components/tabla-paginada/tabla-paginada.component';

@Component({
  selector: 'app-cargas-lista',
  templateUrl: './cargas-lista.component.html',
  styleUrls: ['./cargas-lista.component.scss'],
})
export class CargasListaComponent implements OnInit {
  anios: AnioLectivo[] = [];
  anioSeleccionado = '';
  cargas: CargaResumen[] = [];
  cargando = false;
  error = '';
  procesandoId: string | null = null;

  filtrosTabla: FiltroTabla[] = [
    { campo: 'gradoNombre', etiqueta: 'Grado' },
    { campo: 'estado', etiqueta: 'Estado' },
  ];

  constructor(
    private cargaService: CargaService,
    private anioService: AnioLectivoService,
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
    this.cargaService.listarPorAnio(this.anioSeleccionado).subscribe({
      next: (d) => {
        this.cargas = d;
        this.cargando = false;
      },
      error: () => {
        this.error = 'No se pudo cargar la carga académica.';
        this.cargando = false;
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

  finalizar(c: CargaResumen): void {
    this.accion(c.id, this.cargaService.finalizar(c.id));
  }
  cancelar(c: CargaResumen): void {
    this.accion(c.id, this.cargaService.cancelar(c.id));
  }
  reactivar(c: CargaResumen): void {
    this.accion(c.id, this.cargaService.reactivar(c.id));
  }
}
