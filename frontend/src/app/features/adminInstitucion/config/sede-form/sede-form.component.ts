import { Component, Input, Output, EventEmitter, OnChanges, SimpleChanges } from '@angular/core';
import { SedeService } from '../../../../core/services/sede.service';

/*
  Antes este componente era una PÁGINA con ruta propia
  (/dashboard/sedes/nuevo y /dashboard/sedes/:id).
  Ahora vive dentro del panel lateral de la lista:
  - recibe [sedeId] (null = crear, id = editar)
  - emite (guardado) y (cancelado) — el padre cierra el panel y recarga.
  Puedes eliminar las dos rutas del routing module.
*/
@Component({
  selector: 'app-sede-form',
  templateUrl: './sede-form.component.html',
  styleUrls: ['./sede-form.component.scss']
})
export class SedeFormComponent implements OnChanges {

  @Input() sedeId: string | null = null;
  @Output() guardado = new EventEmitter<void>();
  @Output() cancelado = new EventEmitter<void>();

  modoEdicion = false;

  form = {
    codigo: '', nombre: '', descripcion: '', direccion: '', telefono: '',
    correo: '', ciudad: '', departamento: '', pais: '', principal: false
  };

  cargando = false;
  guardando = false;
  error = '';

  constructor(private sedeService: SedeService) {}

  ngOnChanges(changes: SimpleChanges): void {
    if ('sedeId' in changes) {
      this.error = '';
      this.guardando = false;
      this.modoEdicion = !!this.sedeId;
      if (this.modoEdicion) this.cargar();
      else this.reiniciar();
    }
  }

  private reiniciar(): void {
    this.form = {
      codigo: '', nombre: '', descripcion: '', direccion: '', telefono: '',
      correo: '', ciudad: '', departamento: '', pais: '', principal: false
    };
  }

  private cargar(): void {
    this.cargando = true;
    this.sedeService.obtener(this.sedeId!).subscribe({
      next: (s) => {
        this.form = {
          codigo: s.codigo ?? '', nombre: s.nombre ?? '', descripcion: s.descripcion ?? '',
          direccion: s.direccion ?? '', telefono: s.telefono ?? '', correo: s.correo ?? '',
          ciudad: s.ciudad ?? '', departamento: s.departamento ?? '', pais: s.pais ?? '',
          principal: !!s.principal
        };
        this.cargando = false;
      },
      error: () => { this.error = 'No se pudo cargar la sede.'; this.cargando = false; }
    });
  }

  guardar(): void {
    this.error = '';
    if (!this.form.codigo.trim() || !this.form.nombre.trim()) {
      this.error = 'Código y nombre son obligatorios.';
      return;
    }
    this.guardando = true;
    const p = this.modoEdicion
      ? this.sedeService.editar(this.sedeId!, this.form)
      : this.sedeService.crear(this.form);
    p.subscribe({
      next: () => { this.guardando = false; this.guardado.emit(); },
      error: (err) => { this.guardando = false; this.error = err?.error?.mensaje || 'No se pudo guardar.'; }
    });
  }

  cancelar(): void { this.cancelado.emit(); }
}
