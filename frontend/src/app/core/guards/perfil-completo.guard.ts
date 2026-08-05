import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { map, of } from 'rxjs';
import { PerfilService } from '../services/perfil.service';

export const perfilCompletoGuard: CanActivateFn = (route, state) => {
  const perfil = inject(PerfilService);
  const router = inject(Router);

  // dejar entrar siempre a la propia pantalla de perfil (para poder completarlo)
  if (state.url.includes('/dashboard/perfil')) return of(true);

  return perfil.perfil().pipe(
    map(p => (p.requiereCambioPassword || !p.perfilCompleto)
      ? router.parseUrl('/dashboard/perfil')
      : true)
  );
};
