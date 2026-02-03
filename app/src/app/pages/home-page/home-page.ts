import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth-service';
import { CategoryService } from '../../services/category-service';

@Component({
  selector: 'app-home-page',
  imports: [],
  templateUrl: './home-page.html',
  styleUrl: './home-page.css',
})
export class HomePage {
  constructor(
    private route: Router,
    private authService: AuthService,
    private categoryService: CategoryService,
  ) {}

  go() {
    this.route.navigate(['category']);
  }

  logout() {
    this.authService.logout();
  }

  refresh() {
    this.authService.refreshResquest();
  }

  insertCategory() {
    this.categoryService.insertCategory('Bebidas').subscribe({
      next: (result) => {
        console.log(result.message);
      },
      error: (err) => {
        console.log(err?.error.message);
      },
    });
  }
}
