import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PeriodoService, Periodo } from '../../../core/services/periodo.service';

@Component({
  selector: 'app-periodos-lista',
  templateUrl: './periodos-lista.component.html',
  styleUrls: ['./periodos-lista.component.scss']
})
export class PeriodosListaComponent implements OnInit {
  anioId = '';
  periodos: Periodo[] = [];
  cargando = false; error = '';
  constructor(private periodoService: PeriodoService, private route: ActivatedRoute) {}
  ngOnInit(): void {
    this.anioId = this.route.snapshot.paramMap.get('anioId') || '';
    this.cargar();
  }
  cargar(): void {
    this.cargando = true; this.error = '';
    this.periodoService.listarPorAnio(this.anioId).subscribe({
      next: (d) => { this.periodos = d; this.cargando = false; },
      error: () => { this.error = 'No se pudieron cargar los periodos.'; this.cargando = false; }
    });
  }
}
