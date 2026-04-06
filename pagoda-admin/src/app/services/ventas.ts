import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { ResumenVentasDiario, Venta, PeriodoVentas } from '../models/index';

@Injectable({ providedIn: 'root' })
export class VentasService {
  private http = inject(HttpClient);
  private base = environment.apiUrl;

  getResumen(periodo: PeriodoVentas): Observable<ResumenVentasDiario> {
    return this.http.get<ResumenVentasDiario>(
      `${this.base}/reportes/ventas/resumen`,
      { params: new HttpParams().set('periodo', periodo) }
    );
  }

  getVentas(periodo: PeriodoVentas): Observable<Venta[]> {
    return this.http.get<Venta[]>(
      `${this.base}/reportes/ventas`,
      { params: new HttpParams().set('periodo', periodo) }
    );
  }
}