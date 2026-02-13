import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthService } from '../services/auth-service';
import { catchError, switchMap, throwError } from 'rxjs';

let isRefreshing = false;

export const refreshTokenInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);

  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      // Só tenta refresh em 401
      if (error.status !== 401) {
        return throwError(() => error);
      }

      // Evita loop infinito (ex: erro no /refresh)
      if (req.url.includes('/auth/refresh')) {
        authService.logoutRequest();
        return throwError(() => error);
      }

      if (isRefreshing) {
        return throwError(() => error);
      }

      isRefreshing = true;

      return authService.refreshResquest().pipe(
        switchMap((response) => {
          isRefreshing = false;

          // Salva novo access token
          authService.setLoggedUser(response);

          // Reenvia a request original com token novo
          const newReq = req.clone({
            setHeaders: {
              Authorization: `Bearer ${response.token}`,
            },
          });

          return next(newReq);
        }),
        catchError((refreshError) => {
          isRefreshing = false;
          window.alert(refreshError);
          authService.logoutRequest();
          return throwError(() => refreshError);
        }),
      );
    }),
  );
};
