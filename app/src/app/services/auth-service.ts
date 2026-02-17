import { HttpClient } from '@angular/common/http';
import { AuthResponse } from '../models/interfaces/auth-response-interface';
import { Router } from '@angular/router';
import { inject, Injectable, signal } from '@angular/core';
import { SuccessResponse } from '../models/interfaces/SuccessResponse';
import { DataLoginModel } from '../models/models/data-login-model';
import { AuthModelRequest } from '../models/interfaces/auth-model-request';
import { catchError, firstValueFrom, Observable, of, tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly router = inject(Router);
  private dataLogin = signal<DataLoginModel | null>(null);

  constructor(private httpClient: HttpClient) {}

  async initAuth(): Promise<void> {
    try {
      const result = await firstValueFrom(this.refreshResquest());
      this.setLoggedUser(result);
    } catch (err) {
      return;
    }
  }

  authRequest(loginModel: AuthModelRequest): Observable<AuthResponse> {
    const path = '/api/auth/login';
    return this.httpClient.post<AuthResponse>(path, loginModel);
  }

  refreshResquest(): Observable<DataLoginModel> {
    const path = '/api/auth/refresh';
    return this.httpClient.post<AuthResponse>(path, {}, { withCredentials: true });
  }

  logoutRequest() {
    const path = '/api/auth/logout';
    this.httpClient.post<SuccessResponse>(path, {}).subscribe({
      next: (result) => {
        this.setLoggedUser(null);
        this.router.navigate(['/auth']);
      },
    });
  }

  getLoggedUser() {
    return this.dataLogin();
  }

  setLoggedUser(dataLogin: DataLoginModel | null) {
    this.dataLogin.set(dataLogin);
  }
}
