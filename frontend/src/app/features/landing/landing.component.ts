import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-landing',
  templateUrl: './landing.component.html',
  styleUrls: ['./landing.component.scss']
})
export class LandingComponent implements OnInit {

  // Lista de problemas que se muestran en la sección "El Desafío"
  // En Angular es buena práctica definir los datos en el .ts y renderizarlos en el .html con *ngFor
  problemas = [
    {
      numero: '01',
      titulo: 'Fuga de tiempo administrativo',
      descripcion: 'El personal dedica más tiempo a llenar formularios que a mejorar la calidad educativa de la institución.'
    },
    {
      numero: '02',
      titulo: 'Información fragmentada',
      descripcion: 'Datos de estudiantes en Excel, notas en papel o pagos en cuadernos. Nada se conecta cuando más lo necesitas.'
    },
    {
      numero: '03',
      titulo: 'Brecha de comunicación',
      descripcion: 'La falta de un canal oficial genera desconfianza en los padres y sobrecarga de mensajes en canales personales.'
    }
  ];

  // Tarjetas de beneficios — sección "¿Por qué OptimScul?"
  beneficios = [
    {
      imagen: 'assets/simplicidad.png',
      titulo: 'Gestión Simplificada',
      descripcion: 'Centraliza notas, asistencias y reportes en una plataforma intuitiva que cualquiera puede usar.'
    },
    {
      imagen: 'assets/control.png',
      titulo: 'Control en Tiempo Real',
      descripcion: 'Visualiza el progreso académico y administrativo al instante con nuestro tablero de analíticas.'
    },
    {
      imagen: 'assets/comunicacion.png',
      titulo: 'Conexión Total',
      descripcion: 'Mejora la relación colegio-hogar con notificaciones automáticas y mensajería segura.'
    }
  ];

  // Roles — sección "Soluciones para toda la comunidad"
  roles = [
    {
      titulo: 'Para Docentes',
      descripcion: 'Registra calificaciones rápidamente, controla la asistencia en segundos y mantén comunicación directa.',
      imagen: 'assets/roles/docentes.png',
      reverse: true
    },
    {
      titulo: 'Para Estudiantes',
      descripcion: 'Consulta notas, horarios, tareas y recibe notificaciones importantes desde cualquier dispositivo.',
      imagen: 'assets/roles/estudiantes.png',
      reverse: false
    },
    {
      titulo: 'Para Padres y Acudientes',
      descripcion: 'Haz seguimiento en el rendimiento académico, recibe alertas y accede a reportes y certificados.',
      imagen: 'assets/roles/padres.png',
      reverse: true
    },
    {
      titulo: 'Para la Administración',
      descripcion: 'Gestiona estudiantes, automatiza procesos internos y obtén reportes institucionales en tiempo real.',
      imagen: 'assets/roles/admin.png',
      reverse: false
    }
  ];

  // Capacidades — sección "Infraestructura"
  capacidades = [
    { icono: 'fas fa-users-cog', titulo: 'Control de Usuarios', descripcion: 'Gestión centralizada de roles para estudiantes, docentes y padres de familia.' },
    { icono: 'fas fa-cloud', titulo: 'Acceso 24/7', descripcion: 'Plataforma siempre disponible desde cualquier navegador o dispositivo móvil.' },
    { icono: 'fas fa-file-export', titulo: 'Exportación Rápida', descripcion: 'Genera reportes PDF y hojas de cálculo con un solo clic para procesos legales.' },
    { icono: 'fas fa-paper-plane', titulo: 'Mensajería Directa', descripcion: 'Canal de comunicación seguro entre la administración y la comunidad educativa.' },
    { icono: 'fas fa-user-shield', titulo: 'Protección de Datos', descripcion: 'Cumplimiento con normativas de privacidad y backups automáticos diarios.' },
    { icono: 'fas fa-headset', titulo: 'Asistencia Técnica', descripcion: 'Acompañamiento constante y actualizaciones periódicas del sistema.' }
  ];

  // IntersectionObserver — detecta cuándo un elemento entra en pantalla
  // para activar las animaciones de scroll
  private observer!: IntersectionObserver;

  ngOnInit(): void {
  // Verificamos que estamos en el navegador antes de usar
  // APIs que solo existen ahí como IntersectionObserver y document
  if (typeof window === 'undefined') return;

  this.observer = new IntersectionObserver((entries) => {
    entries.forEach(entry => {
      if (entry.isIntersecting) {
        entry.target.classList.add('visible');
        this.observer.unobserve(entry.target);
      }
    });
  }, { threshold: 0.15 });

  setTimeout(() => {
      document.querySelectorAll('.revelar, .revelar-izq').forEach(el => {
        this.observer.observe(el);
      });
    }, 100);
  }
}
