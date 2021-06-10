package com.example.mqtt.controller;

import com.example.mqtt.mqtt.IotMqttGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 指令下发
 */
@RestController
@RequestMapping("cmd")
public class CmdController {

    @Autowired
    private IotMqttGateway iotMqttGateway;

    @GetMapping("getDeviceInfo")
    public void getDeviceInfo() {
        iotMqttGateway.sendMessage("mqtt/command", 1, "info");
    }
}
