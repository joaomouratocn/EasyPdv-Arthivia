import { Routes } from '@angular/router';
import { LoginPage } from './pages/login-page/login-page';
import { HomePage } from './pages/home-page/home-page';

export const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home', component: HomePage },
  { path: 'login', component: LoginPage },
];
