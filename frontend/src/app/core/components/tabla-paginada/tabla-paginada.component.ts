import { Component, ContentChild, Input, OnChanges, TemplateRef } from '@angular/core';
import { FilaTablaDirective } from '../../directives/fila-tabla.directive';

/*
  Configuración de un filtro por campo:
  - campo:    la propiedad del item sobre la que filtra (ej: 'rolNombre')
  - etiqueta: lo que ve el usuario (ej: 'Rol')
  - opciones: valores posibles; si se omite, se derivan
              automáticamente de los datos (valores únicos).
  Uso en el padre:
    [filtros]="[{ campo: 'rolNombre', etiqueta: 'Rol' },
                { campo: 'estado',    etiqueta: 'Estado' }]"
*/
export interface FiltroTabla {
  campo: string;
  etiqueta: string;
  opciones?: string[];
}

@Component({
  selector: 'app-tabla-paginada',
  templateUrl: './tabla-paginada.component.html',
  styleUrls: ['./tabla-paginada.component.scss']
})
export class TablaPaginadaComponent implements OnChanges {

  @Input() items: any[] = [];
  @Input() camposBusqueda: string[] = [];        // campos donde busca el texto libre
  @Input() filtros: FiltroTabla[] = [];          // filtros por campo (opcional)
  @Input() tamanoPagina = 10;
  @Input() placeholderBusqueda = 'Buscar...';
  @Input() mensajeVacio = 'No hay resultados.';

  @ContentChild(FilaTablaDirective, { read: TemplateRef }) filaTpl!: TemplateRef<any>;

  termino = '';
  paginaActual = 1;

  // estado de los filtros
  filtrosAbiertos = false;
  valoresFiltro: Record<string, string> = {};    // campo -> valor elegido ('' = todos)

  ngOnChanges(): void {
    this.paginaActual = 1;   // si cambian los datos, vuelve a la primera página
  }

  /* ---------- filtros ---------- */

  /** opciones de un filtro: las configuradas, o los valores únicos de los datos */
  opcionesDe(f: FiltroTabla): string[] {
    if (f.opciones?.length) return f.opciones;
    const unicos = new Set<string>();
    for (const item of this.items) {
      const v = item?.[f.campo];
      if (v != null && String(v).trim() !== '') unicos.add(String(v));
    }
    return Array.from(unicos).sort();
  }

  get numFiltrosActivos(): number {
    return Object.values(this.valoresFiltro).filter(v => !!v).length;
  }

  alCambiarFiltro(): void {
    this.paginaActual = 1;
  }

  limpiarFiltros(): void {
    this.valoresFiltro = {};
    this.paginaActual = 1;
  }

  /* ---------- búsqueda + filtros combinados ---------- */
  get filtrados(): any[] {
    const t = this.termino.trim().toLowerCase();

    return this.items.filter(item => {
      // 1) texto libre sobre camposBusqueda
      const pasaTexto = !t || this.camposBusqueda.some(campo => {
        const valor = item?.[campo];
        return valor != null && String(valor).toLowerCase().includes(t);
      });
      if (!pasaTexto) return false;

      // 2) cada filtro activo debe coincidir exactamente
      for (const f of this.filtros) {
        const elegido = this.valoresFiltro[f.campo];
        if (elegido && String(item?.[f.campo] ?? '') !== elegido) return false;
      }
      return true;
    });
  }

  /* ---------- paginación (igual que antes) ---------- */
  get totalPaginas(): number {
    return Math.max(1, Math.ceil(this.filtrados.length / this.tamanoPagina));
  }

  get pagina(): any[] {
    const p = Math.min(this.paginaActual, this.totalPaginas);
    const inicio = (p - 1) * this.tamanoPagina;
    return this.filtrados.slice(inicio, inicio + this.tamanoPagina);
  }

  get paginasVisibles(): number[] {
    return Array.from({ length: this.totalPaginas }, (_, i) => i + 1);
  }

  get desde(): number {
    return this.filtrados.length === 0 ? 0 : (this.paginaActual - 1) * this.tamanoPagina + 1;
  }
  get hasta(): number {
    return Math.min(this.paginaActual * this.tamanoPagina, this.filtrados.length);
  }

  buscar(): void { this.paginaActual = 1; }

  irA(pagina: number): void {
    if (pagina < 1 || pagina > this.totalPaginas) return;
    this.paginaActual = pagina;
  }
}
