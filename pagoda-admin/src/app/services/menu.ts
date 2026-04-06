import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Producto, CrearProductoDTO, ActualizarProductoDTO } from '../models/index';

@Injectable({ providedIn: 'root' })
export class MenuService {
  private http = inject(HttpClient);
  private base = environment.apiUrl;

  getProductos(): Observable<Producto[]> {
    return this.http.get<Producto[]>(`${this.base}/productos`);
  }

  crearProducto(dto: CrearProductoDTO): Observable<Producto> {
    return this.http.post<Producto>(`${this.base}/productos`, dto);
  }

  actualizarProducto(id: number, dto: ActualizarProductoDTO): Observable<Producto> {
    return this.http.put<Producto>(`${this.base}/productos/${id}`, dto);
  }

  eliminarProducto(id: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/productos/${id}`);
  }
}