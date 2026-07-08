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
