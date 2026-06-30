import { Component } from '@angular/core';

@Component({
  selector: 'app-contacto',
  templateUrl: './contacto.component.html',
  styleUrl: './contacto.component.scss'
})
export class ContactoComponent {
  tipoUsuario: 'institucion' | 'padre' | null = null;

  // Controla en qué paso del stepper está el usuario
  pasoActual = 1;
  mostrarError = false;

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

  // Datos del formulario de acudiente
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

  // Total de pasos según el tipo de usuario
  get totalPasos(): number {
    if (this.tipoUsuario === 'institucion') return 2;
    if (this.tipoUsuario === 'padre') return 3;
    return 0;
  }

  // Elegir tipo de usuario — resetea el paso al 1
  elegirTipo(tipo: 'institucion' | 'padre'): void {
    this.tipoUsuario = tipo;
    this.pasoActual = 1;
  }

  // Volver al selector de tipo
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

  // Saber si el paso actual tiene errores para mostrar mensaje
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
    // Por ahora solo mostramos en consola
    // Aquí irá la llamada al backend cuando tengamos el login
    if (this.tipoUsuario === 'institucion') {
      console.log('Formulario institución:', this.formInstitucion);
    } else {
      console.log('Formulario acudiente:', this.formAcudiente);
    }
    alert('¡Mensaje enviado! Te contactaremos pronto.');
  }
}
