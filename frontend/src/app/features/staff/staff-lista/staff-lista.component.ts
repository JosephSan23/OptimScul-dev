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
  creando = false;

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

  abrirCrear(): void { this.creando = true; }
  cerrar(): void { this.creando = false; }
  onGuardado(): void { this.cargar(); }
}
