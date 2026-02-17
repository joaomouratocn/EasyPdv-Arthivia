import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { catchError, throwError } from 'rxjs';

export const errorInterceptor: HttpInterceptorFn = (req, next) => {
  let isSecondTry = false;
  const toast = inject(ToastrService);

  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      let message = 'Ocorreu um erro inesperado';

      switch (error.status) {
        case 0:
          message = 'Conexão recusada. Verifique se o servidor está ligado.';
          break;
        case 401:
          if (isSecondTry) message = 'Sessão expirada ou usuário não autorizado.';
          break;
        case 403:
          message = 'Você não tem permissão para esta ação.';
          break;
        case 404:
          message = 'Recurso não encontrado (404).';
          break;
        case 500:
          message = 'Erro de comunicação com servidor.';
          break;
        case 502:
          message = 'O Proxy não conseguiu encontrar o Backend (Bad Gateway).';
          break;
        default:
          message = error.error?.message || message;
      }

      toast.error(message, 'Erro de Comunicação');
      return throwError(() => error);
    }),
  );
};
