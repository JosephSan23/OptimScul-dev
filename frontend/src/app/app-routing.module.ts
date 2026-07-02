import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LandingComponent } from './features/landing/landing.component';
import { PrimerosPasosComponent } from './features/primeros-pasos/primeros-pasos.component';
import { ContactoComponent } from './features/contacto/contacto.component';
import { LoginComponent } from './features/auth/login/login.component';
import { DashboardComponent } from './features/dashboard/dashboard.component';
import { authGuard } from './core/guards/auth.guard';

const routes: Routes = [
  { path: '', component: LandingComponent },
  { path: 'primeros-pasos', component: PrimerosPasosComponent },
  { path: 'contacto', component: ContactoComponent },
  { path: 'login', component: LoginComponent, data: { animation: 'Login'} },

  { path: 'dashboard/admin',      component: DashboardComponent, canActivate: [authGuard] },
  { path: 'dashboard/colegio',    component: DashboardComponent, canActivate: [authGuard] },
  { path: 'dashboard/profesor',   component: DashboardComponent, canActivate: [authGuard] },
  { path: 'dashboard/estudiante', component: DashboardComponent, canActivate: [authGuard] },
  { path: 'dashboard/acudiente',  component: DashboardComponent, canActivate: [authGuard] },

  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { scrollPositionRestoration: 'top', anchorScrolling: 'enabled' })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
