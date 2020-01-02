package com.demo.badmintonGym.common;

import com.demo.badmintonGym.common.exception.ErrorEnums;

import java.math.BigDecimal;

public class BadmintonGymChargeStandard {
    public static BigDecimal getWorkdayPriceWhen(Integer time) {
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
        throw new RuntimeException(ErrorEnums.INVALID_INPUT.getMsg());
    }

    public static BigDecimal getNonWorkdayPriceWhen(Integer time) {
        if (time <= 12) {
            return BigDecimal.valueOf(40);
        }
        if (time <= 18) {
            return BigDecimal.valueOf(50);
        }
        if (time <= 22) {
            return BigDecimal.valueOf(60);
        }
        throw new RuntimeException(ErrorEnums.INVALID_INPUT.getMsg());
    }
}
