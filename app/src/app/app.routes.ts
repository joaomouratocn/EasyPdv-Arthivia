import { Routes } from '@angular/router';
import { AuthPage } from './pages/auth-page/auth-page';
import { HomePage } from './pages/home-page/home-page';
import { authGuard } from './auth-guard';
import { CategoryPage } from './pages/category-page/category-page';
import { PageNotFound } from './pages/page-not-found/page-not-found';
import { loginGuard } from './login-guard';

export const routes: Routes = [
  { path: '', component: HomePage, canActivate: [authGuard] },
  { path: 'auth', component: AuthPage, canActivate: [loginGuard] },
  { path: 'category', component: CategoryPage, canActivate: [authGuard] },
  { path: '**', component: PageNotFound },
];
