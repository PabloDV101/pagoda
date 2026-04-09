import { Component, signal, inject, OnInit, computed } from '@angular/core';
import { CurrencyPipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Producto, Categoria, CrearProductoDTO } from '../../models/index';
import { MenuService } from '../../services/menu';
import { CategoriasService } from '../../services/categorias';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs';
import { environment } from '../../../environments/environment';
import { ApiResponse } from '../../models/index';

@Component({
  selector: 'app-menu',
  standalone: true,
  imports: [CurrencyPipe, FormsModule],
  templateUrl: './menu.html',
  styleUrl: './menu.scss'
})
export class Menu implements OnInit {
  private menuService = inject(MenuService);
  private categoriasService = inject(CategoriasService);
  private http = inject(HttpClient);
  private base = environment.apiUrl;

  // Tabs
  pestanaActiva = signal<'productos' | 'categorias'>('productos');

  // Productos
  categorias = signal<Categoria[]>([]);
  productos = signal<Producto[]>([]);
  cargando = signal(false);
  error = signal<string | null>(null);

  // Filtros de productos
  busquedaNombre = signal<string>('');
  filtroCategoria = signal<number>(0);
  ordenamiento = signal<'nombre-asc' | 'nombre-desc' | 'precio-asc' | 'precio-desc'>('nombre-asc');

  // Productos filtrados y ordenados
  productosFiltrads = computed(() => {
    let resultado = [...this.productos()];
    
    // Filtrar por nombre
    if (this.busquedaNombre()) {
      resultado = resultado.filter(p => 
        p.nombre.toLowerCase().includes(this.busquedaNombre().toLowerCase())
      );
    }

    // Filtrar por categoría
    if (this.filtroCategoria() > 0) {
      resultado = resultado.filter(p => p.categoria?.id === this.filtroCategoria());
    }

    // Ordenar
    const orden = this.ordenamiento();
    if (orden === 'nombre-asc') {
      resultado.sort((a, b) => a.nombre.localeCompare(b.nombre));
    } else if (orden === 'nombre-desc') {
      resultado.sort((a, b) => b.nombre.localeCompare(a.nombre));
    } else if (orden === 'precio-asc') {
      resultado.sort((a, b) => a.precio - b.precio);
    } else if (orden === 'precio-desc') {
      resultado.sort((a, b) => b.precio - a.precio);
    }

    return resultado;
  });

  // Modal Productos
  modalProductoAbierto = signal(false);
  modoEdicionProducto = signal(false);
  productoEditandoId = signal<number | null>(null);
  formProducto = signal<CrearProductoDTO>({
    nombre: '',
    descripcion: '',
    precio: 0,
    categoria_id: 0
  });
  erroresProducto = signal<{ [key: string]: string }>({});
  guardandoProducto = signal(false);

  // Modal Categorías
  modalCategoriaAbierto = signal(false);
  modoEdicionCategoria = signal(false);
  categoriaEditandoId = signal<number | null>(null);
  formCategoria = signal<Partial<Categoria>>({
    nombre: '',
    descripcion: ''
  });
  erroresCategoria = signal<{ [key: string]: string }>({});
  guardandoCategoria = signal(false);

  ngOnInit() {
    this.cargarCategorias();
    this.cargarProductos();
  }

  // === CATEGORÍAS ===

  cargarCategorias() {
    this.categoriasService.getCategorias().subscribe({
      next: data => this.categorias.set(data),
      error: () => this.error.set('Error al cargar categorías')
    });
  }

  abrirModalNuevaCategoria() {
    this.formCategoria.set({ nombre: '', descripcion: '' });
    this.modoEdicionCategoria.set(false);
    this.categoriaEditandoId.set(null);
    this.erroresCategoria.set({});
    this.error.set(null);
    this.modalCategoriaAbierto.set(true);
  }

  abrirModalEditarCategoria(categoria: Categoria) {
    this.formCategoria.set({
      nombre: categoria.nombre,
      descripcion: categoria.descripcion ?? ''
    });
    this.modoEdicionCategoria.set(true);
    this.categoriaEditandoId.set(categoria.id);
    this.erroresCategoria.set({});
    this.error.set(null);
    this.modalCategoriaAbierto.set(true);
  }

  cerrarModalCategoria() {
    this.modalCategoriaAbierto.set(false);
  }

  validarCategoria(): boolean {
    const errores: { [key: string]: string } = {};
    const f = this.formCategoria();

    if (!f.nombre || f.nombre.trim() === '') {
      errores['nombre'] = 'El nombre es requerido';
    } else if (f.nombre.trim().length < 3) {
      errores['nombre'] = 'El nombre debe tener al menos 3 caracteres';
    } else if (f.nombre.length > 50) {
      errores['nombre'] = 'El nombre no puede exceder 50 caracteres';
    }

    if (f.descripcion && f.descripcion.length > 500) {
      errores['descripcion'] = 'La descripción no puede exceder 500 caracteres';
    }

    this.erroresCategoria.set(errores);
    return Object.keys(errores).length === 0;
  }

  esFormularioCategoriaValido(): boolean {
    const f = this.formCategoria();
    return Object.keys(this.erroresCategoria()).length === 0 && 
           !!(f.nombre && f.nombre.trim());
  }

  guardarCategoria() {
    if (!this.validarCategoria()) {
      this.error.set('Por favor completa correctamente el formulario');
      return;
    }

    this.guardandoCategoria.set(true);
    const f = this.formCategoria() as Categoria;

    if (this.modoEdicionCategoria() && this.categoriaEditandoId() !== null) {
      this.categoriasService.actualizarCategoria(this.categoriaEditandoId()!, f).subscribe({
        next: () => {
          this.cargarCategorias();
          this.cerrarModalCategoria();
          this.error.set(null);
          this.guardandoCategoria.set(false);
        },
        error: () => {
          this.error.set('Error al actualizar categoría. Intenta nuevamente.');
          this.guardandoCategoria.set(false);
        }
      });
    } else {
      this.categoriasService.crearCategoria(f).subscribe({
        next: () => {
          this.cargarCategorias();
          this.cerrarModalCategoria();
          this.error.set(null);
          this.guardandoCategoria.set(false);
        },
        error: () => {
          this.error.set('Error al crear categoría. Intenta nuevamente.');
          this.guardandoCategoria.set(false);
        }
      });
    }
  }

  eliminarCategoria(id: number) {
    if (confirm('¿Estás seguro de que quieres eliminar esta categoría?')) {
      this.categoriasService.eliminarCategoria(id).subscribe({
        next: () => this.cargarCategorias(),
        error: () => this.error.set('Error al eliminar categoría')
      });
    }
  }

  setFormCategoria(campo: keyof Categoria, valor: any) {
    this.formCategoria.update(f => ({ ...f, [campo]: valor }));
  }

  // === PRODUCTOS ===

  cargarProductos() {
    this.cargando.set(true);
    this.menuService.getProductos().subscribe({
      next: data => {
        this.productos.set(data);
        this.cargando.set(false);
      },
      error: () => {
        this.error.set('Error al cargar productos');
        this.cargando.set(false);
      }
    });
  }

  abrirModalNuevoProducto() {
    this.formProducto.set({ nombre: '', descripcion: '', precio: 0, categoria_id: 0 });
    this.modoEdicionProducto.set(false);
    this.productoEditandoId.set(null);
    this.erroresProducto.set({});
    this.error.set(null);
    this.modalProductoAbierto.set(true);
  }

  abrirModalEditarProducto(producto: Producto) {
    this.formProducto.set({
      nombre: producto.nombre,
      descripcion: producto.descripcion ?? '',
      precio: producto.precio,
      categoria_id: producto.categoria?.id ?? 0
    });
    this.modoEdicionProducto.set(true);
    this.productoEditandoId.set(producto.id);
    this.erroresProducto.set({});
    this.error.set(null);
    this.modalProductoAbierto.set(true);
  }

  cerrarModalProducto() {
    this.modalProductoAbierto.set(false);
  }

  validarProducto(): boolean {
    const errores: { [key: string]: string } = {};
    const f = this.formProducto();

    if (!f.nombre || f.nombre.trim() === '') {
      errores['nombre'] = 'El nombre es requerido';
    } else if (f.nombre.trim().length < 3) {
      errores['nombre'] = 'El nombre debe tener al menos 3 caracteres';
    } else if (f.nombre.length > 100) {
      errores['nombre'] = 'El nombre no puede exceder 100 caracteres';
    }

    if (!f.precio || f.precio <= 0) {
      errores['precio'] = 'El precio debe ser mayor a 0';
    } else if (f.precio > 99999.99) {
      errores['precio'] = 'El precio es demasiado alto';
    }

    if (!f.categoria_id || f.categoria_id === 0) {
      errores['categoria_id'] = 'Debes seleccionar una categoría';
    }

    if (f.descripcion && f.descripcion.length > 500) {
      errores['descripcion'] = 'La descripción no puede exceder 500 caracteres';
    }

    this.erroresProducto.set(errores);
    return Object.keys(errores).length === 0;
  }

  esFormularioProductoValido(): boolean {
    const f = this.formProducto();
    return Object.keys(this.erroresProducto()).length === 0 && 
           !!(f.nombre && f.nombre.trim()) && 
           (f.precio ?? 0) > 0 && 
           (f.categoria_id ?? 0) > 0;
  }

  guardarProducto() {
    if (!this.validarProducto()) {
      this.error.set('Por favor completa correctamente el formulario');
      return;
    }

    this.guardandoProducto.set(true);
    const f = this.formProducto();

    if (this.modoEdicionProducto() && this.productoEditandoId() !== null) {
      this.menuService.actualizarProducto(this.productoEditandoId()!, f).subscribe({
        next: () => {
          this.cargarProductos();
          this.cerrarModalProducto();
          this.error.set(null);
          this.guardandoProducto.set(false);
        },
        error: () => {
          this.error.set('Error al actualizar producto. Intenta nuevamente.');
          this.guardandoProducto.set(false);
        }
      });
    } else {
      this.menuService.crearProducto(f).subscribe({
        next: () => {
          this.cargarProductos();
          this.cerrarModalProducto();
          this.error.set(null);
          this.guardandoProducto.set(false);
        },
        error: () => {
          this.error.set('Error al crear producto. Intenta nuevamente.');
          this.guardandoProducto.set(false);
        }
      });
    }
  }

  eliminarProducto(id: number) {
    this.menuService.eliminarProducto(id).subscribe({
      next: () => this.cargarProductos(),
      error: () => this.error.set('Error al eliminar producto')
    });
  }

  setFormProducto(campo: keyof CrearProductoDTO, valor: any) {
    if (campo === 'precio') {
      valor = valor ? parseFloat(valor) : 0;
    }
    if (campo === 'categoria_id') {
      valor = valor ? parseInt(valor) : 0;
    }
    this.formProducto.update(f => ({ ...f, [campo]: valor }));
  }

  // === FILTROS ===

  setFiltroCategoria(valor: any) {
    const numValor = valor ? parseInt(valor, 10) : 0;
    this.filtroCategoria.set(numValor);
  }

  limpiarFiltros() {
    this.busquedaNombre.set('');
    this.filtroCategoria.set(0);
    this.ordenamiento.set('nombre-asc');
  }
}