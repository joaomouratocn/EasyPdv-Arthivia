import { Component } from '@angular/core';
import { NgOptimizedImage } from '@angular/common';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { LoginService } from '../../services/login-service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login-page',
  standalone: true,
  imports: [NgOptimizedImage, ReactiveFormsModule],
  templateUrl: './login-page.html',
  styleUrl: './login-page.css',
})
export class LoginPage {
  formFields = new FormGroup({
    username: new FormControl<string>('', { nonNullable: true, validators: Validators.required }),
    password: new FormControl<string>('', { nonNullable: true, validators: Validators.required }),
  });

  constructor(
    private loginService: LoginService,
    private router: Router,
  ) {}

  login() {
    this.formFields.markAllAsTouched();

    if (this.formFields.invalid) {
      return;
    }

    this.loginService.login(this.formFields.getRawValue()).subscribe({
      next: (response) => {
        this.router.navigate(['home']);
      },
      error: (err) => {
        console.error('Erro no login', err);
      },
    });
  }

  isInvalidField(nameField: string): boolean {
    if (this.formFields.get(nameField)?.invalid && this.formFields.get(nameField)?.touched) {
      return true;
    } else {
      return false;
    }
  }
}
