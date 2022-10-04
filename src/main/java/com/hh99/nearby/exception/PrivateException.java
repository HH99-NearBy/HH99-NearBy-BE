package com.hh99.nearby.exception;

import lombok.Getter;

@Getter

public class PrivateException extends RuntimeException {
    private ErrorCode errorCode;

    public PrivateException(ErrorCode errorCode) {
        super(errorCode.name()+" : "+errorCode.getErrorMsg());
        this.errorCode = errorCode;
    }
}
