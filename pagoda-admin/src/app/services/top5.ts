import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { ResumenPlatillosDiario } from '../models/index';

@Injectable({ providedIn: 'root' })
export class Top5Service {
  private http = inject(HttpClient);
  private base = environment.apiUrl;

  getTop5(fechaInicio: string, fechaFin: string): Observable<ResumenPlatillosDiario[]> {
    const params = new HttpParams()
      .set('fecha_inicio', fechaInicio)
      .set('fecha_fin', fechaFin);
    return this.http.get<ResumenPlatillosDiario[]>(
      `${this.base}/reportes/top5`, { params }
    );
  }
}