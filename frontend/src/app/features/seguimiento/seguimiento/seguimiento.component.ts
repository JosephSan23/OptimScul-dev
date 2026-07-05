import { Component, OnInit } from '@angular/core';
import { SolicitudService, Solicitud } from '../../../core/services/solicitud.service';

@Component({
  selector: 'app-seguimiento',
  templateUrl: './seguimiento.component.html',
  styleUrls: ['./seguimiento.component.scss']
})
export class SeguimientoComponent implements OnInit {

  solicitudes: Solicitud[] = [];
  cargando = false;
  error = '';

  constructor(private solicitudService: SolicitudService) {}

  ngOnInit(): void {
    this.cargar();
  }

  cargar(): void {
    this.cargando = true;
    this.error = '';
    this.solicitudService.misSolicitudes().subscribe({
      next: (data) => { this.solicitudes = data; this.cargando = false; },
      error: () => { this.error = 'No se pudieron cargar tus solicitudes.'; this.cargando = false; }
    });
  }

  claseEstado(estado: string): string {
    switch (estado) {
      case 'PENDIENTE': return 'estado-pendiente';
      case 'APROBADA':  return 'estado-aprobada';
      case 'RECHAZADA': return 'estado-rechazada';
      default:          return 'estado-pendiente';
    }
  }

  etiquetaEstado(estado: string): string {
    switch (estado) {
      case 'PENDIENTE': return 'En revisión';
      case 'APROBADA':  return 'Aprobada';
      case 'RECHAZADA': return 'Rechazada';
      default:          return estado;
    }
  }

  textoEstado(estado: string): string {
    switch (estado) {
      case 'PENDIENTE': return 'Estamos revisando tu solicitud. Te contactaremos pronto.';
      case 'APROBADA':  return '¡Tu institución fue aprobada y creada en la plataforma!';
      case 'RECHAZADA': return 'Tu solicitud no fue aprobada en esta ocasión.';
      default:          return '';
    }
  }
}
