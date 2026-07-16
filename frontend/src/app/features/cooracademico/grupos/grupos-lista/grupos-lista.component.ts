import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { GrupoService, Grupo } from '../../../../core/services/grupo.service';
import { GradoService, Grado } from '../../../../core/services/grado.service';
import { AnioLectivoService, AnioLectivo } from '../../../../core/services/anio-lectivo.service';

@Component({
  selector: 'app-grupos-lista',
  templateUrl: './grupos-lista.component.html',
  styleUrls: ['./grupos-lista.component.scss']
})
export class GruposListaComponent implements OnInit {
  gradoId!: string;
  grado: Grado | null = null;
  grupos: Grupo[] = [];
  aniosPorId: Record<string, AnioLectivo> = {};
  cargando = false; error = ''; procesandoId: string | null = null;

  constructor(
    private grupoService: GrupoService,
    private gradoService: GradoService,
    private anioService: AnioLectivoService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.gradoId = this.route.snapshot.paramMap.get('gradoId')!;
    this.cargarGrado();
    this.cargarAnios();
    this.cargar();
  }

  cargarGrado(): void {
    this.gradoService.obtener(this.gradoId).subscribe({
      next: (g) => this.grado = g,
      error: () => {}
    });
  }

  cargarAnios(): void {
    this.anioService.listar().subscribe({
      next: (anios) => anios.forEach(a => this.aniosPorId[a.id] = a),
      error: () => {}
    });
  }

  cargar(): void {
    this.cargando = true; this.error = '';
    this.grupoService.listarPorGrado(this.gradoId).subscribe({
      next: (d) => { this.grupos = d; this.cargando = false; },
      error: () => { this.error = 'No se pudieron cargar los grupos.'; this.cargando = false; }
    });
  }

  nombreAnio(anioId: string): string {
    const a = this.aniosPorId[anioId];
    return a ? (a.nombre || String(a.anio)) : '';
  }

  activar(g: Grupo): void {
    this.procesandoId = g.id;
    this.grupoService.activar(g.id).subscribe({
      next: () => { this.procesandoId = null; this.cargar(); },
      error: () => { this.procesandoId = null; this.error = 'No se pudo activar.'; }
    });
  }

  inactivar(g: Grupo): void {
    this.procesandoId = g.id;
    this.grupoService.inactivar(g.id).subscribe({
      next: () => { this.procesandoId = null; this.cargar(); },
      error: () => { this.procesandoId = null; this.error = 'No se pudo inactivar.'; }
    });
  }
}
