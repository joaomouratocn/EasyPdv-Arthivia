import { HttpInterceptorFn } from '@angular/common/http';
import { AuthService } from '../services/auth-service';
import { inject } from '@angular/core';

export const insertTokenInterceptorInterceptor: HttpInterceptorFn = (req, next) => {
  const authService: AuthService = inject(AuthService);

  const dataLogin = authService.getLoggedUser();

  if (dataLogin) {
    // Clona a requisição e adiciona o header Authorization
    const authReq = req.clone({
      setHeaders: { Authorization: `Bearer ${dataLogin.token}` },
    });
    return next(authReq);
  }

  // Se não houver token, envia requisição normal
  return next(req);
};
