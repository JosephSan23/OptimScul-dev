import { Component, OnInit } from '@angular/core';
import { InstitucionService, Institucion } from '../../../core/services/institucion.service';

@Component({
  selector: 'app-instituciones-lista',
  templateUrl: './instituciones-lista.component.html',
  styleUrls: ['./instituciones-lista.component.scss']
})
export class InstitucionesListaComponent implements OnInit {

  instituciones: Institucion[] = [];
  cargando = false;
  error = '';

  expandidaId: string | null = null;   // card en modo edición
  creando = false;                      // card "nueva" abierta
  procesandoId: string | null = null;   // acción de estado en curso

  constructor(private institucionService: InstitucionService) {}

  ngOnInit(): void {
    this.cargarInstituciones();
  }

  cargarInstituciones(): void {
    this.cargando = true;
    this.error = '';
    this.institucionService.listar().subscribe({
      next: (data) => { this.instituciones = data; this.cargando = false; },
      error: () => { this.error = 'No se pudieron cargar las instituciones.'; this.cargando = false; }
    });
  }

  abrirEditar(inst: Institucion): void {
    this.creando = false;
    this.expandidaId = inst.id;
  }

  abrirCrear(): void {
    this.expandidaId = null;
    this.creando = true;
  }

  cerrar(): void {
    this.expandidaId = null;
    this.creando = false;
  }

  // Cuando el formulario hijo avisa "guardé"
  onGuardado(): void {
    this.cerrar();
    this.cargarInstituciones();
  }

  suspender(inst: Institucion): void {
    this.procesandoId = inst.id;
    this.institucionService.suspender(inst.id).subscribe({
      next: () => { this.procesandoId = null; this.cargarInstituciones(); },
      error: () => { this.procesandoId = null; this.error = 'No se pudo suspender.'; }
    });
  }

  activar(inst: Institucion): void {
    this.procesandoId = inst.id;
    this.institucionService.activar(inst.id).subscribe({
      next: () => { this.procesandoId = null; this.cargarInstituciones(); },
      error: () => { this.procesandoId = null; this.error = 'No se pudo activar.'; }
    });
  }

  reactivar(inst: Institucion): void {
    this.procesandoId = inst.id;
    this.institucionService.reactivar(inst.id).subscribe({
      next: () => { this.procesandoId = null; this.cargarInstituciones(); },
      error: () => { this.procesandoId = null; this.error = 'No se pudo reactivar.'; }
    });
  }

  iconoPorTipo(tipo: string): string {
    switch (tipo) {
      case 'COLEGIO':   return 'ti ti-school';
      case 'JARDIN':    return 'ti ti-mood-kid';
      case 'INSTITUTO': return 'ti ti-building';
      case 'ACADEMIA':  return 'ti ti-books';
      default:          return 'ti ti-building-community';
    }
  }
  claseTipo(tipo: string): string {
    switch (tipo) {
      case 'COLEGIO':   return 'tipo-colegio';
      case 'JARDIN':    return 'tipo-jardin';
      case 'INSTITUTO': return 'tipo-instituto';
      case 'ACADEMIA':  return 'tipo-academia';
      default:          return 'tipo-otra';
    }
  }
  etiquetaTipo(tipo: string): string {
    switch (tipo) {
      case 'COLEGIO':   return 'Colegio';
      case 'JARDIN':    return 'Jardín';
      case 'INSTITUTO': return 'Instituto';
      case 'ACADEMIA':  return 'Academia';
      default:          return 'Otra';
    }
  }
  claseEstado(estado: string): string {
    switch (estado) {
      case 'ACTIVA':     return 'badge-activa';
      case 'INACTIVA':   return 'badge-inactiva';
      case 'SUSPENDIDA': return 'badge-suspendida';
      case 'PRUEBA':     return 'badge-prueba';
      default:           return 'badge-inactiva';
    }
  }
  etiquetaEstado(estado: string): string {
    switch (estado) {
      case 'ACTIVA':     return 'Activa';
      case 'INACTIVA':   return 'Inactiva';
      case 'SUSPENDIDA': return 'Suspendida';
      case 'PRUEBA':     return 'Prueba';
      default:           return estado;
    }
  }
}
