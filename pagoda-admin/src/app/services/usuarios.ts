import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

export interface Usuario {
  id: number;
  nombre: string;
  rol: string;
  activo: boolean;
}

export interface CrearUsuarioDTO {
  nombre: string;
  rolId: number;
  pinHash: string;
  contrasena: string;
}

export interface ActualizarUsuarioDTO {
  nombre: string;
  rolId: number;
  pinHash: string;
  contrasena?: string;
  activo: boolean;
}

export interface Rol {
  id: number;
  nombre: string;
}

@Injectable({ providedIn: 'root' })
export class UsuariosService {
  private http = inject(HttpClient);
  private base = environment.apiUrl;

  listarUsuarios(): Observable<Usuario[]> {
    return this.http.get<any>(
      `${this.base}/operacion/usuarios`
    ).pipe(map(r => r.data));
  }

  obtenerUsuario(id: number): Observable<Usuario> {
    return this.http.get<any>(
      `${this.base}/operacion/usuarios/${id}`
    ).pipe(map(r => r.data));
  }

  crearUsuario(dto: CrearUsuarioDTO): Observable<Usuario> {
    return this.http.post<any>(
      `${this.base}/operacion/usuarios`, dto
    ).pipe(map(r => r.data));
  }

  actualizarUsuario(id: number, dto: ActualizarUsuarioDTO): Observable<Usuario> {
    return this.http.put<any>(
      `${this.base}/operacion/usuarios/${id}`, dto
    ).pipe(map(r => r.data));
  }

  eliminarUsuario(id: number): Observable<void> {
    return this.http.delete<any>(
      `${this.base}/operacion/usuarios/${id}`
    ).pipe(map(r => r.data));
  }

  listarRoles(): Observable<Rol[]> {
    return this.http.get<any>(
      `${this.base}/catalogos/roles`
    ).pipe(map(r => r.data));
  }
}
