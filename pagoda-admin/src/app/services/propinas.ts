import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { environment } from '../../environments/environment';
import { ApiResponse, ResumenPropinasDiario } from '../models/index';

@Injectable({ providedIn: 'root' })
export class PropinasService {
  private http = inject(HttpClient);
  private base = environment.apiUrl;

  getPropinas(jornadaId: number): Observable<ResumenPropinasDiario> {
    return this.http.get<ApiResponse<ResumenPropinasDiario[]>>(
      `${this.base}/reportes/propinas-diarias/jornada/${jornadaId}`
    ).pipe(
      map(r => {
        // El backend devuelve una lista, tomamos el primer elemento
        return Array.isArray(r.data) && r.data.length > 0 
          ? r.data[0] 
          : {
              propinas_efectivo: 0,
              propinas_tarjeta_bruto: 0,
              propinas_tarjeta_neto: 0,
              total_propinas_neto: 0
            };
      })
    );
  }
}