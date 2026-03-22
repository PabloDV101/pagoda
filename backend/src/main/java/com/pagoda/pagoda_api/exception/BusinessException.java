package com.pagoda.pagoda_api.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final ErrorCodigo errorCodigo;

    public BusinessException(ErrorCodigo errorCodigo) {
        super(errorCodigo.getMensaje());
        this.errorCodigo = errorCodigo;
    }
}