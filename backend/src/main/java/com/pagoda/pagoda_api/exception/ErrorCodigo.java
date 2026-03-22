package com.pagoda.pagoda_api.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCodigo {

    ROL_NO_ENCONTRADO("El rol solicitado no existe en la base de datos", HttpStatus.NOT_FOUND),
    NOMBRE_ROL_DUPLICADO("Ya existe un rol con ese nombre", HttpStatus.CONFLICT),
    ROL_EN_USO("No se puede eliminar el rol porque tiene usuarios asociados", HttpStatus.CONFLICT),

    CATEGORIA_NO_ENCONTRADA("La categoría solicitada no existe", HttpStatus.NOT_FOUND),
    NOMBRE_CATEGORIA_DUPLICADO("Ya existe una categoría con ese nombre", HttpStatus.CONFLICT),
    CATEGORIA_EN_USO("No se puede eliminar la categoría porque tiene productos asociados", HttpStatus.CONFLICT),

    ESTADO_MESA_NO_ENCONTRADO("El estado de mesa solicitado no existe", HttpStatus.NOT_FOUND),
    NOMBRE_ESTADO_MESA_DUPLICADO("Ya existe un estado de mesa con ese nombre", HttpStatus.CONFLICT),
    ESTADO_MESA_EN_USO("No se puede eliminar el estado porque hay mesas asignadas a él", HttpStatus.CONFLICT),

    METODO_PAGO_NO_ENCONTRADO("El método de pago solicitado no existe", HttpStatus.NOT_FOUND),
    NOMBRE_METODO_PAGO_DUPLICADO("Ya existe un método de pago con ese nombre", HttpStatus.CONFLICT),
    METODO_PAGO_EN_USO("No se puede eliminar el método de pago porque tiene transacciones relacionadas", HttpStatus.CONFLICT),

    TIPO_COBRO_NO_ENCONTRADO("El tipo de cobro solicitado no existe", HttpStatus.NOT_FOUND),
    NOMBRE_TIPO_COBRO_DUPLICADO("Ya existe un tipo de cobro con ese nombre", HttpStatus.CONFLICT),
    TIPO_COBRO_EN_USO("No se puede eliminar el tipo de cobro porque está siendo utilizado en ventas", HttpStatus.CONFLICT),

    ESTADO_ITEM_NO_ENCONTRADO("El estado de ítem solicitado no existe", HttpStatus.NOT_FOUND),
    NOMBRE_ESTADO_ITEM_DUPLICADO("Ya existe un estado de ítem con ese nombre", HttpStatus.CONFLICT),
    ESTADO_ITEM_EN_USO("No se puede eliminar el estado porque hay productos o pedidos con este estatus", HttpStatus.CONFLICT),

    USUARIO_NO_ENCONTRADO("El usuario solicitado no existe", HttpStatus.NOT_FOUND),
    NOMBRE_USUARIO_DUPLICADO("Ya existe un usuario con ese nombre", HttpStatus.CONFLICT),

    // Productos
    PRODUCTO_NO_ENCONTRADO("El producto solicitado no existe", HttpStatus.NOT_FOUND),
    NOMBRE_PRODUCTO_DUPLICADO("Ya existe un producto con ese nombre en esta categoría", HttpStatus.CONFLICT),
    PRODUCTO_EN_USO("No se puede eliminar el producto porque tiene historial de ventas", HttpStatus.CONFLICT),
    // Mesas
    MESA_NO_ENCONTRADA("La mesa solicitada no existe", HttpStatus.NOT_FOUND),
    NUMERO_MESA_DUPLICADO("Ya existe una mesa con ese número", HttpStatus.CONFLICT),
    MESA_EN_USO("No se puede eliminar la mesa porque tiene pedidos registrados", HttpStatus.CONFLICT),

    // Jornadas
    JORNADA_NO_ENCONTRADA("La jornada solicitada no existe", HttpStatus.NOT_FOUND),
    JORNADA_YA_ABIERTA("No se puede abrir la jornada: ya existe una activa", HttpStatus.BAD_REQUEST),
    JORNADA_CERRADA("La operación no es posible porque la jornada ya está cerrada", HttpStatus.BAD_REQUEST),
    // Cierre de Día
    CIERRE_NO_ENCONTRADO("El registro de cierre de día solicitado no existe", HttpStatus.NOT_FOUND),
    CIERRE_YA_EXISTE_PARA_JORNADA("Ya se ha generado un cierre para esta jornada", HttpStatus.CONFLICT),

    // Parámetros Local
    PARAMETROS_NO_CONFIGURADOS("No se ha encontrado la configuración inicial del local", HttpStatus.NOT_FOUND),
    ;

    private final String mensaje;
    private final HttpStatus status;

    ErrorCodigo(String mensaje, HttpStatus status) {
        this.mensaje = mensaje;
        this.status = status;
    }
}
