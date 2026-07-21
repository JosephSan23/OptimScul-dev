import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {
  DocenteService,
  MiClase,
  MiFranja,
} from '../../../../core/services/docente.service';
import {
  AnioLectivoService,
  AnioLectivo,
} from '../../../../core/services/anio-lectivo.service';

@Component({
  selector: 'app-mis-clases-list',
  templateUrl: './mis-clases-list.component.html',
  styleUrl: './mis-clases-list.component.scss',
})
export class MisClasesListComponent implements OnInit {
  readonly DIAS = [
    'LUNES',
    'MARTES',
    'MIERCOLES',
    'JUEVES',
    'VIERNES',
    'SABADO',
    'DOMINGO',
  ];
  readonly NOMBRE_DIA: Record<string, string> = {
    LUNES: 'Lunes',
    MARTES: 'Martes',
    MIERCOLES: 'Miércoles',
    JUEVES: 'Jueves',
    VIERNES: 'Viernes',
    SABADO: 'Sábado',
    DOMINGO: 'Domingo',
  };

  anios: AnioLectivo[] = [];
  anioSeleccionado = '';
  clases: MiClase[] = [];
  horario: MiFranja[] = [];
  vista: 'clases' | 'horario' = 'clases';
  cargando = false;
  error = '';

  constructor(
    private docenteService: DocenteService,
    private anioService: AnioLectivoService,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.anioService.listar().subscribe({
      next: (d) => {
        this.anios = d;
        const actual = d.find((a) => a.esActual) ?? d[0];
        if (actual) {
          this.anioSeleccionado = actual.id;
          this.cargar();
        }
      },
      error: () => {
        this.error = 'No se pudieron cargar los años lectivos.';
      },
    });
  }

  cambioAnio(): void {
    this.cargar();
  }

  cargar(): void {
    if (!this.anioSeleccionado) return;
    this.cargando = true;
    this.error = '';
    this.docenteService.misClases(this.anioSeleccionado).subscribe({
      next: (d) => {
        this.clases = d;
        this.cargando = false;
      },
      error: () => {
        this.error = 'No se pudieron cargar tus clases.';
        this.cargando = false;
      },
    });
    this.docenteService.miHorario(this.anioSeleccionado).subscribe({
      next: (d) => (this.horario = d),
      error: () => {},
    });
  }

  abrirClase(c: MiClase): void {
    this.router.navigate(['/dashboard/mis-clases', c.cargaId]);
  }

  franjasDe(dia: string): MiFranja[] {
    return this.horario.filter((h) => h.diaSemana === dia);
  }
  get diasConClase(): string[] {
    return this.DIAS.filter((d) => this.franjasDe(d).length > 0);
  }
}
