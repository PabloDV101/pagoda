import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { environment } from '../../environments/environment';
import { ApiResponse, Producto, CrearProductoDTO, ActualizarProductoDTO } from '../models/index';

@Injectable({ providedIn: 'root' })
export class MenuService {
  private http = inject(HttpClient);
  private base = environment.apiUrl;

  getProductos(): Observable<Producto[]> {
    return this.http.get<ApiResponse<Producto[]>>(
      `${this.base}/operacion/productos`
    ).pipe(map(r => r.data));
  }

  crearProducto(dto: CrearProductoDTO): Observable<Producto> {
    return this.http.post<ApiResponse<Producto>>(
      `${this.base}/operacion/productos`, dto
    ).pipe(map(r => r.data));
  }

  actualizarProducto(id: number, dto: ActualizarProductoDTO): Observable<Producto> {
    return this.http.put<ApiResponse<Producto>>(
      `${this.base}/operacion/productos/${id}`, dto
    ).pipe(map(r => r.data));
  }

  eliminarProducto(id: number): Observable<void> {
    return this.http.delete<ApiResponse<void>>(
      `${this.base}/operacion/productos/${id}`
    ).pipe(map(r => r.data));
  }
}