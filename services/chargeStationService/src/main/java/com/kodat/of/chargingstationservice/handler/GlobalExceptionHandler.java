package com.kodat.of.chargingstationservice.handler;

import com.kodat.of.chargingstationservice.exception.StationNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(StationNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleStationNotFoundException(StationNotFoundException e) {
        LOGGER.error("Station not found ", e);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        ExceptionResponse
                                .builder()
                                .businessErrorCode(BusinessErrorCodes.STATION_NOT_FOUND.getCode())
                                .businessErrorDescription(BusinessErrorCodes.STATION_NOT_FOUND.getDescription())
                                .error(e.getMessage())
                                .build()
                );
    }
}
