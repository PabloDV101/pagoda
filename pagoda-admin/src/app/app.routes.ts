import { Routes } from '@angular/router';
import { authGuard } from './guards/auth.guard';

export const routes: Routes = [
  {
    path: 'login',
    loadComponent: () =>
      import('./pages/login/login').then(m => m.LoginComponent)
  },
  {
    path: '',
    redirectTo: 'ventas',
    pathMatch: 'full'
  },
  {
    path: '',
    loadComponent: () =>
      import('./layout/shell/shell').then(m => m.ShellComponent),
    canActivate: [authGuard],
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
      },
      {
        path: 'configuracion',
        loadComponent: () =>
          import('./pages/configuracion/configuracion').then(m => m.ConfiguracionComponent)
      },
      {
        path: 'usuarios',
        loadComponent: () =>
          import('./pages/usuarios/usuarios').then(m => m.UsuariosComponent)
      }
    ]
  },
  {
    path: '**',
    redirectTo: 'ventas'
  }
];