import { Component, signal, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UsuariosService, Usuario, CrearUsuarioDTO, ActualizarUsuarioDTO, Rol } from '../../services/usuarios';

@Component({
  selector: 'app-usuarios',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './usuarios.html',
  styleUrl: './usuarios.scss'
})
export class UsuariosComponent implements OnInit {
  private usuariosService = inject(UsuariosService);

  // Estado
  usuarios = signal<Usuario[]>([]);
  roles = signal<Rol[]>([]);
  cargando = signal(false);
  error = signal<string | null>(null);
  exito = signal<string | null>(null);
  activo = signal(true);

  // Modal
  mostrarModal = signal(false);
  editando = signal(false);
  usuarioEditID = signal<number | null>(null);

  // Formulario
  form = signal<CrearUsuarioDTO | ActualizarUsuarioDTO>({
    nombre: '',
    rolId: 0,
    pinHash: '',
    contrasena: ''
  });

  ngOnInit() {
    this.cargarUsuarios();
    this.cargarRoles();
  }

  cargarUsuarios() {
    this.cargando.set(true);
    this.usuariosService.listarUsuarios().subscribe({
      next: (data) => {
        this.usuarios.set(data);
        this.cargando.set(false);
      },
      error: () => {
        this.error.set('Error al cargar usuarios');
        this.cargando.set(false);
      }
    });
  }

  cargarRoles() {
    this.usuariosService.listarRoles().subscribe({
      next: (data) => {
        this.roles.set(data);
      },
      error: () => {
        this.error.set('Error al cargar roles');
      }
    });
  }

  abrirModal(usuario?: Usuario) {
    if (usuario) {
      this.editando.set(true);
      this.usuarioEditID.set(usuario.id);
      this.activo.set(usuario.activo);
      this.form.set({
        nombre: usuario.nombre,
        rolId: 0, // Necesitaríamos mapear el nombre del rol al ID
        pinHash: '',
        contrasena: ''
      });
    } else {
      this.editando.set(false);
      this.usuarioEditID.set(null);
      this.activo.set(true);
      this.form.set({
        nombre: '',
        rolId: 0,
        pinHash: '',
        contrasena: ''
      });
    }
    this.mostrarModal.set(true);
  }

  cerrarModal() {
    this.mostrarModal.set(false);
  }

  guardar() {
    const f = this.form();

    if (!f.nombre || f.nombre.trim() === '') {
      this.error.set('El nombre es requerido');
      return;
    }

    if (f.rolId === 0) {
      this.error.set('Debes seleccionar un rol');
      return;
    }

    if (!this.editando()) {
      if (!f.contrasena || f.contrasena.trim() === '') {
        this.error.set('La contraseña es requerida');
        return;
      }
    }

    this.cargando.set(true);
    this.error.set(null);

    const dto = {
      nombre: f.nombre.trim(),
      rolId: f.rolId,
      pinHash: f.pinHash || '',
      contrasena: f.contrasena || undefined,
      ...(this.editando() && { activo: this.activo() })
    };

    if (this.editando()) {
      this.usuariosService.actualizarUsuario(this.usuarioEditID()!, dto as ActualizarUsuarioDTO).subscribe({
        next: () => {
          this.exito.set('Usuario actualizado correctamente');
          this.cerrarModal();
          this.cargarUsuarios();
          setTimeout(() => this.exito.set(null), 3000);
        },
        error: () => {
          this.error.set('Error al actualizar usuario');
          this.cargando.set(false);
        }
      });
    } else {
      this.usuariosService.crearUsuario(dto as CrearUsuarioDTO).subscribe({
        next: () => {
          this.exito.set('Usuario creado correctamente');
          this.cerrarModal();
          this.cargarUsuarios();
          setTimeout(() => this.exito.set(null), 3000);
        },
        error: () => {
          this.error.set('Error al crear usuario');
          this.cargando.set(false);
        }
      });
    }
  }

  eliminar(id: number) {
    if (confirm('¿Estás seguro de que deseas eliminar este usuario?')) {
      this.cargando.set(true);
      this.usuariosService.eliminarUsuario(id).subscribe({
        next: () => {
          this.exito.set('Usuario eliminado correctamente');
          this.cargarUsuarios();
          setTimeout(() => this.exito.set(null), 3000);
        },
        error: () => {
          this.error.set('Error al eliminar usuario');
          this.cargando.set(false);
        }
      });
    }
  }

  limpiarMensajes() {
    this.error.set(null);
    this.exito.set(null);
  }
}
