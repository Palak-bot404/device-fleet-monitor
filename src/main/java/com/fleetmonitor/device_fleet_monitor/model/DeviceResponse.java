package com.fleetmonitor.device_fleet_monitor.model;

import java.time.Instant;

public class DeviceResponse {

    private String id;
    private String name;
    private DeviceStatus status;
    private Instant lastHeartbeat;

    public DeviceResponse(
            String id,
            String name,
            DeviceStatus status,
            Instant lastHeartbeat) {

        this.id = id;
        this.name = name;
        this.status = status;
        this.lastHeartbeat = lastHeartbeat;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public DeviceStatus getStatus() {
        return status;
    }

    public Instant getLastHeartbeat() {
        return lastHeartbeat;
    }
}