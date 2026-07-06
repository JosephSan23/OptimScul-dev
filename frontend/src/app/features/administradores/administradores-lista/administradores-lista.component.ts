import { Component, OnInit } from '@angular/core';
import { AdministradorService, Administrador } from '../../../core/services/administrador.service';

@Component({
  selector: 'app-administradores-lista',
  templateUrl: './administradores-lista.component.html',
  styleUrls: ['./administradores-lista.component.scss']
})
export class AdministradoresListaComponent implements OnInit {

  administradores: Administrador[] = [];
  cargando = false;
  error = '';
  creando = false;
  editando: Administrador | null = null;
  procesandoId: string | null = null;

  constructor(private administradorService: AdministradorService) {}

  ngOnInit(): void { this.cargar(); }

  cargar(): void {
    this.cargando = true;
    this.error = '';
    this.administradorService.listar().subscribe({
      next: (data) => { this.administradores = data; this.cargando = false; },
      error: () => { this.error = 'No se pudieron cargar los administradores.'; this.cargando = false; }
    });
  }

  abrirCrear(): void { this.editando = null; this.creando = true; }
  editar(a: Administrador): void { this.creando = false; this.editando = a; }
  cerrar(): void { this.creando = false; this.editando = null; }

  onGuardado(): void {
    this.cargar();
    if (this.editando) { this.cerrar(); }   // en edición cerramos; en creación dejamos el mensaje visible
  }

  activar(a: Administrador): void {
    this.procesandoId = a.usuarioId;
    this.administradorService.activar(a.usuarioId).subscribe({
      next: () => { this.procesandoId = null; this.cargar(); },
      error: () => { this.procesandoId = null; this.error = 'No se pudo activar.'; }
    });
  }

  inactivar(a: Administrador): void {
    this.procesandoId = a.usuarioId;
    this.administradorService.inactivar(a.usuarioId).subscribe({
      next: () => { this.procesandoId = null; this.cargar(); },
      error: () => { this.procesandoId = null; this.error = 'No se pudo inactivar.'; }
    });
  }
}
