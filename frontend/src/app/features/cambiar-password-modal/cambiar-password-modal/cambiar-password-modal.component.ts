import { Component, EventEmitter, Input, Output } from '@angular/core';
import { PerfilService } from '../../../core/services/perfil.service';

@Component({
  selector: 'app-cambiar-password-modal',
  templateUrl: './cambiar-password-modal.component.html',
  styleUrls: ['./cambiar-password-modal.component.scss'],
})
export class CambiarPasswordModalComponent {
  /** true = primer ingreso: obligatorio, sin botón de cerrar */
  @Input() obligatorio = false;
  @Output() guardado = new EventEmitter<void>();
  @Output() cerrado = new EventEmitter<void>();

  pass = { actual: '', nueva: '', confirmar: '' };
  verNueva = false;
  error = '';
  guardando = false;

  constructor(private perfilService: PerfilService) {}

  get reglas() {
    const n = this.pass.nueva;
    return {
      longitud:  n.length >= 8,
      mayuscula: /[A-Z]/.test(n),
      numero:    /[0-9]/.test(n),
      coincide:  n.length > 0 && n === this.pass.confirmar,
    };
  }
  get valida(): boolean {
    const r = this.reglas;
    return r.longitud && r.mayuscula && r.numero && r.coincide && this.pass.actual.length > 0;
  }

  guardar(): void {
    if (!this.valida) return;
    this.guardando = true; this.error = '';
    this.perfilService.cambiarPassword(this.pass.actual, this.pass.nueva).subscribe({
      next: () => { this.guardando = false; this.perfilService.refrescar(); this.guardado.emit(); },
      error: (e) => { this.guardando = false; this.error = e?.error?.message || 'No se pudo cambiar la contraseña.'; },
    });
  }

  onCerrar(): void { if (!this.obligatorio) this.cerrado.emit(); }
}
