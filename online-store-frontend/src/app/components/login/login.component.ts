import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html'
})
export class LoginComponent {
  email = '';
  password = '';
  constructor(private http: HttpClient) {}

  login() {
    this.http.post('/api/auth/login', { email: this.email, password: this.password })
      .subscribe({
        next: (res: any) => {
          alert('Login successful');
          localStorage.setItem('token', res.token);
          localStorage.setItem('role', res.role);
        },
        error: () => alert('Login failed')
      });
  }
}
