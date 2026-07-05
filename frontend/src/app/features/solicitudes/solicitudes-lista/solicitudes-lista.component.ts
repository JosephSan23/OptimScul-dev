import { Component, OnInit } from '@angular/core';
import { SolicitudService, Solicitud } from '../../../core/services/solicitud.service';

@Component({
  selector: 'app-solicitudes-lista',
  templateUrl: './solicitudes-lista.component.html',
  styleUrls: ['./solicitudes-lista.component.scss']
})
export class SolicitudesListaComponent implements OnInit {

  solicitudes: Solicitud[] = [];
  seleccionada: Solicitud | null = null;   // la que se muestra en el panel derecho
  cargando = false;
  error = '';
  procesandoId: string | null = null;

  constructor(private solicitudService: SolicitudService) {}

  ngOnInit(): void {
    this.cargar();
  }

  cargar(): void {
    this.cargando = true;
    this.error = '';
    this.solicitudService.listar().subscribe({
      next: (data) => {
        this.solicitudes = data;
        this.cargando = false;
        // Si la seleccionada ya no existe o cambió, refrescamos la referencia
        if (this.seleccionada) {
          this.seleccionada = data.find(s => s.id === this.seleccionada!.id) || null;
        }
      },
      error: () => { this.error = 'No se pudieron cargar las solicitudes.'; this.cargando = false; }
    });
  }

  get pendientes(): Solicitud[] {
    return this.solicitudes.filter(s => s.estado === 'PENDIENTE');
  }
  get aprobadas(): Solicitud[] {
    return this.solicitudes.filter(s => s.estado === 'APROBADA');
  }
  get rechazadas(): Solicitud[] {
    return this.solicitudes.filter(s => s.estado === 'RECHAZADA');
  }

  seleccionar(sol: Solicitud): void {
    this.seleccionada = sol;
  }

  aprobar(sol: Solicitud): void {
    this.procesandoId = sol.id;
    this.solicitudService.aprobar(sol.id).subscribe({
      next: () => { this.procesandoId = null; this.cargar(); },
      error: () => { this.procesandoId = null; this.error = 'No se pudo aprobar.'; }
    });
  }

  rechazar(sol: Solicitud): void {
    const motivo = prompt('Motivo del rechazo:');
    if (!motivo || !motivo.trim()) return;
    this.procesandoId = sol.id;
    this.solicitudService.rechazar(sol.id, motivo).subscribe({
      next: () => { this.procesandoId = null; this.cargar(); },
      error: () => { this.procesandoId = null; this.error = 'No se pudo rechazar.'; }
    });
  }
}
