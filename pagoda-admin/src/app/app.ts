import { Component, signal, inject, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { JornadaService } from './services/jornada';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App implements OnInit {
  protected readonly title = signal('pagoda-admin');
  private jornadaService = inject(JornadaService);

  ngOnInit() {
    // Intentar cargar la jornada, pero no es obligatorio
    this.jornadaService.cargar().subscribe({
      next: (jornada) => {
        console.log('Jornada actual cargada:', jornada);
      },
      error: () => {
        // Silenciosamente ignorar si no hay jornada abierta
        // La app seguirá funcionando normalmente
      }
    });
  }
}

