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
import { AdministradoresListaComponent } from './features/administradores/administradores-lista/administradores-lista.component';
import { roleGuard } from './core/guards/role.guard';
import { StaffListaComponent } from './features/staff/staff-lista/staff-lista.component';
import { StaffFormComponent } from './features/staff/staff-form/staff-form.component';
import { SedesListaComponent } from './features/config/sedes-lista/sedes-lista.component';
import { SedeFormComponent } from './features/config/sede-form/sede-form.component';
import { JornadasListaComponent } from './features/config/jornadas-lista/jornadas-lista.component';
import { JornadaFormComponent } from './features/config/jornada-form/jornada-form.component';
import { DatosColegioComponent } from './features/config/datos-colegio/datos-colegio.component';
import { AniosLectivosListaComponent } from './features/config/anios-lectivos-lista/anios-lectivos-lista.component';
import { AnioLectivoFormComponent } from './features/config/anio-lectivo-form/anio-lectivo-form.component';

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
    { path: 'admin',          component: DashboardComponent,          canActivate: [roleGuard], data: { soloSuperAdmin: true } },
    { path: 'staff',       component: StaffListaComponent, canActivate: [roleGuard], data: { roles: ['ADMIN_INSTITUCION'] } },
    { path: 'staff/nuevo', component: StaffFormComponent,  canActivate: [roleGuard], data: { roles: ['ADMIN_INSTITUCION'] } },
    { path: 'staff/:id',   component: StaffFormComponent,  canActivate: [roleGuard], data: { roles: ['ADMIN_INSTITUCION'] } },
    { path: 'sedes',       component: SedesListaComponent, canActivate: [roleGuard], data: { roles: ['ADMIN_INSTITUCION'] } },
    { path: 'sedes/nuevo', component: SedeFormComponent,   canActivate: [roleGuard], data: { roles: ['ADMIN_INSTITUCION'] } },
    { path: 'sedes/:id',   component: SedeFormComponent,   canActivate: [roleGuard], data: { roles: ['ADMIN_INSTITUCION'] } },
    { path: 'jornadas',       component: JornadasListaComponent, canActivate: [roleGuard], data: { roles: ['ADMIN_INSTITUCION'] } },
    { path: 'jornadas/nuevo', component: JornadaFormComponent,   canActivate: [roleGuard], data: { roles: ['ADMIN_INSTITUCION'] } },
    { path: 'jornadas/:id',   component: JornadaFormComponent,   canActivate: [roleGuard], data: { roles: ['ADMIN_INSTITUCION'] } },
    { path: 'institucion', component: DatosColegioComponent, canActivate: [roleGuard], data: { roles: ['ADMIN_INSTITUCION'] } },
    { path: 'anios-lectivos',       component: AniosLectivosListaComponent, canActivate: [roleGuard], data: { roles: ['ADMIN_INSTITUCION'] } },
    { path: 'anios-lectivos/nuevo', component: AnioLectivoFormComponent,    canActivate: [roleGuard], data: { roles: ['ADMIN_INSTITUCION'] } },
    { path: 'anios-lectivos/:id',   component: AnioLectivoFormComponent,    canActivate: [roleGuard], data: { roles: ['ADMIN_INSTITUCION'] } },

    { path: 'instituciones',  component: InstitucionesListaComponent, canActivate: [roleGuard], data: { soloSuperAdmin: true } },
    { path: 'solicitudes',    component: SolicitudesListaComponent,   canActivate: [roleGuard], data: { soloSuperAdmin: true } },
    { path: 'administradores',component: AdministradoresListaComponent, canActivate: [roleGuard], data: { soloSuperAdmin: true } },
    { path: 'colegio',        component: DashboardComponent,          canActivate: [roleGuard], data: { roles: ['ADMIN_INSTITUCION'] } },
    { path: 'profesor',       component: DashboardComponent,          canActivate: [roleGuard], data: { roles: ['DOCENTE'] } },
    { path: 'estudiante',     component: DashboardComponent,          canActivate: [roleGuard], data: { roles: ['ESTUDIANTE'] } },
    { path: 'acudiente',      component: DashboardComponent,          canActivate: [roleGuard], data: { roles: ['ACUDIENTE'] } },
  ]
  },

  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { scrollPositionRestoration: 'top', anchorScrolling: 'enabled' })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
