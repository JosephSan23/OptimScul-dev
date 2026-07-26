import { Component, OnInit } from '@angular/core';
import { AcudienteService, Hijo } from '../../../../core/services/rol/acudiente.service';
import { FranjaHorario } from '../../../../core/services/rol/estudiante.service';
import { AnioLectivoService, AnioLectivo } from '../../../../core/services/anio-lectivo.service';

@Component({
  selector: 'app-horario-hijo',
  templateUrl: './horario-hijo.component.html',
  styleUrls: ['./horario-hijo.component.scss']
})
export class HorarioHijoComponent implements OnInit {
  readonly DIAS = ['LUNES', 'MARTES', 'MIERCOLES', 'JUEVES', 'VIERNES', 'SABADO', 'DOMINGO'];
  readonly NOMBRE_DIA: Record<string, string> = {
    LUNES: 'Lunes', MARTES: 'Martes', MIERCOLES: 'Miércoles', JUEVES: 'Jueves',
    VIERNES: 'Viernes', SABADO: 'Sábado', DOMINGO: 'Domingo'
  };

  hijos: Hijo[] = [];
  hijoSeleccionado = '';
  anios: AnioLectivo[] = [];
  anioSeleccionado = '';
  franjas: FranjaHorario[] = [];
  matriculado = true;
  cargando = false; error = '';

  constructor(private acudienteService: AcudienteService, private anioService: AnioLectivoService) {}

  ngOnInit(): void {
    this.acudienteService.hijos().subscribe({
      next: (h) => {
        this.hijos = h;
        if (h.length) this.hijoSeleccionado = h[0].estudianteId;
        this.anioService.listar().subscribe({
          next: (d) => {
            this.anios = d;
            const actual = d.find(a => a.esActual) ?? d[0];
            if (actual) { this.anioSeleccionado = actual.id; this.cargar(); }
          },
          error: () => { this.error = 'No se pudieron cargar los años lectivos.'; }
        });
      },
      error: () => { this.error = 'No se pudieron cargar tus hijos.'; }
    });
  }

  cambioHijo(): void { this.cargar(); }
  cambioAnio(): void { this.cargar(); }

  cargar(): void {
    if (!this.hijoSeleccionado || !this.anioSeleccionado) return;
    this.cargando = true; this.error = '';
    this.acudienteService.horarioHijo(this.hijoSeleccionado, this.anioSeleccionado).subscribe({
      next: (v) => { this.matriculado = v.matriculado; this.franjas = v.franjas; this.cargando = false; },
      error: () => { this.error = 'No se pudo cargar el horario.'; this.cargando = false; }
    });
  }

  franjasDe(dia: string): FranjaHorario[] { return this.franjas.filter(f => f.diaSemana === dia); }
  get diasConClase(): string[] { return this.DIAS.filter(d => this.franjasDe(d).length > 0); }
}
