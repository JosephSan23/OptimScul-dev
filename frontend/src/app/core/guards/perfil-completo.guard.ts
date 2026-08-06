import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { map, of } from 'rxjs';
import { PerfilService } from '../services/perfil.service';
import { AuthService } from '../services/auth.service';

export const perfilCompletoGuard: CanActivateFn = (route, state) => {
  const perfil = inject(PerfilService);
  const auth = inject(AuthService);
  const router = inject(Router);

  if (auth.getTipoContexto() !== 'INSTITUCION') return of(true);
  if (state.url.includes('/dashboard/perfil')) return of(true);

  return perfil.perfil().pipe(
    map(p => (!p.perfilCompleto) ? router.parseUrl('/dashboard/perfil') : true)
  );
};
