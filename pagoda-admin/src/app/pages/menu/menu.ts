import { Component, signal } from '@angular/core';
import { CurrencyPipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Producto, Categoria, CrearProductoDTO } from '../../models/index';

@Component({
  selector: 'app-menu',
  standalone: true,
  imports: [CurrencyPipe, FormsModule],
  templateUrl: './menu.html',
  styleUrl: './menu.scss'
})
export class Menu {

  categorias = signal<Categoria[]>([
    { id: 1, nombre: 'Platos Principales' },
    { id: 2, nombre: 'Entradas' },
    { id: 3, nombre: 'Sopas' },
    { id: 4, nombre: 'Postres' },
    { id: 5, nombre: 'Bebidas' },
    { id: 6, nombre: 'Ensaladas' },
    { id: 7, nombre: 'Rolls' },
    { id: 8, nombre: 'Ramen' },
  ]);

  productos = signal<Producto[]>([
    { id: 1, nombre: 'Pad Thai', precio: 145, categoria_id: 1, activo: true, fecha_creacion: '', categoria: { id: 1, nombre: 'Platos Principales' } },
    { id: 2, nombre: 'Tom Yum Goong', precio: 125, categoria_id: 3, activo: true, fecha_creacion: '', categoria: { id: 3, nombre: 'Sopas' } },
    { id: 3, nombre: 'Spring Rolls', precio: 85, categoria_id: 2, activo: true, fecha_creacion: '', categoria: { id: 2, nombre: 'Entradas' } },
    { id: 4, nombre: 'Green Curry', precio: 155, categoria_id: 1, activo: true, fecha_creacion: '', categoria: { id: 1, nombre: 'Platos Principales' } },
    { id: 5, nombre: 'Mango Sticky Rice', precio: 75, categoria_id: 4, activo: true, fecha_creacion: '', categoria: { id: 4, nombre: 'Postres' } },
    { id: 6, nombre: 'Thai Iced Tea', precio: 45, categoria_id: 5, activo: true, fecha_creacion: '', categoria: { id: 5, nombre: 'Bebidas' } },
    { id: 7, nombre: 'Papaya Salad', precio: 95, categoria_id: 6, activo: true, fecha_creacion: '', categoria: { id: 6, nombre: 'Ensaladas' } },
    { id: 8, nombre: 'Massaman Curry', precio: 165, categoria_id: 1, activo: true, fecha_creacion: '', categoria: { id: 1, nombre: 'Platos Principales' } },
    { id: 9, nombre: 'Satay de Pollo', precio: 110, categoria_id: 2, activo: true, fecha_creacion: '', categoria: { id: 2, nombre: 'Entradas' } },
    { id: 10, nombre: 'Coconut Ice Cream', precio: 65, categoria_id: 4, activo: true, fecha_creacion: '', categoria: { id: 4, nombre: 'Postres' } },
    { id: 11, nombre: 'California Roll', precio: 120, categoria_id: 7, activo: true, fecha_creacion: '', categoria: { id: 7, nombre: 'Rolls' } },
  ]);

  // Modal
  modalAbierto = signal(false);
  modoEdicion = signal(false);
  productoEditandoId = signal<number | null>(null);

  form = signal<CrearProductoDTO>({
    nombre: '',
    descripcion: '',
    precio: 0,
    categoria_id: 0
  });

  abrirModalNuevo() {
    this.form.set({ nombre: '', descripcion: '', precio: 0, categoria_id: 0 });
    this.modoEdicion.set(false);
    this.productoEditandoId.set(null);
    this.modalAbierto.set(true);
  }

  abrirModalEditar(producto: Producto) {
    this.form.set({
      nombre: producto.nombre,
      descripcion: producto.descripcion ?? '',
      precio: producto.precio,
      categoria_id: producto.categoria_id
    });
    this.modoEdicion.set(true);
    this.productoEditandoId.set(producto.id);
    this.modalAbierto.set(true);
  }

  cerrarModal() {
    this.modalAbierto.set(false);
  }

  guardar() {
    const f = this.form();
    if (!f.nombre || !f.precio || !f.categoria_id) return;

    const categoria = this.categorias().find(c => c.id === +f.categoria_id);

    if (this.modoEdicion() && this.productoEditandoId() !== null) {
      this.productos.update(lista =>
        lista.map(p => p.id === this.productoEditandoId()
          ? { ...p, ...f, categoria_id: +f.categoria_id, categoria }
          : p
        )
      );
    } else {
      const nuevo: Producto = {
        id: Date.now(),
        nombre: f.nombre,
        descripcion: f.descripcion,
        precio: +f.precio,
        categoria_id: +f.categoria_id,
        categoria,
        activo: true,
        fecha_creacion: new Date().toISOString()
      };
      this.productos.update(lista => [...lista, nuevo]);
    }
    this.cerrarModal();
  }

  eliminar(id: number) {
    this.productos.update(lista => lista.filter(p => p.id !== id));
  }

  setForm(campo: keyof CrearProductoDTO, valor: string) {
    this.form.update(f => ({ ...f, [campo]: valor }));
  }
}