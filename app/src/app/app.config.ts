import {
  ApplicationConfig,
  inject,
  provideAppInitializer,
  provideBrowserGlobalErrorListeners,
} from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { insertTokenInterceptorInterceptor } from './interceptors/insert-token-interceptor-interceptor';
import { refreshTokenInterceptor } from './interceptors/refresh-token-interceptor-interceptor';
import { AuthService } from './services/auth-service';

export const appConfig: ApplicationConfig = {
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideRouter(routes),
    provideHttpClient(
      withInterceptors([insertTokenInterceptorInterceptor, refreshTokenInterceptor]),
    ),
    provideAppInitializer(() => {
      const auth = inject(AuthService);
      return auth.initAuth();
    }),
  ],
};
