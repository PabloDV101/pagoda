import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { ResumenPropinasDiario } from '../models/index';

@Injectable({ providedIn: 'root' })
export class PropinasService {
  private http = inject(HttpClient);
  private base = environment.apiUrl;

  getPropinas(fechaInicio: string, fechaFin: string): Observable<ResumenPropinasDiario> {
    const params = new HttpParams()
      .set('fecha_inicio', fechaInicio)
      .set('fecha_fin', fechaFin);
    return this.http.get<ResumenPropinasDiario>(
      `${this.base}/reportes/propinas`, { params }
    );
  }
}