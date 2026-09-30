package com.fleetmonitor.device_fleet_monitor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DeviceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleDeviceNotFound(
            DeviceNotFoundException exception) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of(
                        "timestamp", Instant.now().toString(),
                        "status", 404,
                        "error", "Not Found",
                        "message", exception.getMessage()
                )
        );
    }

    @ExceptionHandler(DuplicateDeviceException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicateDevice(
            DuplicateDeviceException exception) {

        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                Map.of(
                        "timestamp", Instant.now().toString(),
                        "status", 409,
                        "error", "Conflict",
                        "message", exception.getMessage()
                )
        );
    }
}