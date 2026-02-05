import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from './services/auth-service';
import { inject } from '@angular/core';
import { catchError, map, of } from 'rxjs';

export const authGuard: CanActivateFn = (route, state) => {
  const authService: AuthService = inject(AuthService);
  const router: Router = inject(Router);

  if (authService.getLoggedUser() !== null) {
    return true;
  }

  return authService.refreshResquest().pipe(
    map((result) => {
      authService.setLoggedUser(result);
      return true;
    }),
    catchError(() => {
      authService.setLoggedUser(null);
      router.navigate(['auth']);
      return of(false);
    }),
  );
};
