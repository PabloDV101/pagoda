import { Component, signal } from '@angular/core';
import { CurrencyPipe } from '@angular/common';
import { ResumenPlatillosDiario } from '../../models/index';

@Component({
  selector: 'app-top5',
  standalone: true,
  imports: [CurrencyPipe],
  templateUrl: './top5.html',
  styleUrl: './top5.scss'
})
export class Top5 {

  fechaInicio = signal('2026-02-24');
  fechaFin = signal('2026-03-02');

  platillos = signal<ResumenPlatillosDiario[]>([
    { producto_id: 1, cantidad_vendida: 68, total_generado: 9860, posicion: 1, producto: { id: 1, nombre: 'Pad Thai', precio: 145, categoria_id: 1, activo: true, fecha_creacion: '', categoria: { id: 1, nombre: 'Platos Principales' } } },
    { producto_id: 2, cantidad_vendida: 38, total_generado: 6270, posicion: 2, producto: { id: 2, nombre: 'Ramen Tonkotsu', precio: 165, categoria_id: 2, activo: true, fecha_creacion: '', categoria: { id: 2, nombre: 'Ramen' } } },
    { producto_id: 3, cantidad_vendida: 52, total_generado: 6240, posicion: 3, producto: { id: 3, nombre: 'California Roll', precio: 120, categoria_id: 3, activo: true, fecha_creacion: '', categoria: { id: 3, nombre: 'Rolls' } } },
    { producto_id: 4, cantidad_vendida: 45, total_generado: 5625, posicion: 4, producto: { id: 4, nombre: 'Tom Yum Goong', precio: 125, categoria_id: 4, activo: true, fecha_creacion: '', categoria: { id: 4, nombre: 'Sopas' } } },
    { producto_id: 5, cantidad_vendida: 34, total_generado: 5270, posicion: 5, producto: { id: 5, nombre: 'Green Curry', precio: 155, categoria_id: 1, activo: true, fecha_creacion: '', categoria: { id: 1, nombre: 'Platos Principales' } } },
  ]);

  setFechaInicio(val: string) { this.fechaInicio.set(val); }
  setFechaFin(val: string) { this.fechaFin.set(val); }
}