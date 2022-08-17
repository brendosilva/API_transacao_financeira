package br.curso.coffe.transaction.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.logging.Logger;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class ApplicationExceptionHandler {



    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ErrorResponse> unauthorized(UnauthorizedException exception) {
        log.error("Erro não autorizado tratado", exception);
        return response(exception.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> internal(Exception exception) {
        log.error("Erro não autorizado tratado", exception);
        return response("Error generic", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> notFoundException(NotFoundException exception) {
        log.error("Recurso não encontrado", exception);
        return response(exception.getMessage(), HttpStatus.NOT_FOUND);
    }



    private ResponseEntity<ErrorResponse> response(String message, HttpStatus status) {
        return ResponseEntity.status(status).body(new ErrorResponse(message, status.value()));
    }

}
