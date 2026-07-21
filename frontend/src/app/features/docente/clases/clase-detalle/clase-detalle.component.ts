import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import {
  DocenteService,
  EstudianteDeClase,
} from '../../../../core/services/docente.service';

@Component({
  selector: 'app-clase-detalle',
  templateUrl: './clase-detalle.component.html',
  styleUrls: ['./clase-detalle.component.scss'],
})
export class ClaseDetalleComponent implements OnInit {
  cargaId!: string;
  estudiantes: EstudianteDeClase[] = [];
  cargando = false;
  error = '';

  constructor(
    private docenteService: DocenteService,
    private route: ActivatedRoute,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.cargaId = this.route.snapshot.paramMap.get('cargaId')!;
    this.cargar();
  }

  cargar(): void {
    this.cargando = true;
    this.error = '';
    this.docenteService.estudiantesDeClase(this.cargaId).subscribe({
      next: (d) => {
        this.estudiantes = d;
        this.cargando = false;
      },
      error: (err) => {
        this.error =
          err?.status === 403
            ? 'Esta clase no te pertenece.'
            : 'No se pudo cargar la lista de estudiantes.';
        this.cargando = false;
      },
    });
  }

  volver(): void {
    this.router.navigate(['/dashboard/mis-clases']);
  }
}
