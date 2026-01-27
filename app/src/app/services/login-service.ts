import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LoginResponse } from '../models/interfaces/login-response-interface';
import { LoginModel } from '../models/models/login-model';
import { App } from '../app';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  constructor(private httpClient: HttpClient) {}

  login(loginModel: LoginModel): Observable<LoginResponse> {
    const path = '/login';
    return this.httpClient.post<LoginResponse>(App.BASE_URL + path, loginModel);
  }
}
