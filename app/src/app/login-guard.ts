import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from './services/auth-service';
import { catchError, map, of } from 'rxjs';

export const loginGuard: CanActivateFn = (route, state) => {
  const authService: AuthService = inject(AuthService);
  const router = inject(Router);

  if (authService.getLoggedUser()) {
    router.navigate(['']);
    return false;
  }

  return true;
};
