package com.hh99.nearby.exception;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class PrivateResponseBody {
    private String errorCode;
    private String errorMsg;
}