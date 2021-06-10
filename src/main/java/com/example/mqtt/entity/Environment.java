package com.example.mqtt.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("environment")
public class Environment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;
    private String clientId;
    private Integer slaveId;
    private BigDecimal co2;
    private BigDecimal pm25;
    private BigDecimal humidity;
    private BigDecimal temperature;
    private Date dataTime;
    private Date createTime;

}
