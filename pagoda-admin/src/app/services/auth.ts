import { Injectable, signal, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { environment } from '../../environments/environment';

export interface LoginResponse {
  id: number;
  usuario: string;
  activo: boolean;
  token: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private http = inject(HttpClient);
  private router = inject(Router);
  private apiUrl = `${environment.apiUrl}/admin`;

  // Signal para token
  token = signal<string | null>(this.getTokenFromStorage());
  usuario = signal<string | null>(this.getUserFromStorage());
  isAuthenticated = signal<boolean>(!!this.token());

  constructor() {
    // Recuperar token de localStorage si existe
    const savedToken = localStorage.getItem('authToken');
    if (savedToken) {
      this.token.set(savedToken);
      this.usuario.set(localStorage.getItem('adminUsuario'));
      this.isAuthenticated.set(true);
    }
  }

  login(usuario: string, contrasena: string) {
    return this.http.post<any>(`${this.apiUrl}/login`, { usuario, contrasena });
  }

  setAuth(response: any) {
    const loginData = response.data;
    this.token.set(loginData.token);
    this.usuario.set(loginData.usuario);
    this.isAuthenticated.set(true);

    localStorage.setItem('authToken', loginData.token);
    localStorage.setItem('adminUsuario', loginData.usuario);
    localStorage.setItem('adminId', loginData.id);
  }

  logout() {
    this.token.set(null);
    this.usuario.set(null);
    this.isAuthenticated.set(false);

    localStorage.removeItem('authToken');
    localStorage.removeItem('adminUsuario');
    localStorage.removeItem('adminId');

    this.router.navigate(['/login']);
  }

  getToken(): string | null {
    return this.token();
  }

  private getTokenFromStorage(): string | null {
    return localStorage.getItem('authToken');
  }

  private getUserFromStorage(): string | null {
    return localStorage.getItem('adminUsuario');
  }
}
