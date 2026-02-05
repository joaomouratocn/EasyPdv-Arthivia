import { HttpClient } from '@angular/common/http';
import { AuthResponse } from '../models/interfaces/auth-response-interface';
import { Router } from '@angular/router';
import { inject, Injectable, signal } from '@angular/core';
import { SuccessResponse } from '../models/interfaces/SuccessResponse';
import { DataLoginModel } from '../models/models/data-login-model';
import { AuthModelRequest } from '../models/interfaces/auth-model-request';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly router = inject(Router);
  private dataLogin = signal<DataLoginModel | false>(false);

  constructor(private httpClient: HttpClient) {}

  authRequest(loginModel: AuthModelRequest): Observable<AuthResponse> {
    const path = '/api/auth/login';
    return this.httpClient.post<AuthResponse>(path, loginModel);
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
        this.router.navigate(['auth']);
      },
    });
  }

  logoutRequest(): Observable<SuccessResponse> {
    const path = '/api/auth/logout';
    return this.httpClient.post<SuccessResponse>(path, {}, { withCredentials: true });
  }

  getLoggedUser() {
    return this.dataLogin();
  }

  setLoggedUser(dataLogin: DataLoginModel) {
    this.dataLogin.set(dataLogin);
  }
}
