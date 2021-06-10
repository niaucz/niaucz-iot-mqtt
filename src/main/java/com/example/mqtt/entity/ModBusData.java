package com.example.mqtt.entity;

import lombok.Data;

import java.io.Serializable;


@Data
public class ModBusData implements Serializable {

    private static final long serialVersionUID = 1L;
    private String clientId;
    private Integer slaveId;
    private Integer address;
    private Integer quantity;
    private Integer[] data;

}
