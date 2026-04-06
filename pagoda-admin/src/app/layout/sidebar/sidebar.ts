import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';

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
  navItems: NavItem[] = [
    { label: 'Ventas',   route: '/ventas' },
    { label: 'Top 5',    route: '/top5' },
    { label: 'Menú',     route: '/menu' },
    { label: 'Propinas', route: '/propinas' }
  ];
}