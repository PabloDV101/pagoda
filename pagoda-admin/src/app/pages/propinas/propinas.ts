import { Component, signal } from '@angular/core';
import { CurrencyPipe } from '@angular/common';
import { ResumenPropinasDiario } from '../../models/index';

@Component({
  selector: 'app-propinas',
  standalone: true,
  imports: [CurrencyPipe],
  templateUrl: './propinas.html',
  styleUrl: './propinas.scss'
})
export class Propinas {

  fechaInicio = signal('2026-02-24');
  fechaFin = signal('2026-03-02');

  resumen = signal<ResumenPropinasDiario>({
    propinas_efectivo: 850,
    propinas_tarjeta_bruto: 1060,
    propinas_tarjeta_neto: 1023,
    total_propinas_neto: 1910
  });

  setFechaInicio(val: string) { this.fechaInicio.set(val); }
  setFechaFin(val: string) { this.fechaFin.set(val); }
}