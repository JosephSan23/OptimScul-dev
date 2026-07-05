import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LandingComponent } from './features/landing/landing.component';
import { PrimerosPasosComponent } from './features/primeros-pasos/primeros-pasos.component';
import { ContactoComponent } from './features/contacto/contacto.component';
import { LoginComponent } from './features/auth/login/login.component';
import { RegistroComponent } from './features/auth/registro/registro.component';
import { DashboardComponent } from './features/dashboard/dashboard.component';
import { authGuard } from './core/guards/auth.guard';
import { LayoutComponent } from './core/components/layout/layout.component';
import { InstitucionesListaComponent } from './features/instituciones/instituciones-lista/instituciones-lista.component';
import { SolicitudesListaComponent } from './features/solicitudes/solicitudes-lista/solicitudes-lista.component';
import { SeguimientoComponent } from './features/seguimiento/seguimiento/seguimiento.component';

const routes: Routes = [
  { path: '', component: LandingComponent },
  { path: 'primeros-pasos', component: PrimerosPasosComponent },
  { path: 'seguimiento', component: SeguimientoComponent, canActivate: [authGuard] },
  { path: 'contacto', component: ContactoComponent, canActivate: [authGuard] },
  { path: 'login', component: LoginComponent, data: { animation: 'Login'} },
  { path: 'registro', component: RegistroComponent },

  {
  path: 'dashboard',
  component: LayoutComponent,
  canActivate: [authGuard],
  children: [
    { path: 'admin',      component: DashboardComponent },
    { path: 'instituciones', component: InstitucionesListaComponent },
    { path: 'solicitudes', component: SolicitudesListaComponent },
    { path: 'colegio',    component: DashboardComponent },
    { path: 'profesor',   component: DashboardComponent },
    { path: 'estudiante', component: DashboardComponent },
    { path: 'acudiente',  component: DashboardComponent },
  ]
  },

  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { scrollPositionRestoration: 'top', anchorScrolling: 'enabled' })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
