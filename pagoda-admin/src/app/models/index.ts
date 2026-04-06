// ── Catálogos ──────────────────────────────────────────
export interface Rol {
  id: number;
  nombre: string;
}

export interface Categoria {
  id: number;
  nombre: string;
  descripcion?: string;
}

export interface MetodoPago {
  id: number;
  nombre: 'EFECTIVO' | 'TARJETA';
}

export interface TipoCobro {
  id: number;
  nombre: 'TOTAL' | 'EQUITATIVO' | 'POR_PERSONA';
}

// ── Operación ──────────────────────────────────────────
export interface Producto {
  id: number;
  nombre: string;
  descripcion?: string;
  precio: number;
  categoria_id: number;
  categoria?: Categoria;     // join opcional
  activo: boolean;
  fecha_creacion: string;
}

export interface Mesa {
  id: number;
  numero: number;
  capacidad: number;
  estado_id: number;
}

// ── Ventas ─────────────────────────────────────────────
export interface ItemVenta {
  id: number;
  venta_id: number;
  producto_id: number;
  producto?: Producto;
  numero_comensal: number;
  precio_unitario: number;
  cantidad: number;
  notas?: string;
  estado_item_id: number;
}

export interface Pago {
  id: number;
  venta_id: number;
  numero_comensal?: number;
  metodo_pago_id: number;
  metodo_pago?: MetodoPago;
  monto: number;
  comision_porcentaje: number;
  monto_neto: number;
  propina_monto: number;
  propina_metodo_pago_id?: number;
  propina_neto: number;
}

export interface Venta {
  id: number;
  mesa_id: number;
  mesa?: Mesa;
  usuario_id: number;
  jornada_id: number;
  num_comensales: number;
  tipo_cobro_id: number;
  total_cuenta: number;
  comision_porcentaje: number;
  fecha_creacion: string;
  fecha_cierre?: string;
  items?: ItemVenta[];
  pagos?: Pago[];
}

// ── Reportes ───────────────────────────────────────────
export interface ResumenVentasDiario {
  jornada_id: number;
  total_ventas: number;
  total_efectivo: number;
  total_tarjeta_bruto: number;
  total_tarjeta_neto: number;
  total_transacciones: number;
  fondo_caja: number;
}

export interface ResumenPlatillosDiario {
  producto_id: number;
  producto?: Producto;
  cantidad_vendida: number;
  total_generado: number;
  posicion?: number;        // calculado en frontend
}

export interface ResumenPropinasDiario {
  propinas_efectivo: number;
  propinas_tarjeta_bruto: number;
  propinas_tarjeta_neto: number;
  total_propinas_neto: number;
}

// ── Filtros / DTOs ─────────────────────────────────────
export type PeriodoVentas = 'diario' | 'semanal' | 'mensual';

export interface FiltroFecha {
  fecha_inicio: string;   // ISO: 'YYYY-MM-DD'
  fecha_fin: string;
}

export interface FiltroVentas {
  periodo: PeriodoVentas;
}

// ── Menú CRUD ──────────────────────────────────────────
export interface CrearProductoDTO {
  nombre: string;
  descripcion?: string;
  precio: number;
  categoria_id: number;
}

export interface ActualizarProductoDTO extends Partial<CrearProductoDTO> {}
