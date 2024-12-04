package com.kodat.of.chargingstationservice.handler;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public enum BusinessErrorCodes {
    STATION_NOT_FOUND(404,HttpStatus.NOT_FOUND,"Station not found"){
    };

    private final int code;
    private final HttpStatus httpStatus;
    private final String description;

    BusinessErrorCodes(int code, HttpStatus httpStatus, String description) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.description = description;
    }
}
