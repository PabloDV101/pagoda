import { Component, signal, inject, OnInit } from '@angular/core';
import { CurrencyPipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Categoria } from '../../models/index';
import { CategoriasService } from '../../services/categorias';

@Component({
  selector: 'app-categorias',
  standalone: true,
  imports: [CurrencyPipe, FormsModule],
  templateUrl: './categorias.html',
  styleUrl: './categorias.scss'
})
export class Categorias implements OnInit {
  private categoriasService = inject(CategoriasService);

  categorias = signal<Categoria[]>([]);
  cargando = signal(false);
  error = signal<string | null>(null);

  // Modal
  modalAbierto = signal(false);
  modoEdicion = signal(false);
  categoriaEditandoId = signal<number | null>(null);

  form = signal<Partial<Categoria>>({
    nombre: '',
    descripcion: ''
  });

  // Validaciones
  erroresValidacion = signal<{ [key: string]: string }>({});
  guardando = signal(false);

  ngOnInit() {
    this.cargarCategorias();
  }

  cargarCategorias() {
    this.cargando.set(true);
    this.categoriasService.getCategorias().subscribe({
      next: data => {
        this.categorias.set(data);
        this.cargando.set(false);
      },
      error: () => {
        this.error.set('Error al cargar categorías');
        this.cargando.set(false);
      }
    });
  }

  abrirModalNuevo() {
    this.form.set({ nombre: '', descripcion: '' });
    this.modoEdicion.set(false);
    this.categoriaEditandoId.set(null);
    this.erroresValidacion.set({});
    this.error.set(null);
    this.modalAbierto.set(true);
  }

  abrirModalEditar(categoria: Categoria) {
    this.form.set({
      nombre: categoria.nombre,
      descripcion: categoria.descripcion ?? ''
    });
    this.modoEdicion.set(true);
    this.categoriaEditandoId.set(categoria.id);
    this.erroresValidacion.set({});
    this.error.set(null);
    this.modalAbierto.set(true);
  }

  cerrarModal() {
    this.modalAbierto.set(false);
  }

  validar(): boolean {
    const errores: { [key: string]: string } = {};
    const f = this.form();

    // Validar nombre
    if (!f.nombre || f.nombre.trim() === '') {
      errores['nombre'] = 'El nombre es requerido';
    } else if (f.nombre.trim().length < 3) {
      errores['nombre'] = 'El nombre debe tener al menos 3 caracteres';
    } else if (f.nombre.length > 50) {
      errores['nombre'] = 'El nombre no puede exceder 50 caracteres';
    }

    // Validar descripción (opcional, pero máximo)
    if (f.descripcion && f.descripcion.length > 500) {
      errores['descripcion'] = 'La descripción no puede exceder 500 caracteres';
    }

    this.erroresValidacion.set(errores);
    return Object.keys(errores).length === 0;
  }

  esFormularioValido(): boolean {
    const f = this.form();
    return Object.keys(this.erroresValidacion()).length === 0 && 
           !!(f.nombre && f.nombre.trim());
  }

  guardar() {
    if (!this.validar()) {
      this.error.set('Por favor completa correctamente el formulario');
      return;
    }

    this.guardando.set(true);
    const f = this.form() as Categoria;

    if (this.modoEdicion() && this.categoriaEditandoId() !== null) {
      this.categoriasService.actualizarCategoria(this.categoriaEditandoId()!, f).subscribe({
        next: () => {
          this.cargarCategorias();
          this.cerrarModal();
          this.error.set(null);
          this.guardando.set(false);
        },
        error: (err) => {
          this.error.set('Error al actualizar categoría. Intenta nuevamente.');
          this.guardando.set(false);
        }
      });
    } else {
      this.categoriasService.crearCategoria(f).subscribe({
        next: () => {
          this.cargarCategorias();
          this.cerrarModal();
          this.error.set(null);
          this.guardando.set(false);
        },
        error: (err) => {
          this.error.set('Error al crear categoría. Intenta nuevamente.');
          this.guardando.set(false);
        }
      });
    }
  }

  eliminar(id: number) {
    if (confirm('¿Estás seguro de que quieres eliminar esta categoría?')) {
      this.categoriasService.eliminarCategoria(id).subscribe({
        next: () => this.cargarCategorias(),
        error: () => this.error.set('Error al eliminar categoría')
      });
    }
  }

  setForm(campo: keyof Categoria, valor: any) {
    this.form.update(f => ({ ...f, [campo]: valor }));
  }
}
