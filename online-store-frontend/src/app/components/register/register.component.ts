import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html'
})
export class RegisterComponent {
  email = '';
  password = '';
  constructor(private http: HttpClient) {}

  register() {
    this.http.post('/api/auth/register', { email: this.email, password: this.password, role: 'CUSTOMER' })
      .subscribe({
        next: () => alert('Registration successful'),
        error: () => alert('Registration failed')
      });
  }
}
