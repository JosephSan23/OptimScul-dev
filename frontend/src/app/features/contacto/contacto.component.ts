import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { SolicitudService } from '../../core/services/solicitud.service';
import {
  correo,
  telefonoCo,
  soloDigitos,
  documentoPorTipo,
  fechaNacimiento as validarFechaNac,
} from '../../core/validation/validators';

@Component({
  selector: 'app-contacto',
  templateUrl: './contacto.component.html',
  styleUrl: './contacto.component.scss'
})
export class ContactoComponent {
  tipoUsuario: 'institucion' | 'padre' | null = null;

  pasoActual = 1;
  mostrarError = false;
  mensajeError = '';

  // Estado del envío al backend
  enviando = false;
  errorEnvio = '';

  formInstitucion = {
    // Paso 1
    nombreColegio: '',
    nit: '',
    ciudad: '',
    direccion: '',
    telefono: '',
    // Paso 2
    nombreContacto: '',
    correo: '',
    mensaje: ''
  };

  formAcudiente = {
    // Paso 1 — datos del acudiente
    tipoDocumento: '',
    numeroDocumento: '',
    primerNombre: '',
    segundoNombre: '',
    primerApellido: '',
    segundoApellido: '',
    telefono: '',
    telefonoAlternativo: '',
    correo: '',
    // Paso 2 — datos del estudiante
    tipoDocumentoEstudiante: '',
    numeroDocumentoEstudiante: '',
    primerNombreEstudiante: '',
    segundoNombreEstudiante: '',
    primerApellidoEstudiante: '',
    segundoApellidoEstudiante: '',
    fechaNacimiento: '',
    sexo: '',
    ciudad: '',
    departamento: '',
    colegioActual: '',
    gradoAspira: '',
    // Paso 3 — postulación
    colegioInteres: '',
    mensaje: '',
    documentos: [] as File[]
  };

  constructor(private solicitudService: SolicitudService, private router: Router) {}

  get totalPasos(): number {
    if (this.tipoUsuario === 'institucion') return 2;
    if (this.tipoUsuario === 'padre') return 3;
    return 0;
  }

  elegirTipo(tipo: 'institucion' | 'padre'): void {
    this.tipoUsuario = tipo;
    this.pasoActual = 1;
  }

  volverAlSelector(): void {
    this.tipoUsuario = null;
    this.pasoActual = 1;
  }

  siguiente(): void {
    if (!this.validarPasoActual()) return;
    if (this.pasoActual < this.totalPasos) {
      this.pasoActual++;
      window.scrollTo({ top: 0, behavior: 'smooth' });
    }
  }

  /** Paso válido = obligatorios completos Y formatos correctos. */
  validarPasoActual(): boolean {
    return this.requeridosCompletos() && this.problemaFormato() === null;
  }

  private requeridosCompletos(): boolean {
    if (this.tipoUsuario === 'institucion') {
      if (this.pasoActual === 1) {
        return !!(
          this.formInstitucion.nombreColegio &&
          this.formInstitucion.nit &&
          this.formInstitucion.ciudad &&
          this.formInstitucion.telefono
        );
      }
      if (this.pasoActual === 2) {
        return !!(
          this.formInstitucion.nombreContacto &&
          this.formInstitucion.correo &&
          this.formInstitucion.mensaje
        );
      }
    }

    if (this.tipoUsuario === 'padre') {
      if (this.pasoActual === 1) {
        return !!(
          this.formAcudiente.tipoDocumento &&
          this.formAcudiente.numeroDocumento &&
          this.formAcudiente.primerNombre &&
          this.formAcudiente.primerApellido &&
          this.formAcudiente.telefono &&
          this.formAcudiente.correo
        );
      }
      if (this.pasoActual === 2) {
        return !!(
          this.formAcudiente.tipoDocumentoEstudiante &&
          this.formAcudiente.numeroDocumentoEstudiante &&
          this.formAcudiente.primerNombreEstudiante &&
          this.formAcudiente.primerApellidoEstudiante &&
          this.formAcudiente.fechaNacimiento &&
          this.formAcudiente.gradoAspira
        );
      }
      if (this.pasoActual === 3) {
        return !!(
          this.formAcudiente.colegioInteres &&
          this.formAcudiente.mensaje
        );
      }
    }
    return true;
  }

  /** Devuelve el primer problema de FORMATO del paso actual, o null si todo va bien. */
  private problemaFormato(): string | null {
    const fi = this.formInstitucion, fa = this.formAcudiente;
    if (this.tipoUsuario === 'institucion') {
      if (this.pasoActual === 1) {
        return soloDigitos('El NIT solo debe contener números')(fi.nit)
            ?? telefonoCo()(fi.telefono);
      }
      if (this.pasoActual === 2) {
        return correo()(fi.correo);
      }
    }
    if (this.tipoUsuario === 'padre') {
      if (this.pasoActual === 1) {
        return documentoPorTipo()(fa.numeroDocumento, fa)
            ?? telefonoCo()(fa.telefono)
            ?? telefonoCo('Teléfono alternativo inválido (7 dígitos fijo o 10 dígitos celular)')(fa.telefonoAlternativo)
            ?? correo()(fa.correo);
      }
      if (this.pasoActual === 2) {
        return documentoPorTipo('tipoDocumentoEstudiante')(fa.numeroDocumentoEstudiante, fa)
            ?? validarFechaNac()(fa.fechaNacimiento);
      }
    }
    return null;
  }

  intentarSiguiente(): void {
    if (!this.requeridosCompletos()) {
      this.mensajeError = 'Por favor completa todos los campos obligatorios antes de continuar.';
      this.mostrarError = true;
      return;
    }
    const fmt = this.problemaFormato();
    if (fmt) {
      this.mensajeError = fmt;
      this.mostrarError = true;
      return;
    }
    this.mostrarError = false;
    this.mensajeError = '';
    this.siguiente();
  }

  get pasoInvalido(): boolean {
    return !this.validarPasoActual();
  }

  anterior(): void {
    if (this.pasoActual > 1) {
      this.pasoActual--;
      window.scrollTo({ top: 0, behavior: 'smooth' });
    }
  }

  onDocumentosCargados(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files) {
      this.formAcudiente.documentos = Array.from(input.files);
    }
  }

  enviar(): void {
    if (this.tipoUsuario === 'institucion') {
      // Última validación antes de enviar (obligatorios + formato del paso final).
      if (!this.requeridosCompletos()) {
        this.mensajeError = 'Por favor completa todos los campos obligatorios antes de continuar.';
        this.mostrarError = true;
        return;
      }
      const fmt = this.problemaFormato();
      if (fmt) {
        this.mensajeError = fmt;
        this.mostrarError = true;
        return;
      }
      this.mostrarError = false;
      this.mensajeError = '';
      this.enviando = true;
      this.errorEnvio = '';

      this.solicitudService.crear({
        nombreColegio: this.formInstitucion.nombreColegio,
        nit: this.formInstitucion.nit,
        ciudad: this.formInstitucion.ciudad,
        direccion: this.formInstitucion.direccion,
        telefono: this.formInstitucion.telefono,
        nombreContacto: this.formInstitucion.nombreContacto,
        correo: this.formInstitucion.correo,
        mensaje: this.formInstitucion.mensaje
      }).subscribe({
        next: () => {
          this.enviando = false;
          alert('¡Solicitud enviada! Revisaremos tu información y te contactaremos.');
          this.router.navigate(['/']);
        },
        error: (err) => {
          this.enviando = false;
          this.errorEnvio = err?.error?.mensaje || 'No se pudo enviar la solicitud. Intenta de nuevo.';
        }
      });

    } else {
      // Flujo acudiente — se conectará más adelante (postulaciones)
      console.log('Formulario acudiente:', this.formAcudiente);
      alert('El envío de postulaciones estará disponible pronto.');
    }
  }
}
