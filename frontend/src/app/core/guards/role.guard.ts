import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

export const roleGuard: CanActivateFn = (route) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  const soloSuperAdmin = route.data?.['soloSuperAdmin'] === true;
  const rolesPermitidos = route.data?.['roles'] as string[] | undefined;

  if (soloSuperAdmin) {
    if (authService.esSuperAdmin()) return true;
    router.navigate(['/']);
    return false;
  }

  if (rolesPermitidos && rolesPermitidos.length) {
    if (rolesPermitidos.some(r => authService.tieneRol(r))) return true;
    router.navigate(['/']);
    return false;
  }

  return true;   // ruta sin restricción específica
};
