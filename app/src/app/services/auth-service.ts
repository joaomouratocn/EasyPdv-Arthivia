import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthResponse as AuthResponse } from '../models/interfaces/auth-response-interface';
import { Router } from '@angular/router';
import { inject, Injectable, signal } from '@angular/core';
import { SuccessResponse } from '../models/interfaces/SuccessResponse';
import { DataLoginModel } from '../models/models/data-login-model';
import { AuthModelRequest } from '../models/interfaces/auth-model-request';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly router = inject(Router);
  private dataLogin = signal<DataLoginModel | null>(null);

  constructor(private httpClient: HttpClient) {}

  authRequest(loginModel: AuthModelRequest) {
    const path = '/api/auth/login';
    this.httpClient.post<AuthResponse>(path, loginModel).subscribe({
      next: (result) => {
        console.log(result);
        const data = new DataLoginModel(result.name, result.token);
        this.dataLogin.set(data);
        this.router.navigate(['']);
      },
      error: (err) => {
        console.log(err?.error.message);
      },
    });
  }

  refreshResquest() {
    const path = '/api/auth/refresh';
    this.httpClient.post<AuthResponse>(path, {}, { withCredentials: true }).subscribe({
      next: (result) => {
        console.log(result);
        const data = new DataLoginModel(result.name, result.token);
        this.dataLogin.set(data);
      },
      error: (err) => {
        console.log(err?.error.message);
      },
    });
  }

  logoutRequest(): Observable<SuccessResponse> {
    const path = '/api/auth/logout';
    return this.httpClient.post<SuccessResponse>(path, {}, { withCredentials: true });
  }

  logout() {
    this.logoutRequest().subscribe({
      next: (result) => {
        console.log(result.message);
        this.dataLogin.set(null);
        this.router.navigate(['/auth']);
      },
      error: (err) => {
        console.log(err?.error.message);
      },
    });
  }

  getLoggedUser() {
    return this.dataLogin();
  }
}
