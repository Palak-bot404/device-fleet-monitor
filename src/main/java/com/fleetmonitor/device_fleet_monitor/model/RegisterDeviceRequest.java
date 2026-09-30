package com.fleetmonitor.device_fleet_monitor.model;

import jakarta.validation.constraints.NotBlank;

public class RegisterDeviceRequest {

    @NotBlank(message = "Device ID is required")
    private String id;

    @NotBlank(message = "Device name is required")
    private String name;

    public RegisterDeviceRequest() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}