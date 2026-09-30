package com.fleetmonitor.device_fleet_monitor.service;

import com.fleetmonitor.device_fleet_monitor.exception.DeviceNotFoundException;
import com.fleetmonitor.device_fleet_monitor.exception.DuplicateDeviceException;
import com.fleetmonitor.device_fleet_monitor.model.Device;
import com.fleetmonitor.device_fleet_monitor.model.DeviceResponse;
import com.fleetmonitor.device_fleet_monitor.model.DeviceStatus;
import com.fleetmonitor.device_fleet_monitor.model.HeartbeatRequest;
import com.fleetmonitor.device_fleet_monitor.model.RegisterDeviceRequest;

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class DeviceService {

    private static final long TIMEOUT_SECONDS = 30;

    private final Map<String, Device> devices = new ConcurrentHashMap<>();

    public DeviceResponse registerDevice(RegisterDeviceRequest request) {

        if (devices.containsKey(request.getId())) {
            throw new DuplicateDeviceException(
                    "Device already exists: " + request.getId()
            );
        }

        Device device = new Device(
                request.getId(),
                request.getName()
        );

        devices.put(request.getId(), device);

        return convertToResponse(device);
    }

    public DeviceResponse processHeartbeat(
            String id,
            HeartbeatRequest request) {

        Device device = devices.get(id);

        if (device == null) {
            throw new DeviceNotFoundException(
                    "Device not found: " + id
            );
        }

        device.updateHeartbeat(
                request.getTimestamp(),
                request.getStatus()
        );

        return convertToResponse(device);
    }

    public List<DeviceResponse> getAllDevices() {

        List<DeviceResponse> result = new ArrayList<>();

        for (Device device : devices.values()) {
            result.add(convertToResponse(device));
        }

        return result;
    }

    public DeviceResponse getDevice(String id) {

        Device device = devices.get(id);

        if (device == null) {
            throw new DeviceNotFoundException(
                    "Device not found: " + id
            );
        }

        return convertToResponse(device);
    }

    public long getTotalDevices() {
        return devices.size();
    }

    public long getOnlineDevices() {

        return devices.values()
                .stream()
                .filter(device ->
                        getStatus(device) == DeviceStatus.ONLINE
                )
                .count();
    }

    public long getOfflineDevices() {

        return devices.values()
                .stream()
                .filter(device ->
                        getStatus(device) == DeviceStatus.OFFLINE
                )
                .count();
    }

    private DeviceResponse convertToResponse(Device device) {

        return new DeviceResponse(
                device.getId(),
                device.getName(),
                getStatus(device),
                device.getLastHeartbeat()
        );
    }

    private DeviceStatus getStatus(Device device) {

        if (device.getLastHeartbeat() == null) {
            return DeviceStatus.OFFLINE;
        }

        long seconds = Duration.between(
                device.getLastHeartbeat(),
                Instant.now()
        ).getSeconds();

        if (seconds <= TIMEOUT_SECONDS) {
            return DeviceStatus.ONLINE;
        }

        return DeviceStatus.OFFLINE;
    }
}