-- Crear tabla administradores (en caso de que Hibernate no lo haga automáticamente)
CREATE TABLE IF NOT EXISTS administradores (
    id SERIAL PRIMARY KEY,
    usuario VARCHAR(50) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insertar administrador por defecto si no existe
-- Usuario: admin, Contraseña: admin123 (en Base64)
DO $$ 
BEGIN
    IF NOT EXISTS (SELECT 1 FROM administradores WHERE usuario = 'admin') THEN
        INSERT INTO administradores (usuario, contrasena, activo) 
        VALUES ('admin', 'YWRtaW4xMjM=', true);
    END IF;
END $$;
