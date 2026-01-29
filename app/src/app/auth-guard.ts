import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from './services/auth-service';
import { inject, signal } from '@angular/core';

export const authGuard: CanActivateFn = (route, state) => {
  const authService: AuthService = inject(AuthService);
  const router: Router = inject(Router);

  if (authService.getLoggedUser()) {
    return true;
  }

  router.navigate(['']);
  return false;
};
