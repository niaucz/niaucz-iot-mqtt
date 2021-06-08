package com.example.mqtt.entity;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author ksd
 */
public class ModBusData implements Serializable {

    private static final long serialVersionUID = 1L;
    private String clientId;
    private Integer slaveId;
    private Integer address;
    private Integer quantity;
    private Integer[] data;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Integer getSlaveID() {
        return slaveId;
    }

    public void setSlaveID(Integer slaveID) {
        this.slaveId = slaveID;
    }

    public Integer getAddress() {
        return address;
    }

    public void setAddress(Integer address) {
        this.address = address;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer[] getData() {
        return data;
    }

    public void setData(Integer[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ModBusData{" +
                "clientId='" + clientId + '\'' +
                ", slaveID=" + slaveId +
                ", address=" + address +
                ", quantity=" + quantity +
                ", data=" + Arrays.toString(data) +
                '}';
    }
}
