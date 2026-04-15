import { Component, inject } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { AuthService } from '../../services/auth';

interface NavItem {
  label: string;
  route: string;
}

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './sidebar.html',
  styleUrl: './sidebar.scss'
})
export class SidebarComponent {
  private authService = inject(AuthService);
  usuario = this.authService.usuario;

  navItems: NavItem[] = [
    { label: 'Ventas',      route: '/ventas' },
    { label: 'Top 5',       route: '/top5' },
    { label: 'Menú',        route: '/menu' },
    { label: 'Propinas',    route: '/propinas' },
    { label: '👥 Usuarios',   route: '/usuarios' },
    { label: '⚙️ Configuración', route: '/configuracion' }
  ];

  logout() {
    this.authService.logout();
  }
}