import { Component } from '@angular/core';
import { NgOptimizedImage } from '@angular/common';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth-service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login-page',
  standalone: true,
  imports: [NgOptimizedImage, ReactiveFormsModule],
  templateUrl: './auth-page.html',
  styleUrl: './auth-page.css',
})
export class AuthPage {
  formFields = new FormGroup({
    username: new FormControl<string>('', { nonNullable: true, validators: Validators.required }),
    password: new FormControl<string>('', { nonNullable: true, validators: Validators.required }),
  });

  constructor(
    private authService: AuthService,
    private router: Router,
  ) {}

  auth() {
    this.formFields.markAllAsTouched();

    if (this.formFields.invalid) {
      return;
    }

    this.authService.authRequest(this.formFields.getRawValue());
  }

  isInvalidField(nameField: string): boolean {
    if (this.formFields.get(nameField)?.invalid && this.formFields.get(nameField)?.touched) {
      return true;
    } else {
      return false;
    }
  }

  showAlert(message: string) {
    window.alert(message);
  }
}
