package com.fleetmonitor.device_fleet_monitor.model;

import java.time.Instant;

public class Device {

    private String id;
    private String name;
    private Instant lastHeartbeat;
    private String heartbeatStatus;

    public Device(String id, String name) {
        this.id = id;
        this.name = name;
        this.lastHeartbeat = null;
        this.heartbeatStatus = null;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Instant getLastHeartbeat() {
        return lastHeartbeat;
    }

    public String getHeartbeatStatus() {
        return heartbeatStatus;
    }

    public void updateHeartbeat(Instant timestamp, String status) {
        this.lastHeartbeat = timestamp;
        this.heartbeatStatus = status;
    }
}