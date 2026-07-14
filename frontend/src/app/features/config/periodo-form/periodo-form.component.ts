import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PeriodoService } from '../../../core/services/periodo.service';

@Component({
  selector: 'app-periodo-form',
  templateUrl: './periodo-form.component.html',
  styleUrls: ['./periodo-form.component.scss']
})
export class PeriodoFormComponent implements OnInit {
  modoEdicion = false;
  periodoId: string | null = null;
  anioId = '';

  form: { numero: number | null; nombre: string; descripcion: string; fechaInicio: string; fechaFin: string; peso: number | null; estado: string } = {
    numero: null, nombre: '', descripcion: '', fechaInicio: '', fechaFin: '', peso: null, estado: 'PLANEADO'
  };
  estados = ['PLANEADO', 'ACTIVO', 'CERRADO', 'ANULADO'];
  cargando = false; guardando = false; error = '';

  constructor(private periodoService: PeriodoService, private route: ActivatedRoute, private router: Router) {}

  ngOnInit(): void {
    this.periodoId = this.route.snapshot.paramMap.get('id');
    if (this.periodoId) {
      this.modoEdicion = true;
      this.cargar();
    } else {
      this.anioId = this.route.snapshot.paramMap.get('anioId') || '';
    }
  }

  cargar(): void {
    this.cargando = true;
    this.periodoService.obtener(this.periodoId!).subscribe({
      next: (p) => {
        this.anioId = p.anioLectivoId;
        this.form = {
          numero: p.numero ?? null, nombre: p.nombre ?? '', descripcion: p.descripcion ?? '',
          fechaInicio: p.fechaInicio ?? '', fechaFin: p.fechaFin ?? '', peso: p.peso ?? null, estado: p.estado ?? 'PLANEADO'
        };
        this.cargando = false;
      },
      error: () => { this.error = 'No se pudo cargar el periodo.'; this.cargando = false; }
    });
  }

  guardar(): void {
    this.error = '';
    if (!this.form.numero || !this.form.nombre.trim() || !this.form.fechaInicio || !this.form.fechaFin) {
      this.error = 'Número, nombre y fechas son obligatorios.'; return;
    }
    this.guardando = true;
    const p = this.modoEdicion
      ? this.periodoService.editar(this.periodoId!, this.form)
      : this.periodoService.crear(this.anioId, this.form);
    p.subscribe({
      next: () => this.volver(),
      error: (err) => { this.guardando = false; this.error = err?.error?.mensaje || 'No se pudo guardar.'; }
    });
  }

  volver(): void { this.router.navigate(['/dashboard/anios-lectivos', this.anioId, 'periodos']); }
  cancelar(): void { this.volver(); }
}
