import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PerfilService, Perfil } from '../../../core/services/perfil.service';
import {
  validarEsquema,
  hayErrores,
  validarCampo,
  Esquema,
  ErroresForm,
} from '../../../core/validation/form-validator';
import {
  requerido,
  soloLetras,
  correo,
  telefonoCo,
  fechaNacimiento,
  longitudMin,
  coincideCon,
} from '../../../core/validation/validators';

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

  // Validación de la pestaña "datos" (sobre el objeto p).
  esquemaDatos: Esquema = {
    segundoNombre: [soloLetras()],
    segundoApellido: [soloLetras()],
    fechaNacimiento: [requerido('La fecha de nacimiento es obligatoria'), fechaNacimiento()],
    sexo: [requerido('Selecciona el sexo')],
    telefono: [requerido('El teléfono es obligatorio'), telefonoCo()],
    telefonoAlternativo: [telefonoCo()],
    correo: [correo()],
    direccion: [requerido('La dirección es obligatoria')],
    ciudad: [requerido('La ciudad es obligatoria')],
  };
  erroresDatos: ErroresForm = {};

  // Validación de la pestaña "contraseña".
  esquemaPass: Esquema = {
    actual: [requerido('Ingresa tu contraseña actual')],
    nueva: [requerido('Ingresa la nueva contraseña'), longitudMin(8, 'La contraseña debe tener al menos 8 caracteres')],
    confirmar: [requerido('Confirma la contraseña'), coincideCon('nueva', 'Las contraseñas no coinciden')],
  };
  erroresPass: ErroresForm = {};

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

  validarDato(campo: string): void {
    const msg = validarCampo(campo, this.p, this.esquemaDatos);
    if (msg) this.erroresDatos[campo] = msg;
    else delete this.erroresDatos[campo];
  }

  validarPass(campo: string): void {
    const msg = validarCampo(campo, this.pass, this.esquemaPass);
    if (msg) this.erroresPass[campo] = msg;
    else delete this.erroresPass[campo];
  }

  guardarDatos(): void {
    this.error = ''; this.exito = '';
    this.erroresDatos = validarEsquema(this.p, this.esquemaDatos);
    if (hayErrores(this.erroresDatos)) {
      this.error = 'Revisa los campos marcados en rojo.';
      return;
    }
    this.guardando = true;
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
    this.erroresPass = validarEsquema(this.pass, this.esquemaPass);
    if (hayErrores(this.erroresPass)) {
      this.error = 'Revisa los campos marcados en rojo.';
      return;
    }
    this.guardando = true;
    this.perfilSvc.cambiarPassword(this.pass.actual, this.pass.nueva).subscribe({
      next: () => {
        this.guardando = false; this.exito = 'Contraseña actualizada.';
        this.pass = { actual: '', nueva: '', confirmar: '' };
        this.erroresPass = {};
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
