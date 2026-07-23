import { Component, OnInit } from '@angular/core';
import { EscalaService, Escala } from '../../../../core/services/escala.service';
import { ConfigAcademicaService } from '../../../../core/services/config-academica.service';

@Component({
  selector: 'app-escalas-lista',
  templateUrl: './escalas-lista.component.html',
  styleUrls: ['./escalas-lista.component.scss']
})
export class EscalasListaComponent implements OnInit {
  escalas: Escala[] = [];
  cargando = false; error = ''; procesandoId: string | null = null;

  cfgMin: number | null = null;
  cfgMax: number | null = null;
  problemas: string[] = [];

  constructor(private escalaService: EscalaService, private configService: ConfigAcademicaService) {}

  ngOnInit(): void {
    this.configService.obtener().subscribe({
      next: (c) => { this.cfgMin = c.notaMinima; this.cfgMax = c.notaMaxima; this.analizarCobertura(); },
      error: () => {}
    });
    this.cargar();
  }

  cargar(): void {
    this.cargando = true; this.error = '';
    this.escalaService.listar().subscribe({
      next: (d) => { this.escalas = d; this.cargando = false; this.analizarCobertura(); },
      error: () => { this.error = 'No se pudieron cargar las escalas.'; this.cargando = false; }
    });
  }

  /** Detecta huecos en la cobertura [cfgMin, cfgMax) usando las bandas ACTIVAS. */
  private analizarCobertura(): void {
    this.problemas = [];
    if (this.cfgMin == null || this.cfgMax == null || this.escalas.length === 0) return;

    const activas = this.escalas
      .filter(e => e.activa)
      .sort((a, b) => a.notaMinima - b.notaMinima);
    if (activas.length === 0) { this.problemas.push('No hay bandas activas.'); return; }

    if (activas[0].notaMinima > this.cfgMin)
      this.problemas.push(`Falta cubrir de ${this.cfgMin} a ${activas[0].notaMinima}.`);

    for (let i = 1; i < activas.length; i++) {
      const prev = activas[i - 1], cur = activas[i];
      if (cur.notaMinima > prev.notaMaxima)
        this.problemas.push(`Hueco entre ${prev.notaMaxima} y ${cur.notaMinima}.`);
    }

    const ultima = activas[activas.length - 1];
    if (ultima.notaMaxima < this.cfgMax)
      this.problemas.push(`Falta cubrir de ${ultima.notaMaxima} a ${this.cfgMax}.`);
  }

  activar(e: Escala): void {
    this.procesandoId = e.id;
    this.escalaService.activar(e.id).subscribe({
      next: () => { this.procesandoId = null; this.cargar(); },
      error: () => { this.procesandoId = null; this.error = 'No se pudo activar.'; }
    });
  }

  inactivar(e: Escala): void {
    this.procesandoId = e.id;
    this.escalaService.inactivar(e.id).subscribe({
      next: () => { this.procesandoId = null; this.cargar(); },
      error: () => { this.procesandoId = null; this.error = 'No se pudo inactivar.'; }
    });
  }
}
