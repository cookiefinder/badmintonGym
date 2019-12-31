package com.demo.badmintonGym.common;

import com.demo.badmintonGym.infrastructure.ReservationDao;

/**
 * 获取单列的饿汉模式
 */
public class SingletonBean {

    private static final ReservationDao reservationDao = new ReservationDao();

    public static ReservationDao getReservationDao() {
        return reservationDao;
    }
}
