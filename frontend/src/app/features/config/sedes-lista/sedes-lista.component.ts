import { Component, OnInit } from '@angular/core';
import { SedeService, Sede } from '../../../core/services/sede.service';

@Component({
  selector: 'app-sedes-lista',
  templateUrl: './sedes-lista.component.html',
  styleUrls: ['./sedes-lista.component.scss']
})
export class SedesListaComponent implements OnInit {
  sedes: Sede[] = [];
  cargando = false;
  error = '';
  procesandoId: string | null = null;

  // búsqueda local (las sedes son pocas — no necesita paginación)
  filtro = '';

  // estado del panel lateral
  panelAbierto = false;
  sedeEditandoId: string | null = null;   // null = crear

  constructor(private sedeService: SedeService) {}

  ngOnInit(): void { this.cargar(); }

  cargar(): void {
    this.cargando = true; this.error = '';
    this.sedeService.listar().subscribe({
      next: (d) => { this.sedes = d; this.cargando = false; },
      error: () => { this.error = 'No se pudieron cargar las sedes.'; this.cargando = false; }
    });
  }

  get sedesFiltradas(): Sede[] {
    const f = this.filtro.trim().toLowerCase();
    if (!f) return this.sedes;
    return this.sedes.filter(s =>
      [s.codigo, s.nombre, s.ciudad, s.departamento]
        .some(v => (v ?? '').toLowerCase().includes(f))
    );
  }

  /* ---------- panel lateral ---------- */
  abrirCrear(): void {
    this.sedeEditandoId = null;
    this.panelAbierto = true;
  }

  abrirEditar(s: Sede): void {
    this.sedeEditandoId = s.id;
    this.panelAbierto = true;
  }

  cerrarPanel(): void {
    this.panelAbierto = false;
    this.sedeEditandoId = null;
  }

  onGuardado(): void {
    this.cerrarPanel();
    this.cargar();
  }

  /* ---------- activar / inactivar ---------- */
  activar(s: Sede): void {
    this.procesandoId = s.id;
    this.sedeService.activar(s.id).subscribe({
      next: () => { this.procesandoId = null; this.cargar(); },
      error: () => { this.procesandoId = null; this.error = 'No se pudo activar.'; }
    });
  }

  inactivar(s: Sede): void {
    this.procesandoId = s.id;
    this.sedeService.inactivar(s.id).subscribe({
      next: () => { this.procesandoId = null; this.cargar(); },
      error: () => { this.procesandoId = null; this.error = 'No se pudo inactivar.'; }
    });
  }
}
