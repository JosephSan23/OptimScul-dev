import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { EscalaService } from '../../../../core/services/escala.service';
import { ConfigAcademicaService } from '../../../../core/services/config-academica.service';


@Component({
  selector: 'app-escala-form',
  templateUrl: './escala-form.component.html',
  styleUrls: ['./escala-form.component.scss'],
})
export class EscalaFormComponent implements OnInit {
  modoEdicion = false;
  escalaId: string | null = null;
  form = {
    nombre: '',
    abreviatura: '',
    notaMinima: null as number | null,
    notaMaxima: null as number | null,
    aprueba: false,
    orden: null as number | null,
  };
  cargando = false;
  guardando = false;
  error = '';
  rangoMin = 0;
  rangoMax = 5;
  rangoCargado = false;

  constructor(
    private escalaService: EscalaService,
    private route: ActivatedRoute,
    private router: Router,
    private configService: ConfigAcademicaService
  ) {}

  ngOnInit(): void {
    this.escalaId = this.route.snapshot.paramMap.get('id');
    this.modoEdicion = !!this.escalaId;
    if (this.modoEdicion) this.cargar();
    this.configService.obtener().subscribe({
      next: (c) => { this.rangoMin = c.notaMinima; this.rangoMax = c.notaMaxima; this.rangoCargado = true; },
      error: () => {}
    });
  }

  cargar(): void {
    this.cargando = true;
    this.escalaService.obtener(this.escalaId!).subscribe({
      next: (e) => {
        this.form = {
          nombre: e.nombre ?? '',
          abreviatura: e.abreviatura ?? '',
          notaMinima: e.notaMinima,
          notaMaxima: e.notaMaxima,
          aprueba: e.aprueba,
          orden: e.orden,
        };
        this.cargando = false;
      },
      error: () => {
        this.error = 'No se pudo cargar la banda.';
        this.cargando = false;
      },
    });
  }

  guardar(): void {
    this.error = '';
    if (
      !this.form.nombre.trim() ||
      this.form.notaMinima == null ||
      this.form.notaMaxima == null ||
      this.form.orden == null
    ) {
      this.error = 'Nombre, rango y orden son obligatorios.';
      return;
    }
    if (this.form.notaMinima >= this.form.notaMaxima) {
      this.error = 'La nota mínima no puede ser mayor que la máxima.';
      return;
    }
    if (this.rangoCargado && (this.form.notaMinima! < this.rangoMin || this.form.notaMaxima! > this.rangoMax)) {
      this.error = `La banda debe estar entre ${this.rangoMin} y ${this.rangoMax} (rango de la configuración).`;
      return;
    }
    this.guardando = true;
    const body = {
      nombre: this.form.nombre,
      abreviatura: this.form.abreviatura,
      notaMinima: this.form.notaMinima,
      notaMaxima: this.form.notaMaxima,
      aprueba: this.form.aprueba,
      orden: this.form.orden,
    };
    const p = this.modoEdicion
      ? this.escalaService.editar(this.escalaId!, body)
      : this.escalaService.crear(body);
    p.subscribe({
      next: () => this.router.navigate(['/dashboard/escalas']),
      error: (err) => {
        this.guardando = false;
        this.error = err?.error?.mensaje || 'No se pudo guardar.';
      },
    });
  }

  cancelar(): void {
    this.router.navigate(['/dashboard/escalas']);
  }
}
