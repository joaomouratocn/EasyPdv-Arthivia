import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth-service';

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
  ) {}

  go() {
    this.route.navigate(['category']);
  }

  logout() {
    this.authService.logout();
  }
}
