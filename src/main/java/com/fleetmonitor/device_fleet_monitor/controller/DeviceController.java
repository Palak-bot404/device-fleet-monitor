package com.fleetmonitor.device_fleet_monitor.controller;

import com.fleetmonitor.device_fleet_monitor.model.DeviceResponse;
import com.fleetmonitor.device_fleet_monitor.model.HeartbeatRequest;
import com.fleetmonitor.device_fleet_monitor.model.RegisterDeviceRequest;
import com.fleetmonitor.device_fleet_monitor.service.DeviceService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping("/devices")
    @ResponseStatus(HttpStatus.CREATED)
    public DeviceResponse registerDevice(
            @Valid @RequestBody RegisterDeviceRequest request) {

        return deviceService.registerDevice(request);
    }

    @PostMapping("/devices/{id}/heartbeat")
    public DeviceResponse heartbeat(
            @PathVariable String id,
            @Valid @RequestBody HeartbeatRequest request) {

        return deviceService.processHeartbeat(id, request);
    }

    @GetMapping("/devices")
    public List<DeviceResponse> getDevices() {
        return deviceService.getAllDevices();
    }

    @GetMapping("/devices/{id}")
    public DeviceResponse getDevice(@PathVariable String id) {
        return deviceService.getDevice(id);
    }

    @GetMapping("/summary")
    public Map<String, Long> getSummary() {

        return Map.of(
                "total", deviceService.getTotalDevices(),
                "online", deviceService.getOnlineDevices(),
                "offline", deviceService.getOfflineDevices()
        );
    }
}