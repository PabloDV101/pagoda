import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { environment } from '../../environments/environment';
import { ApiResponse, Categoria } from '../models/index';

@Injectable({ providedIn: 'root' })
export class CategoriasService {
  private http = inject(HttpClient);
  private base = environment.apiUrl;

  getCategorias(): Observable<Categoria[]> {
    return this.http.get<ApiResponse<Categoria[]>>(
      `${this.base}/catalogos/categorias`
    ).pipe(map(r => r.data));
  }

  crearCategoria(categoria: Categoria): Observable<Categoria> {
    return this.http.post<ApiResponse<Categoria>>(
      `${this.base}/catalogos/categorias`, categoria
    ).pipe(map(r => r.data));
  }

  actualizarCategoria(id: number, categoria: Categoria): Observable<Categoria> {
    return this.http.put<ApiResponse<Categoria>>(
      `${this.base}/catalogos/categorias/${id}`, categoria
    ).pipe(map(r => r.data));
  }

  eliminarCategoria(id: number): Observable<void> {
    return this.http.delete<ApiResponse<void>>(
      `${this.base}/catalogos/categorias/${id}`
    ).pipe(map(r => r.data));
  }
}
