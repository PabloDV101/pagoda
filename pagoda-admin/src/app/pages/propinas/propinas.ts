import { Component, signal, inject, OnInit } from '@angular/core';
import { CurrencyPipe } from '@angular/common';
import { ResumenPropinasDiario } from '../../models/index';
import { PropinasService } from '../../services/propinas';
import { JornadaService } from '../../services/jornada';

@Component({
  selector: 'app-propinas',
  standalone: true,
  imports: [CurrencyPipe],
  templateUrl: './propinas.html',
  styleUrl: './propinas.scss'
})
export class Propinas implements OnInit {
  private propinasService = inject(PropinasService);
  private jornadaService = inject(JornadaService);

  fechaInicio = signal('2026-02-24');
  fechaFin = signal('2026-03-02');

  resumen = signal<ResumenPropinasDiario>({
    propinas_efectivo: 0,
    propinas_tarjeta_bruto: 0,
    propinas_tarjeta_neto: 0,
    total_propinas_neto: 0
  });

  cargando = signal(false);
  error = signal<string | null>(null);

  ngOnInit() {
    this.cargarPropinas();
  }

  cargarPropinas() {
    this.cargando.set(true);
    const jornada = this.jornadaService.jornadaActual();
    
    if (jornada && jornada.id) {
      this.propinasService.getPropinas(jornada.id).subscribe({
        next: data => {
          this.resumen.set(data);
          this.cargando.set(false);
        },
        error: (err) => {
          this.error.set('Error al cargar las propinas');
          this.cargando.set(false);
        }
      });
    } else {
      this.cargarJornadaYPropinas();
    }
  }

  cargarJornadaYPropinas() {
    this.jornadaService.cargar().subscribe({
      next: (jornada) => {
        if (jornada && jornada.id) {
          this.propinasService.getPropinas(jornada.id).subscribe({
            next: data => {
              this.resumen.set(data);
              this.cargando.set(false);
            },
            error: () => {
              this.error.set('Error al cargar las propinas');
              this.cargando.set(false);
            }
          });
        }
      },
      error: () => {
        this.error.set('Error al cargar la jornada');
        this.cargando.set(false);
      }
    });
  }

  setFechaInicio(val: string) { this.fechaInicio.set(val); }
  setFechaFin(val: string) { this.fechaFin.set(val); }
}