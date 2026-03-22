package com.pagoda.pagoda_api.exception;

import com.pagoda.pagoda_api.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>> manejarBusinessException(BusinessException ex) {
        return ApiResponse.error(
                ex.getErrorCodigo().getMensaje(),
                ex.getErrorCodigo().getStatus()
        );
    }

    // Atrapador para errores inesperados (como nulos o errores de SQL)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> manejarExceptionGeneral(Exception ex) {
        return ApiResponse.error(
                "Error inesperado: " + ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<Void>> manejarErrorIntegridad(Exception ex) {
        // Si el error contiene la palabra "fk" o "violates", es una llave foránea
        return ApiResponse.error(
                "No se puede realizar la operación: El registro está relacionado con otros datos.",
                HttpStatus.CONFLICT
        );
    }
}