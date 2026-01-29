import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthResponse as AuthResponse } from '../models/interfaces/auth-response-interface';
import { AuthModel } from '../models/models/auth-model';
import { App } from '../app';
import { Router } from '@angular/router';
import { inject, Injectable, signal } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly router = inject(Router);
  private name = signal<string | null>(null);
  private path = '/auth/login';

  constructor(private httpClient: HttpClient) {}

  auth(loginModel: AuthModel): Observable<AuthResponse> {
    return this.httpClient.post<AuthResponse>(App.BASE_URL + this.path, loginModel);
  }

  logout() {
    this.name.set(null);
    localStorage.removeItem('name');
    this.router.navigate(['/auth']);
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

  /**
   *   this.http.post('/api/login', dados, {
        withCredentials: true

      })

      this.http.get('/api/protegido', {
      ithCredentials: true
      })
   */
}
