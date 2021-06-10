package com.example.mqtt.utils;

import java.math.BigDecimal;

/**
 * 计算公式
 */
public class MathFormula {

    private static final BigDecimal COM1 = BigDecimal.valueOf(256);
    private static final BigDecimal COM2 = BigDecimal.valueOf(10.0);
    private static final BigDecimal COM3 = BigDecimal.valueOf(-6);
    private static final BigDecimal COM4 = BigDecimal.valueOf(125);
    private static final BigDecimal COM5 = BigDecimal.valueOf(-46.85);
    private static final BigDecimal COM6 = BigDecimal.valueOf(175.72);
    private static final BigDecimal COM7 = BigDecimal.valueOf(65536);

    public static BigDecimal co2(Integer h, Integer l) {
        BigDecimal bh = BigDecimal.valueOf(h);
        BigDecimal bl = BigDecimal.valueOf(l);
        return bh.multiply(COM1).add(bl);
    }

    public static BigDecimal pm25(Integer h, Integer l) {
        return co2(h, l);
    }

    public static BigDecimal temperature(Integer h, Integer l) {
        BigDecimal bh = BigDecimal.valueOf(h);
        BigDecimal bl = BigDecimal.valueOf(l);
        return bh.multiply(COM1).add(bl).multiply(COM6).divide(COM7, 3, BigDecimal.ROUND_DOWN).add(COM5);
    }

    public static BigDecimal humidity(Integer h, Integer l) {
        BigDecimal bh = BigDecimal.valueOf(h);
        BigDecimal bl = BigDecimal.valueOf(l);
        return bh.multiply(COM1).add(bl).multiply(COM4).divide(COM7, 3, BigDecimal.ROUND_DOWN).add(COM3);
    }

}
