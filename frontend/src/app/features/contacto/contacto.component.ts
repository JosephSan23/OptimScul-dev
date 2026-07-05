import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { SolicitudService } from '../../core/services/solicitud.service';

@Component({
  selector: 'app-contacto',
  templateUrl: './contacto.component.html',
  styleUrl: './contacto.component.scss'
})
export class ContactoComponent {
  tipoUsuario: 'institucion' | 'padre' | null = null;

  pasoActual = 1;
  mostrarError = false;

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

  validarPasoActual(): boolean {
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

  intentarSiguiente(): void {
    if (!this.validarPasoActual()) {
      this.mostrarError = true;
      return;
    }
    this.mostrarError = false;
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
