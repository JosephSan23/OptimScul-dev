import { Component, ContentChild, Input, OnChanges, TemplateRef } from '@angular/core';
import { FilaTablaDirective } from '../../directives/fila-tabla.directive';

@Component({
  selector: 'app-tabla-paginada',
  templateUrl: './tabla-paginada.component.html',
  styleUrls: ['./tabla-paginada.component.scss']
})
export class TablaPaginadaComponent implements OnChanges {

  @Input() items: any[] = [];
  @Input() camposBusqueda: string[] = [];        // campos donde busca el filtro
  @Input() tamanoPagina = 10;
  @Input() placeholderBusqueda = 'Buscar...';
  @Input() mensajeVacio = 'No hay resultados.';

  @ContentChild(FilaTablaDirective, { read: TemplateRef }) filaTpl!: TemplateRef<any>;
  termino = '';
  paginaActual = 1;

  ngOnChanges(): void {
    this.paginaActual = 1;   // si cambian los datos, vuelve a la primera página
  }

  get filtrados(): any[] {
    const t = this.termino.trim().toLowerCase();
    if (!t) return this.items;
    return this.items.filter(item =>
      this.camposBusqueda.some(campo => {
        const valor = item?.[campo];
        return valor != null && String(valor).toLowerCase().includes(t);
      })
    );
  }

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
