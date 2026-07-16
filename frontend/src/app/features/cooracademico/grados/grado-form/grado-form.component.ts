import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { GradoService } from '../../../../core/services/grado.service';

@Component({
  selector: 'app-grado-form',
  templateUrl: './grado-form.component.html',
  styleUrls: ['./grado-form.component.scss'],
})
export class GradoFormComponent implements OnInit {
  modoEdicion = false;
  gradoId: string | null = null;
  form = { codigo: '', nombre: '', nivel: '', orden: null as number | null };
  niveles = [
    'PREESCOLAR',
    'PRIMARIA',
    'SECUNDARIA',
    'MEDIA',
    'TECNICA',
    'OTRO',
  ];
  cargando = false;
  guardando = false;
  error = '';

  constructor(
    private gradoService: GradoService,
    private route: ActivatedRoute,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.gradoId = this.route.snapshot.paramMap.get('id');
    this.modoEdicion = !!this.gradoId;
    if (this.modoEdicion) this.cargar();
  }

  cargar(): void {
    this.cargando = true;
    this.gradoService.obtener(this.gradoId!).subscribe({
      next: (g) => {
        this.form = {
          codigo: g.codigo ?? '',
          nombre: g.nombre ?? '',
          nivel: g.nivel ?? '',
          orden: g.orden ?? null,
        };
        this.cargando = false;
      },
      error: () => {
        this.error = 'No se pudo cargar el grado.';
        this.cargando = false;
      },
    });
  }

  guardar(): void {
    this.error = '';
    if (
      !this.form.codigo.trim() ||
      !this.form.nombre.trim() ||
      !this.form.nivel ||
      this.form.orden == null
    ) {
      this.error = 'Código, nombre, nivel y orden son obligatorios.';
      return;
    }
    this.guardando = true;
    const body = {
      codigo: this.form.codigo,
      nombre: this.form.nombre,
      nivel: this.form.nivel,
      orden: this.form.orden,
    };
    const p = this.modoEdicion
      ? this.gradoService.editar(this.gradoId!, body)
      : this.gradoService.crear(body);
    p.subscribe({
      next: () => this.router.navigate(['/dashboard/grados']),
      error: (err) => {
        this.guardando = false;
        this.error = err?.error?.mensaje || 'No se pudo guardar.';
      },
    });
  }

  cancelar(): void {
    this.router.navigate(['/dashboard/grados']);
  }
}
