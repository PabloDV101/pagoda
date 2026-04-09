import { Component, signal, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AdministradorService, ActualizarAdministradorDTO, Administrador } from '../../services/administrador';

@Component({
  selector: 'app-configuracion',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './configuracion.html',
  styleUrl: './configuracion.scss'
})
export class ConfiguracionComponent implements OnInit {
  private administradorService = inject(AdministradorService);

  // Estado
  cargando = signal(false);
  error = signal<string | null>(null);
  exito = signal<string | null>(null);
  admin = signal<Administrador | null>(null);

  // Formulario
  formActualizar = signal<ActualizarAdministradorDTO>({
    usuario: '',
    contrasenaActual: '',
    contrasenaNueva: ''
  });
  erroresForm = signal<{ [key: string]: string }>({});
  guardando = signal(false);

  ngOnInit() {
    this.cargarPerfil();
  }

  cargarPerfil() {
    this.cargando.set(true);
    this.administradorService.obtenerPerfil().subscribe({
      next: (response) => {
        this.admin.set(response.data);
        this.formActualizar.update(f => ({ ...f, usuario: response.data.usuario }));
        this.cargando.set(false);
      },
      error: () => {
        this.error.set('Error al cargar el perfil del administrador');
        this.cargando.set(false);
      }
    });
  }

  validarFormulario(): boolean {
    const errores: { [key: string]: string } = {};
    const f = this.formActualizar();

    if (!f.usuario || f.usuario.trim() === '') {
      errores['usuario'] = 'El nombre de usuario es requerido';
    } else if (f.usuario.length < 3) {
      errores['usuario'] = 'El usuario debe tener al menos 3 caracteres';
    } else if (f.usuario.length > 50) {
      errores['usuario'] = 'El usuario no puede exceder 50 caracteres';
    }

    if (!f.contrasenaActual || f.contrasenaActual === '') {
      errores['contrasenaActual'] = 'Debes ingresar tu contraseña actual para hacer cambios';
    }

    if (f.contrasenaNueva && f.contrasenaNueva.length > 0) {
      if (f.contrasenaNueva.length < 6) {
        errores['contrasenaNueva'] = 'La nueva contraseña debe tener al menos 6 caracteres';
      } else if (f.contrasenaNueva === f.contrasenaActual) {
        errores['contrasenaNueva'] = 'La nueva contraseña debe ser diferente a la actual';
      }
    }

    this.erroresForm.set(errores);
    return Object.keys(errores).length === 0;
  }

  guardar() {
    this.error.set(null);
    this.exito.set(null);

    if (!this.validarFormulario()) {
      this.error.set('Por favor completa correctamente el formulario');
      return;
    }

    this.guardando.set(true);
    const dto = this.formActualizar();

    this.administradorService.actualizar(dto).subscribe({
      next: (response) => {
        this.admin.set(response.data);
        this.exito.set('Datos actualizados correctamente');
        // Limpiar formulario
        this.formActualizar.set({
          usuario: response.data.usuario,
          contrasenaActual: '',
          contrasenaNueva: ''
        });
        this.guardando.set(false);
      },
      error: (err) => {
        const mensajeError = err.error?.message || 'Error al actualizar los datos';
        this.error.set(mensajeError);
        this.guardando.set(false);
      }
    });
  }

  setFormField(campo: keyof ActualizarAdministradorDTO, valor: any) {
    this.formActualizar.update(f => ({ ...f, [campo]: valor }));
    // Limpiar error del campo específico cuando el usuario empieza a escribir
    if (this.erroresForm()[campo]) {
      this.erroresForm.update(e => {
        const newErrors = { ...e };
        delete newErrors[campo];
        return newErrors;
      });
    }
  }

  limpiarMensajes() {
    this.error.set(null);
    this.exito.set(null);
  }
}
