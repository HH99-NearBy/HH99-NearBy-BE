package com.hh99.nearby.exception;

import io.sentry.Sentry;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//Global error controller
@RestControllerAdvice
public class RestApiExceptionHandler {
    @ExceptionHandler(value = { PrivateException.class })
    public ResponseEntity<?> handleApiRequestException(PrivateException ex) {
        String errCode = ex.getErrorCode().getErrorCode();
        String errMSG = ex.getErrorCode().getErrorMsg();
        PrivateResponseBody privateResponseBody = PrivateResponseBody.builder().errorCode(errCode).errorMsg(errMSG).build();
        Sentry.captureException(ex);
        return new ResponseEntity(
                privateResponseBody,
                ex.getErrorCode().getHttpStatus()
        );
    }
}
