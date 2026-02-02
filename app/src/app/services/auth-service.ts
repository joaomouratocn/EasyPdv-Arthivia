import { HttpClient } from '@angular/common/http';
import { Observable, ObservedValueOf } from 'rxjs';
import { AuthResponse as AuthResponse } from '../models/interfaces/auth-response-interface';
import { AuthModel } from '../models/models/auth-model';
import { App } from '../app';
import { Router } from '@angular/router';
import { inject, Injectable, signal } from '@angular/core';
import { SuccessResponse } from '../models/interfaces/SuccessResponse';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly router = inject(Router);
  private name = signal<string | null>(null);

  constructor(private httpClient: HttpClient) {}

  authRequest(loginModel: AuthModel): Observable<AuthResponse> {
    const path = 'auth/login';
    return this.httpClient.post<AuthResponse>(App.BASE_URL + path, loginModel);
  }

  logoutRequest(): Observable<SuccessResponse> {
    const path = 'auth/logout';
    return this.httpClient.post<SuccessResponse>(
      App.BASE_URL + path,
      {},
      { withCredentials: true },
    );
  }

  logout() {
    this.logoutRequest().subscribe({
      next: (result) => {
        console.log(result.message);
        this.name.set(null);
        localStorage.removeItem('name');
        this.router.navigate(['/auth']);
      },
      error: (err) => {
        console.log(err?.error.message);
      },
    });
  }

  initAuth() {
    const savedUser = localStorage.getItem('name');
    if (savedUser) {
      this.name.set(savedUser);
    } else {
      this.router.navigate(['/auth']);
    }
  }

  setLoggedUser(name: string) {
    this.name.set(name);
  }

  getLoggedUser() {
    return this.name();
  }
}
