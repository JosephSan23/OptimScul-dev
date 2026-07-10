import { Component } from '@angular/core';

// Componente principalmente visual — los datos viven aquí
// para mantener el HTML limpio.
@Component({
  selector: 'app-primeros-pasos',
  templateUrl: './primeros-pasos.component.html',
  styleUrls: ['./primeros-pasos.component.scss']
})
export class PrimerosPasosComponent {

  perfilActivo: 'institucion' | 'padre' = 'institucion';

  elegirPerfil(perfil: 'institucion' | 'padre'): void {
    this.perfilActivo = perfil;
  }

  // Devuelve los pasos del perfil seleccionado
  get pasosActivos() {
    return this.perfilActivo === 'institucion'
      ? this.pasosInstituciones
      : this.pasosPadres;
  }

  // Pasos para instituciones educativas
  pasosInstituciones = [
    {
      numero: '01',
      icono: 'fas fa-file-signature',
      titulo: 'Solicitud de Vinculación',
      descripcion: 'Completa el formulario de contacto institucional. Nuestro equipo revisará que tu colegio cumpla con los estándares técnicos.'
    },
    {
      numero: '02',
      icono: 'fas fa-comments',
      titulo: 'Sesión de Consultoría',
      descripcion: 'Nos contactaremos contigo para definir el plan de implementación que mejor se adapte a ti.'
    },
    {
      numero: '03',
      icono: 'fas fa-user-shield',
      titulo: 'Activación de Licencia',
      descripcion: 'Una vez aprobado, habilitamos tu entorno seguro y capacitamos a tu personal administrativo para el lanzamiento.'
    }
  ];

  // Pasos para padres y acudientes
  pasosPadres = [
    {
      numero: '01',
      icono: 'fas fa-search-location',
      titulo: 'Explora el Directorio',
      descripcion: 'Filtra colegios por ubicación, costos y metodología pedagógica en nuestra base de datos verificada.'
    },
    {
      numero: '02',
      icono: 'fas fa-paper-plane',
      titulo: 'Envío de Formulario',
      descripcion: 'Llena tus datos y los del estudiante. Esta información se enviará de forma encriptada directamente al departamento de admisiones del colegio.'
    },
    {
      numero: '03',
      icono: 'fas fa-calendar-check',
      titulo: 'Seguimiento Directo',
      descripcion: 'El colegio recibirá tu postulación y se pondrá en contacto contigo para agendar entrevistas o pruebas.'
    }
  ];
}
