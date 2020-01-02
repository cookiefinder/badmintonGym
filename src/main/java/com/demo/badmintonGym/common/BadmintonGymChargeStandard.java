package com.demo.badmintonGym.common;

import java.math.BigDecimal;

public class BadmintonGymChargeStandard {
    public static BigDecimal getWorkdayPriceWhen(Integer time) {
        if (time <= 9) {
            return BigDecimal.ZERO;
        }
        if (time <= 12) {
            return BigDecimal.valueOf(30);
        }
        if (time <= 18) {
            return BigDecimal.valueOf(50);
        }
        if (time <= 20) {
            return BigDecimal.valueOf(80);
        }
        if (time <= 22) {
            return BigDecimal.valueOf(60);
        }
        throw new RuntimeException("时间节点不在工作日的运营返回内");
    }

    public static BigDecimal getNonWorkdayPriceWhen(Integer time) {
        if (time <= 9) {
            return BigDecimal.ZERO;
        }
        if (time <= 12) {
            return BigDecimal.valueOf(40);
        }
        if (time <= 18) {
            return BigDecimal.valueOf(50);
        }
        if (time <= 22) {
            return BigDecimal.valueOf(60);
        }
        throw new RuntimeException("时间节点不在非工作日的运营返回内");
    }
}
