import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AnioLectivoService } from '../../../core/services/anio-lectivo.service';

@Component({
  selector: 'app-anio-lectivo-form',
  templateUrl: './anio-lectivo-form.component.html',
  styleUrls: ['./anio-lectivo-form.component.scss'],
})
export class AnioLectivoFormComponent implements OnInit {
  modoEdicion = false;
  anioId: string | null = null;
  form: {
    anio: number | null;
    nombre: string;
    descripcion: string;
    fechaInicio: string;
    fechaFin: string;
    estado: string;
  } = {
    anio: null,
    nombre: '',
    descripcion: '',
    fechaInicio: '',
    fechaFin: '',
    estado: 'PLANEACION',
  };
  estados = ['PLANEACION', 'ACTIVO', 'CERRADO', 'CANCELADO'];
  cargando = false;
  guardando = false;
  error = '';

  constructor(
    private anioService: AnioLectivoService,
    private route: ActivatedRoute,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.anioId = this.route.snapshot.paramMap.get('id');
    this.modoEdicion = !!this.anioId;
    if (this.modoEdicion) this.cargar();
  }
  cargar(): void {
    this.cargando = true;
    this.anioService.obtener(this.anioId!).subscribe({
      next: (a) => {
        this.form = {
          anio: a.anio ?? null,
          nombre: a.nombre ?? '',
          descripcion: a.descripcion ?? '',
          fechaInicio: a.fechaInicio ?? '',
          fechaFin: a.fechaFin ?? '',
          estado: a.estado ?? 'PLANEACION',
        };
        this.cargando = false;
      },
      error: () => {
        this.error = 'No se pudo cargar el año lectivo.';
        this.cargando = false;
      },
    });
  }
  guardar(): void {
    this.error = '';
    if (
      !this.form.anio ||
      !this.form.nombre.trim() ||
      !this.form.fechaInicio ||
      !this.form.fechaFin
    ) {
      this.error = 'Año, nombre y fechas son obligatorios.';
      return;
    }
    this.guardando = true;
    const p = this.modoEdicion
      ? this.anioService.editar(this.anioId!, this.form)
      : this.anioService.crear(this.form);
    p.subscribe({
      next: () => this.router.navigate(['/dashboard/anios-lectivos']),
      error: (err) => {
        this.guardando = false;
        this.error = err?.error?.mensaje || 'No se pudo guardar.';
      },
    });
  }
  cancelar(): void {
    this.router.navigate(['/dashboard/anios-lectivos']);
  }
}
