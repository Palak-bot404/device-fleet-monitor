package com.fleetmonitor.device_fleet_monitor.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public class HeartbeatRequest {

    @NotNull(message = "Timestamp is required")
    private Instant timestamp;

    @NotBlank(message = "Status is required")
    private String status;

    public Instant getTimestamp() {
        return timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}