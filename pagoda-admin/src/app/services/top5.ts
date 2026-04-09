import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { environment } from '../../environments/environment';
import { ApiResponse, ResumenPlatillosDiario } from '../models/index';

@Injectable({ providedIn: 'root' })
export class Top5Service {
  private http = inject(HttpClient);
  private base = environment.apiUrl;

  getTop5(jornadaId: number): Observable<ResumenPlatillosDiario[]> {
    return this.http.get<ApiResponse<ResumenPlatillosDiario[]>>(
      `${this.base}/reportes/platillos-diarios/jornada/${jornadaId}`
    ).pipe(map(r => r.data));
  }
}