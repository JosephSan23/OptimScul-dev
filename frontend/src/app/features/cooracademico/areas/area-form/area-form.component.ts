import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AreaService } from '../../../../core/services/area.service';

@Component({
  selector: 'app-area-form',
  templateUrl: './area-form.component.html',
  styleUrls: ['./area-form.component.scss'],
})
export class AreaFormComponent implements OnInit {
  modoEdicion = false;
  areaId: string | null = null;
  form = { codigo: '', nombre: '', descripcion: '' };
  cargando = false;
  guardando = false;
  error = '';

  constructor(
    private areaService: AreaService,
    private route: ActivatedRoute,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.areaId = this.route.snapshot.paramMap.get('id');
    this.modoEdicion = !!this.areaId;
    if (this.modoEdicion) this.cargar();
  }

  cargar(): void {
    this.cargando = true;
    this.areaService.obtener(this.areaId!).subscribe({
      next: (a) => {
        this.form = {
          codigo: a.codigo ?? '',
          nombre: a.nombre ?? '',
          descripcion: a.descripcion ?? '',
        };
        this.cargando = false;
      },
      error: () => {
        this.error = 'No se pudo cargar el área.';
        this.cargando = false;
      },
    });
  }

  guardar(): void {
    this.error = '';
    if (!this.form.codigo.trim() || !this.form.nombre.trim()) {
      this.error = 'Código y nombre son obligatorios.';
      return;
    }
    this.guardando = true;
    const p = this.modoEdicion
      ? this.areaService.editar(this.areaId!, this.form)
      : this.areaService.crear(this.form);
    p.subscribe({
      next: () => this.router.navigate(['/dashboard/areas']),
      error: (err) => {
        this.guardando = false;
        this.error = err?.error?.mensaje || 'No se pudo guardar.';
      },
    });
  }

  cancelar(): void {
    this.router.navigate(['/dashboard/areas']);
  }
}
