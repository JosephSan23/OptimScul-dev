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
import { AuthInterceptor } from './core/interceptors/auth.interceptor'; // ajusta la ruta si está en otro lado
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
import { StaffListaComponent } from './features/staff/staff-lista/staff-lista.component';
import { StaffFormComponent } from './features/staff/staff-form/staff-form.component';
import { SedesListaComponent } from './features/config/sedes-lista/sedes-lista.component';
import { SedeFormComponent } from './features/config/sede-form/sede-form.component';
import { JornadasListaComponent } from './features/config/jornadas-lista/jornadas-lista.component';
import { JornadaFormComponent } from './features/config/jornada-form/jornada-form.component';
import { DatosColegioComponent } from './features/config/datos-colegio/datos-colegio.component';
import { AniosLectivosListaComponent } from './features/config/anios-lectivos-lista/anios-lectivos-lista.component';
import { AnioLectivoFormComponent } from './features/config/anio-lectivo-form/anio-lectivo-form.component'

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
