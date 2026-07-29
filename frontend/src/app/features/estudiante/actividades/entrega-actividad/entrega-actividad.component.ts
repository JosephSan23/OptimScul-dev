import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ActividadEstudianteService, MiEntrega } from '../../../../core/services/rol/estudiante/actividad-estudiante.service';

@Component({
  selector: 'app-entrega-actividad',
  templateUrl: './entrega-actividad.component.html',
  styleUrls: ['./entrega-actividad.component.scss']
})
export class EntregaActividadComponent implements OnInit {

  actividadId = '';
  entrega: MiEntrega | null = null;
  archivos: File[] = [];
  comentario = '';
  cargando = false; enviando = false; error = ''; ok = '';
  readonly MAX_MB = 25;
  readonly TIPOS_OK = ['pdf','png','jpg','jpeg','webp','doc','docx','xls','xlsx','ppt','pptx','txt','zip'];


  constructor(
    private actividadService: ActividadEstudianteService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.actividadId = this.route.snapshot.paramMap.get('actividadId') ?? '';
    this.cargar();
  }

  cargar(): void {
    this.cargando = true; this.error = '';
    this.actividadService.miEntrega(this.actividadId).subscribe({
      next: (e) => { this.entrega = e; this.comentario = e.comentarioEstudiante ?? ''; this.cargando = false; },
      error: () => { this.error = 'No se pudo cargar la entrega.'; this.cargando = false; }
    });
  }

  onArchivos(event: Event): void {
    const input = event.target as HTMLInputElement;
    this.error = '';
    if (!input.files) return;
    const seleccionados = Array.from(input.files);
    for (const f of seleccionados) {
      const ext = (f.name.split('.').pop() ?? '').toLowerCase();
      if (!this.TIPOS_OK.includes(ext)) {
        this.error = `Tipo de archivo no permitido: ${f.name}`;
        input.value = ''; this.archivos = []; return;
      }
      if (f.size > this.MAX_MB * 1024 * 1024) {
        this.error = `"${f.name}" supera ${this.MAX_MB} MB.`;
        input.value = ''; this.archivos = []; return;
      }
    }
    this.archivos = seleccionados;
  }

  quitar(i: number): void { this.archivos.splice(i, 1); }

  get calificada(): boolean { return this.entrega?.estado === 'CALIFICADA'; }

  enviar(): void {
    if (this.calificada) return;
    if (this.archivos.length === 0 && !this.comentario.trim()) {
      this.error = 'Adjunta al menos un archivo o escribe un comentario.'; return;
    }
    this.enviando = true; this.error = ''; this.ok = '';
    this.actividadService.entregar(this.actividadId, this.comentario, this.archivos).subscribe({
      next: (e) => { this.entrega = e; this.archivos = []; this.enviando = false; this.ok = '¡Entrega enviada!'; },
      error: (err) => { this.error = err?.error?.mensaje ?? 'No se pudo enviar la entrega.'; this.enviando = false; }
    });
  }

  formatoTamano(bytes: number): string {
    if (bytes < 1024) return bytes + ' B';
    if (bytes < 1024 * 1024) return (bytes / 1024).toFixed(1) + ' KB';
    return (bytes / (1024 * 1024)).toFixed(1) + ' MB';
  }

  volver(): void { this.router.navigate(['/dashboard/estudiante/actividades']); }

  eliminarArchivo(documentoId: string): void {
    if (this.calificada) return;
    if (!confirm('¿Eliminar este archivo de tu entrega?')) return;
    this.actividadService.eliminarArchivo(this.actividadId, documentoId).subscribe({
      next: (e) => { this.entrega = e; this.ok = 'Archivo eliminado.'; },
      error: (err) => { this.error = err?.error?.mensaje ?? 'No se pudo eliminar.'; }
    });
  }

}
