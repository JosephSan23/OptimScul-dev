import { Component, OnInit } from '@angular/core';
import {
  EstudianteService,
  FranjaHorario,
} from '../../../../core/services/rol/estudiante.service';
import {
  AnioLectivoService,
  AnioLectivo,
} from '../../../../core/services/anio-lectivo.service';

@Component({
  selector: 'app-mi-horario',
  templateUrl: './mi-horario.component.html',
  styleUrls: ['./mi-horario.component.scss'],
})
export class MiHorarioComponent implements OnInit {
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
  franjas: FranjaHorario[] = [];
  matriculado = true;
  cargando = false;
  error = '';

  constructor(
    private estudianteService: EstudianteService,
    private anioService: AnioLectivoService,
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
    this.estudianteService.miHorario(this.anioSeleccionado).subscribe({
      next: (v) => {
        this.matriculado = v.matriculado;
        this.franjas = v.franjas;
        this.cargando = false;
      },
      error: () => {
        this.error = 'No se pudo cargar tu horario.';
        this.cargando = false;
      },
    });
  }

  franjasDe(dia: string): FranjaHorario[] {
    return this.franjas.filter((f) => f.diaSemana === dia);
  }
  get diasConClase(): string[] {
    return this.DIAS.filter((d) => this.franjasDe(d).length > 0);
  }
}
