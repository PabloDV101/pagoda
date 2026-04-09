import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { environment } from '../../environments/environment';
import { ApiResponse, ResumenVentasDiario, Venta } from '../models/index';

@Injectable({ providedIn: 'root' })
export class VentasService {
  private http = inject(HttpClient);
  private base = environment.apiUrl;

  getJornadaActual(): Observable<any> {
    return this.http.get<ApiResponse<any>>(
      `${this.base}/operacion/jornadas/estado`
    ).pipe(map(r => r.data));
  }

  getResumenVentas(jornadaId: number): Observable<ResumenVentasDiario[]> {
    return this.http.get<ApiResponse<ResumenVentasDiario[]>>(
      `${this.base}/reportes/ventas-diarias/jornada/${jornadaId}`
    ).pipe(map(r => r.data));
  }

  getVentasActivas(): Observable<Venta[]> {
    return this.http.get<ApiResponse<Venta[]>>(
      `${this.base}/ventas/activas`
    ).pipe(map(r => r.data));
  }
}