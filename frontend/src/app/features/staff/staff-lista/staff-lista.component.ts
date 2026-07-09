import { Component, OnInit } from '@angular/core';
import { StaffService, Staff } from '../../../core/services/staff.service';

@Component({
  selector: 'app-staff-lista',
  templateUrl: './staff-lista.component.html',
  styleUrls: ['./staff-lista.component.scss']
})
export class StaffListaComponent implements OnInit {

  staff: Staff[] = [];
  cargando = false;
  error = '';

  constructor(private staffService: StaffService) {}

  ngOnInit(): void { this.cargar(); }

  cargar(): void {
    this.cargando = true;
    this.error = '';
    this.staffService.listar().subscribe({
      next: (data) => { this.staff = data; this.cargando = false; },
      error: () => { this.error = 'No se pudo cargar el personal.'; this.cargando = false; }
    });
  }

  procesandoId: string | null = null;

  activar(s: Staff): void {
    this.procesandoId = s.usuarioId;
    this.staffService.activar(s.usuarioId).subscribe({
      next: () => { this.procesandoId = null; this.cargar(); },
      error: () => { this.procesandoId = null; this.error = 'No se pudo activar.'; }
    });
  }
  inactivar(s: Staff): void {
    this.procesandoId = s.usuarioId;
    this.staffService.inactivar(s.usuarioId).subscribe({
      next: () => { this.procesandoId = null; this.cargar(); },
      error: () => { this.procesandoId = null; this.error = 'No se pudo inactivar.'; }
    });
  }
}
