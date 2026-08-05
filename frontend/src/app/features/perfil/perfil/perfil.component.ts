import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PerfilService, Perfil } from '../../../core/services/perfil.service';

@Component({
  selector: 'app-perfil',
  templateUrl: './perfil.component.html',
  styleUrls: ['./perfil.component.scss'],
})
export class PerfilComponent implements OnInit {
  p!: Perfil;
  cargando = true;
  guardando = false;
  error = ''; exito = '';
  tab: 'datos' | 'password' | 'foto' = 'datos';
  sexos = ['MASCULINO', 'FEMENINO', 'OTRO'];

  pass = { actual: '', nueva: '', confirmar: '' };

  constructor(private perfilSvc: PerfilService, private router: Router) {}

  ngOnInit(): void {
    this.perfilSvc.perfil().subscribe({
      next: (p) => {
        this.p = { ...p };
        // en el primer ingreso, primero contraseña; luego datos
        this.tab = p.requiereCambioPassword ? 'password' : 'datos';
        this.cargando = false;
      },
      error: () => { this.error = 'No se pudo cargar el perfil.'; this.cargando = false; },
    });
  }

  get obligatorio(): boolean { return this.p?.requiereCambioPassword || !this.p?.perfilCompleto; }
  falta(campo: string): boolean { return this.p?.camposFaltantes?.includes(campo); }

  guardarDatos(): void {
    this.error = ''; this.exito = ''; this.guardando = true;
    this.perfilSvc.actualizar(this.p).subscribe({
      next: (p) => {
        this.p = { ...p }; this.guardando = false; this.exito = 'Datos guardados.';
        this.perfilSvc.refrescar();
        if (p.perfilCompleto && !p.requiereCambioPassword) this.router.navigate(['/dashboard']);
      },
      error: (e) => { this.guardando = false; this.error = e?.error?.message || 'No se pudo guardar.'; },
    });
  }

  cambiarPassword(): void {
    this.error = ''; this.exito = '';
    if (this.pass.nueva !== this.pass.confirmar) { this.error = 'Las contraseñas no coinciden.'; return; }
    this.guardando = true;
    this.perfilSvc.cambiarPassword(this.pass.actual, this.pass.nueva).subscribe({
      next: () => {
        this.guardando = false; this.exito = 'Contraseña actualizada.';
        this.pass = { actual: '', nueva: '', confirmar: '' };
        this.p.requiereCambioPassword = false;
        this.perfilSvc.refrescar();
        this.tab = 'datos';   // pasa a completar datos
      },
      error: (e) => { this.guardando = false; this.error = e?.error?.message || 'No se pudo cambiar.'; },
    });
  }

  onFoto(ev: Event): void {
    const file = (ev.target as HTMLInputElement).files?.[0];
    if (!file) return;
    this.perfilSvc.subirFoto(file).subscribe({
      next: (r) => { this.p.fotoUrl = r.fotoUrl; this.exito = 'Foto actualizada.'; },
      error: (e) => { this.error = e?.error?.message || 'No se pudo subir la foto.'; },
    });
  }
}
