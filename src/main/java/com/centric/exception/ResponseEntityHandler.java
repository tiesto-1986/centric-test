package com.centric.exception;

import java.nio.file.AccessDeniedException;
import java.util.Date;

import com.centric.dto.ErrorDetails;
import com.centric.exception.CentricException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@RestController
@Slf4j
public class ResponseEntityHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex, WebRequest request) {
        log.error(ex.getMessage());

        if (ex instanceof CentricException) {
            ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
                    request.getDescription(false));
            CentricException centricException = ((CentricException) ex);
            HttpStatus httpStatus = centricException.getHttpStatus() == null
                    ? CentricException.DEFAULT_HTTP_STATUS
                    : centricException.getHttpStatus();
            return new ResponseEntity<>(errorDetails, httpStatus);
        } else if (ex instanceof MethodArgumentNotValidException){
            ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
            return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
        } else {
            ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
            return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
