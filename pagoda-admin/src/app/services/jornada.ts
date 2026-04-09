import { Injectable, inject, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map, tap } from 'rxjs';
import { environment } from '../../environments/environment';
import { ApiResponse } from '../models/index';

@Injectable({ providedIn: 'root' })
export class JornadaService {
  private http = inject(HttpClient);
  private base = environment.apiUrl;

  jornadaActual = signal<any>(null);

  cargar() {
    return this.http.get<ApiResponse<any>>(
      `${this.base}/operacion/jornadas/estado`
    ).pipe(
      map(r => r.data),
      tap(j => this.jornadaActual.set(j))
    );
  }
}