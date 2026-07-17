import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AsignaturaService } from '../../../../core/services/asignatura.service';
import { AreaService, Area } from '../../../../core/services/area.service';

@Component({
  selector: 'app-asignatura-form',
  templateUrl: './asignatura-form.component.html',
  styleUrls: ['./asignatura-form.component.scss'],
})
export class AsignaturaFormComponent implements OnInit {
  modoEdicion = false;
  asignaturaId: string | null = null;
  form = {
    areaId: '',
    codigo: '',
    nombre: '',
    descripcion: '',
    intensidadHorariaSemanal: null as number | null,
    requiereCalificacion: true,
    requiereRecuperacion: true,
    esComportamiento: false,
  };
  areas: Area[] = [];
  cargando = false;
  guardando = false;
  error = '';

  constructor(
    private asignaturaService: AsignaturaService,
    private areaService: AreaService,
    private route: ActivatedRoute,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.asignaturaId = this.route.snapshot.paramMap.get('id');
    this.modoEdicion = !!this.asignaturaId;
    this.areaService
      .listar()
      .subscribe({
        next: (d) => (this.areas = d.filter((a) => a.activa)),
        error: () => {},
      });
    if (this.modoEdicion) this.cargar();
  }

  cargar(): void {
    this.cargando = true;
    this.asignaturaService.obtener(this.asignaturaId!).subscribe({
      next: (a) => {
        this.form = {
          areaId: a.areaId ?? '',
          codigo: a.codigo ?? '',
          nombre: a.nombre ?? '',
          descripcion: a.descripcion ?? '',
          intensidadHorariaSemanal: a.intensidadHorariaSemanal ?? null,
          requiereCalificacion: a.requiereCalificacion ?? true,
          requiereRecuperacion: a.requiereRecuperacion ?? true,
          esComportamiento: a.esComportamiento ?? false,
        };
        this.cargando = false;
      },
      error: () => {
        this.error = 'No se pudo cargar la asignatura.';
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
    const body = {
      areaId: this.form.areaId || null,
      codigo: this.form.codigo,
      nombre: this.form.nombre,
      descripcion: this.form.descripcion,
      intensidadHorariaSemanal: this.form.intensidadHorariaSemanal,
      requiereCalificacion: this.form.requiereCalificacion,
      requiereRecuperacion: this.form.requiereRecuperacion,
      esComportamiento: this.form.esComportamiento,
    };
    const p = this.modoEdicion
      ? this.asignaturaService.editar(this.asignaturaId!, body)
      : this.asignaturaService.crear(body);
    p.subscribe({
      next: () => this.router.navigate(['/dashboard/asignaturas']),
      error: (err) => {
        this.guardando = false;
        this.error = err?.error?.mensaje || 'No se pudo guardar.';
      },
    });
  }

  cancelar(): void {
    this.router.navigate(['/dashboard/asignaturas']);
  }
}
