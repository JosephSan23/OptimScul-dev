import { Component, OnInit } from '@angular/core';
import {
  ConfigAcademicaService,
  ConfigAcademica,
} from '../../../../core/services/config-academica.service';

@Component({
  selector: 'app-config-academica',
  templateUrl: './config-academica.component.html',
  styleUrls: ['./config-academica.component.scss'],
})
export class ConfigAcademicaComponent implements OnInit {
  form: ConfigAcademica = {
    usaPeriodos: true,
    numeroPeriodos: 4,
    notaMinimaAprobacion: 3,
    notaMinima: 0,
    notaMaxima: 5,
    decimalesNota: 2,
    usaRecuperacion: true,
    asistenciaPorClase: true,
    manejaComportamiento: true,
    manejaPuestos: false,
    porcentajeInasistenciaReprobacion: null,
  };
  cargando = false;
  guardando = false;
  error = '';
  exito = '';

  constructor(private configService: ConfigAcademicaService) {}

  ngOnInit(): void {
    this.cargando = true;
    this.configService.obtener().subscribe({
      next: (c) => {
        this.form = { ...this.form, ...c };
        this.cargando = false;
      },
      error: () => {
        this.error = 'No se pudo cargar la configuración.';
        this.cargando = false;
      },
    });
  }

  guardar(): void {
    this.error = '';
    this.exito = '';
    if (this.form.notaMinima >= this.form.notaMaxima) {
      this.error = 'La nota mínima debe ser menor que la máxima.';
      return;
    }
    if (
      this.form.notaMinimaAprobacion < this.form.notaMinima ||
      this.form.notaMinimaAprobacion > this.form.notaMaxima
    ) {
      this.error =
        'La nota de aprobación debe estar entre la mínima y la máxima.';
      return;
    }
    if (this.form.usaPeriodos && this.form.numeroPeriodos < 1) {
      this.error = 'Debe haber al menos un periodo.';
      return;
    }
    this.guardando = true;
    this.configService.guardar(this.form).subscribe({
      next: (c) => {
        this.form = { ...this.form, ...c };
        this.guardando = false;
        this.exito = 'Configuración guardada.';
      },
      error: (err) => {
        this.guardando = false;
        this.error = err?.error?.mensaje || 'No se pudo guardar.';
      },
    });
  }
}
