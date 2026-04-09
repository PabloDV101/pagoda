import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';

export interface Administrador {
  id: number;
  usuario: string;
  activo: boolean;
}

export interface ActualizarAdministradorDTO {
  usuario: string;
  contrasenaActual: string;
  contrasenaNueva: string;
}

@Injectable({
  providedIn: 'root'
})
export class AdministradorService {
  private http = inject(HttpClient);
  private apiUrl = `${environment.apiUrl}/admin`;

  obtenerPerfil() {
    return this.http.get<any>(`${this.apiUrl}/perfil`);
  }

  actualizar(dto: ActualizarAdministradorDTO) {
    return this.http.put<any>(`${this.apiUrl}/actualizar`, dto);
  }
}
