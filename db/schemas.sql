--Schemas for pagoda data base
CREATE SCHEMA IF NOT EXISTS catalogos;
CREATE SCHEMA IF NOT EXISTS operacion;
CREATE SCHEMA IF NOT EXISTS ventas;
CREATE SCHEMA IF NOT EXISTS reportes;

--Catalogos
CREATE TABLE catalogos.categorias (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT
);

CREATE TABLE catalogos.roles (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT
);

CREATE TABLE catalogos.estados_mesa (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL
);

CREATE TABLE catalogos.metodos_pago (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL
);

CREATE TABLE catalogos.tipos_cobro (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL
);

CREATE TABLE catalogos.estados_item (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL
);

--Operacion
CREATE TABLE operacion.usuarios (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    rol_id INT REFERENCES catalogos.roles(id),
    pin_hash VARCHAR(255) NOT NULL,
    activo BOOLEAN DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE operacion.productos (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(10, 2) NOT NULL,
    categoria_id INT REFERENCES catalogos.categorias(id),
    activo BOOLEAN DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE operacion.mesas (
    id SERIAL PRIMARY KEY,
    numero INT NOT NULL,
    capacidad INT NOT NULL,
    estado_id INT REFERENCES catalogos.estados_mesa(id)
);

CREATE TABLE operacion.jornadas (
    id SERIAL PRIMARY KEY,
    fecha DATE NOT NULL,
    fondo_caja DECIMAL(10, 2) NOT NULL,
    hora_apertura TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    hora_cierre TIMESTAMP,
    tipo_cierre VARCHAR(50),
    realizada BOOLEAN DEFAULT FALSE,
    usuario_apertura_id INT REFERENCES operacion.usuarios(id),
    usuario_cierre_id INT REFERENCES operacion.usuarios(id)
);
CREATE TABLE operacion.cierre_dia (
    id SERIAL PRIMARY KEY,
    fecha DATE NOT NULL,
    saldo_inicial DECIMAL(10, 2),
    ventas DECIMAL(10, 2),
    saldo_final DECIMAL(10, 2),
    num_pedidos_desechados INT,
    volumen_ventas_cantidad INT,
    volumen_ventas_efectivo DECIMAL(10, 2),
    volumen_ventas_tarjeta DECIMAL(10, 2),
    fecha_generacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
--ventas

CREATE TABLE ventas.ventas (
    id SERIAL PRIMARY KEY,
    mesa_id INT REFERENCES operacion.mesas(id),
    usuario_id INT REFERENCES operacion.usuarios(id),
    jornada_id INT REFERENCES operacion.jornadas(id),
    num_comensales INT NOT NULL,
    tipo_cobro_id INT REFERENCES catalogos.tipos_cobro(id),
    total_cuenta DECIMAL(10, 2) DEFAULT 0.00,
    comision_porcentaje DECIMAL(5, 2),
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_cierre TIMESTAMP
);

CREATE TABLE ventas.items_venta (
    id SERIAL PRIMARY KEY,
    venta_id INT REFERENCES ventas.ventas(id),
    producto_id INT REFERENCES operacion.productos(id),
    numero_comensal INT,
    precio_unitario DECIMAL(10, 2) NOT NULL,
    cantidad INT NOT NULL,
    notas TEXT,
    estado_item_id INT REFERENCES catalogos.estados_item(id)
);

CREATE TABLE ventas.pagos (
    id SERIAL PRIMARY KEY,
    venta_id INT REFERENCES ventas.ventas(id),
    numero_comensal INT,
    metodo_pago_id INT REFERENCES catalogos.metodos_pago(id),
    monto DECIMAL(10, 2) NOT NULL,
    comision_porcentaje DECIMAL(5, 2),
    monto_neto DECIMAL(10, 2),
    propina_monto DECIMAL(10, 2) DEFAULT 0,
    propina_metodo_pago_id INT REFERENCES catalogos.metodos_pago(id),
    propina_neta DECIMAL(10, 2)
);

--reportes

CREATE TABLE reportes.resumen_platillos_diario (
    id SERIAL PRIMARY KEY,
    jornada_id INT REFERENCES operacion.jornadas(id),
    venta_id INT REFERENCES ventas.ventas(id),
    producto_id INT REFERENCES operacion.productos(id),
    cantidad_vendida INT,
    total_generado DECIMAL(10, 2),
    total_efectivo DECIMAL(10, 2),
    total_tarjeta_bruto DECIMAL(10, 2),
    total_tarjeta_neto DECIMAL(10, 2),
    fecha_generacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE reportes.resumen_propinas_diario (
    id SERIAL PRIMARY KEY,
    jornada_id INT REFERENCES operacion.jornadas(id),
    venta_id INT REFERENCES ventas.ventas(id),
    propinas_efectivo DECIMAL(10, 2) DEFAULT 0,
    propinas_tarjeta_bruto DECIMAL(10, 2) DEFAULT 0,
    propinas_tarjeta_neto DECIMAL(10, 2) DEFAULT 0,
    total_propinas_neto DECIMAL(10, 2) DEFAULT 0,
    fecha_generacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE reportes.resumen_ventas_diario (
    id SERIAL PRIMARY KEY,
    jornada_id INT REFERENCES operacion.jornadas(id),
    venta_id INT REFERENCES ventas.ventas(id),
    total_ventas DECIMAL(10, 2) DEFAULT 0,
    total_efectivo DECIMAL(10, 2) DEFAULT 0,
    total_tarjeta_bruto DECIMAL(10, 2) DEFAULT 0,
    total_tarjeta_neto DECIMAL(10, 2) DEFAULT 0,
    total_transacciones INT DEFAULT 0,
    fecha_generacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Nota: Puedes repetir este patrón para resumen_propinas_diario 
-- y resumen_ventas_diario según las columnas visibles en tu imagen.