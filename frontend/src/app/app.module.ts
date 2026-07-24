import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarComponent } from './core/components/navbar/navbar.component';
import { FooterComponent } from './core/components/footer/footer.component';
import { LandingComponent } from './features/landing/landing.component';
import { PrimerosPasosComponent } from './features/primeros-pasos/primeros-pasos.component';
import { ContactoComponent } from './features/contacto/contacto.component';
import { FormsModule } from '@angular/forms';
import { LoginComponent } from './features/auth/login/login.component';
import { AuthInterceptor } from './core/interceptors/auth.interceptor';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { DashboardComponent } from './features/dashboard/dashboard.component';
import { LayoutComponent } from './core/components/layout/layout.component';
import { InstitucionesListaComponent } from './features/instituciones/instituciones-lista/instituciones-lista.component';
import { InstitucionFormComponent } from './features/instituciones/institucion-form/institucion-form.component';
import { SolicitudesListaComponent } from './features/solicitudes/solicitudes-lista/solicitudes-lista.component';
import { RegistroComponent } from './features/auth/registro/registro.component';
import { SeguimientoComponent } from './features/seguimiento/seguimiento/seguimiento.component';
import { AdministradorFormComponent } from './features/administradores/administrador-form/administrador-form.component';
import { AdministradoresListaComponent } from './features/administradores/administradores-lista/administradores-lista.component';
import { TablaPaginadaComponent } from './core/components/tabla-paginada/tabla-paginada.component';
import { FilaTablaDirective } from './core/directives/fila-tabla.directive';
import { StaffListaComponent } from './features/adminInstitucion/staff/staff-lista/staff-lista.component';
import { StaffFormComponent } from './features/adminInstitucion/staff/staff-form/staff-form.component';
import { SedesListaComponent } from './features/adminInstitucion/config/sedes-lista/sedes-lista.component';
import { SedeFormComponent } from './features/adminInstitucion/config/sede-form/sede-form.component';
import { JornadasListaComponent } from './features/adminInstitucion/config/jornadas-lista/jornadas-lista.component';
import { JornadaFormComponent } from './features/adminInstitucion/config/jornada-form/jornada-form.component';
import { DatosColegioComponent } from './features/adminInstitucion/config/datos-colegio/datos-colegio.component';
import { AniosLectivosListaComponent } from './features/adminInstitucion/config/anios-lectivos-lista/anios-lectivos-lista.component';
import { AnioLectivoFormComponent } from './features/adminInstitucion/config/anio-lectivo-form/anio-lectivo-form.component';
import { PeriodosListaComponent } from './features/adminInstitucion/config/periodos-lista/periodos-lista.component';
import { PeriodoFormComponent } from './features/adminInstitucion/config/periodo-form/periodo-form.component';
import { EstudianteFormComponent } from './features/cooracademico/estudiante-form/estudiante-form.component';
import { EstudiantesListaComponent } from './features/cooracademico/estudiantes-lista/estudiantes-lista.component';
import { AcudientesListaComponent } from './features/cooracademico/community/acudientes-lista/acudientes-lista.component';
import { AcudienteFormComponent } from './features/cooracademico/community/acudiente-form/acudiente-form.component';
import { GradosListaComponent } from './features/cooracademico/grados/grados-lista/grados-lista.component';
import { GradoFormComponent } from './features/cooracademico/grados/grado-form/grado-form.component';
import { GruposListaComponent } from './features/cooracademico/grupos/grupos-lista/grupos-lista.component';
import { GrupoFormComponent } from './features/cooracademico/grupos/grupo-form/grupo-form.component';
import { AreasListaComponent } from './features/cooracademico/areas/areas-lista/areas-lista.component';
import { AreaFormComponent } from './features/cooracademico/areas/area-form/area-form.component';
import { AsignaturasListaComponent } from './features/cooracademico/asignaturas/asignaturas-lista/asignaturas-lista.component';
import { AsignaturaFormComponent } from './features/cooracademico/asignaturas/asignatura-form/asignatura-form.component';
import { CargasListaComponent } from './features/cooracademico/cargaAcademica/cargas-lista/cargas-lista.component';
import { CargaFormComponent } from './features/cooracademico/cargaAcademica/carga-form/carga-form.component';
import { HorariosListaComponent } from './features/cooracademico/horarios/horarios-lista/horarios-lista.component';
import { HorarioFormComponent } from './features/cooracademico/horarios/horario-form/horario-form.component';
import { MatriculasListaComponent } from './features/cooracademico/matriculas/matriculas-lista/matriculas-lista.component';
import { MatriculaFormComponent } from './features/cooracademico/matriculas/matricula-form/matricula-form.component';
import { MisClasesListComponent } from './features/docente/clases/mis-clases-list/mis-clases-list.component';
import { ClaseDetalleComponent } from './features/docente/clases/clase-detalle/clase-detalle.component';
import { AsistenciaComponent } from './features/docente/clases/asistencia/asistencia.component';
import { ReporteAsistenciaComponent } from './features/docente/reportes/reporte-asistencia/reporte-asistencia.component';
import { ConfigAcademicaComponent } from './features/adminInstitucion/config-academica/config-academica/config-academica.component';
import { EscalasListaComponent } from './features/adminInstitucion/config-academica/escalas-lista/escalas-lista.component';
import { EscalaFormComponent } from './features/adminInstitucion/config-academica/escala-form/escala-form.component';
import { MatrizAsistenciaComponent } from './features/cooracademico/asistencia/matriz-asistencia/matriz-asistencia.component';
import { ActividadesListaComponent } from './features/docente/clases/actividades/actividades-lista/actividades-lista.component';
import { ActividadFormComponent } from './features/docente/clases/actividades/actividad-form/actividad-form.component';
import { CalificarComponent } from './features/docente/clases/actividades/calificar/calificar/calificar.component';


@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    FooterComponent,
    LandingComponent,
    PrimerosPasosComponent,
    ContactoComponent,
    LoginComponent,
    DashboardComponent,
    LayoutComponent,
    InstitucionesListaComponent,
    InstitucionFormComponent,
    SolicitudesListaComponent,
    RegistroComponent,
    SeguimientoComponent,
    AdministradorFormComponent,
    AdministradoresListaComponent,
    TablaPaginadaComponent,
    FilaTablaDirective,
    StaffListaComponent,
    StaffFormComponent,
    SedesListaComponent,
    SedeFormComponent,
    JornadasListaComponent,
    JornadaFormComponent,
    DatosColegioComponent,
    AniosLectivosListaComponent,
    AnioLectivoFormComponent,
    PeriodosListaComponent,
    PeriodoFormComponent,
    EstudianteFormComponent,
    EstudiantesListaComponent,
    AcudientesListaComponent,
    AcudienteFormComponent,
    GradosListaComponent,
    GradoFormComponent,
    GruposListaComponent,
    GrupoFormComponent,
    AreasListaComponent,
    AreaFormComponent,
    AsignaturasListaComponent,
    AsignaturaFormComponent,
    CargasListaComponent,
    CargaFormComponent,
    HorariosListaComponent,
    HorarioFormComponent,
    MatriculasListaComponent,
    MatriculaFormComponent,
    MisClasesListComponent,
    ClaseDetalleComponent,
    AsistenciaComponent,
    ReporteAsistenciaComponent,
    ConfigAcademicaComponent,
    EscalasListaComponent,
    EscalaFormComponent,
    MatrizAsistenciaComponent,
    ActividadesListaComponent,
    ActividadFormComponent,
    CalificarComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule

  ],
  providers: [{
    provide: HTTP_INTERCEPTORS,
    useClass: AuthInterceptor,
    multi: true
  }],
  bootstrap: [AppComponent]
})
export class AppModule { }
