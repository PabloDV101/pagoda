import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'ventas',
    pathMatch: 'full'
  },
  {
    path: '',
    loadComponent: () =>
      import('./layout/shell/shell').then(m => m.ShellComponent),
    children: [
      {
        path: 'ventas',
        loadComponent: () =>
          import('./pages/ventas/ventas').then(m => m.Ventas)
      },
      {
        path: 'top5',
        loadComponent: () =>
          import('./pages/top5/top5').then(m => m.Top5)
      },
      {
        path: 'menu',
        loadComponent: () =>
          import('./pages/menu/menu').then(m => m.Menu)
      },
      {
        path: 'propinas',
        loadComponent: () =>
          import('./pages/propinas/propinas').then(m => m.Propinas)
      }
    ]
  },
  {
    path: '**',
    redirectTo: 'ventas'
  }
];