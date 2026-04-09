import { Component, signal, inject, OnInit } from '@angular/core';
import { CurrencyPipe } from '@angular/common';
import { ResumenPlatillosDiario } from '../../models/index';
import { Top5Service } from '../../services/top5';
import { JornadaService } from '../../services/jornada';

@Component({
  selector: 'app-top5',
  standalone: true,
  imports: [CurrencyPipe],
  templateUrl: './top5.html',
  styleUrl: './top5.scss'
})
export class Top5 implements OnInit {
  private top5Service = inject(Top5Service);
  private jornadaService = inject(JornadaService);

  fechaInicio = signal('2026-02-24');
  fechaFin = signal('2026-03-02');

  platillos = signal<ResumenPlatillosDiario[]>([]);

  cargando = signal(false);
  error = signal<string | null>(null);

  ngOnInit() {
    this.cargarTop5();
  }

  cargarTop5() {
    this.cargando.set(true);
    const jornada = this.jornadaService.jornadaActual();
    
    if (jornada && jornada.id) {
      this.cargarDatos(jornada.id);
    } else {
      this.cargarJornadaYTop5();
    }
  }

  cargarJornadaYTop5() {
    this.jornadaService.cargar().subscribe({
      next: (jornada) => {
        if (jornada && jornada.id) {
          this.cargarDatos(jornada.id);
        }
      },
      error: () => {
        this.error.set('Error al cargar la jornada');
        this.cargando.set(false);
      }
    });
  }

  cargarDatos(jornadaId: number) {
    this.top5Service.getTop5(jornadaId).subscribe({
      next: (data) => {
        this.platillos.set(data);
        this.cargando.set(false);
      },
      error: () => {
        this.error.set('Error al cargar los platillos');
        this.cargando.set(false);
      }
    });
  }

  setFechaInicio(val: string) { this.fechaInicio.set(val); }
  setFechaFin(val: string) { this.fechaFin.set(val); }
}