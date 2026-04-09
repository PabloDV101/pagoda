import { Component, signal, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.scss'
})
export class LoginComponent implements OnInit {
  private authService = inject(AuthService);
  private router = inject(Router);

  usuario = signal('');
  contrasena = signal('');
  error = signal<string | null>(null);
  cargando = signal(false);
  mostrarContrasena = signal(false);

  ngOnInit() {
    // Si ya está autenticado, redirigir a la app
    if (this.authService.isAuthenticated()) {
      this.router.navigate(['/ventas']);
    }
  }

  login() {
    this.error.set(null);

    if (!this.usuario() || !this.contrasena()) {
      this.error.set('Por favor ingresa usuario y contraseña');
      return;
    }

    this.cargando.set(true);

    this.authService.login(this.usuario(), this.contrasena()).subscribe({
      next: (response) => {
        this.authService.setAuth(response);
        this.router.navigate(['/ventas']);
        this.cargando.set(false);
      },
      error: (err) => {
        const message = err.error?.message || 'Credenciales inválidas. Intenta de nuevo.';
        this.error.set(message);
        this.cargando.set(false);
      }
    });
  }

  toggleMostrarContrasena() {
    this.mostrarContrasena.update(v => !v);
  }

  setUsuario(valor: string) {
    this.usuario.set(valor);
  }

  setContrasena(valor: string) {
    this.contrasena.set(valor);
  }
}
