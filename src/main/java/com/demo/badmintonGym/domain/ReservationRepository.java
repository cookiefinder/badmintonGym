package com.demo.badmintonGym.domain;

import com.demo.badmintonGym.common.SingletonBean;
import com.demo.badmintonGym.infrastructure.ReservationDao;

import java.util.List;
import java.util.Map;

public class ReservationRepository {
    private final ReservationDao reservationDao;

    public ReservationRepository() {
        this.reservationDao = SingletonBean.getReservationDao();
    }

    public boolean reserve(Reservation reservation) {
        return reservationDao.addReservation(reservation);
    }

    public boolean cancelReservation(Reservation reservation) {
        return reservationDao.updateReservation(reservation);
    }

    public Map<String, List<Reservation>> summary() {
        return reservationDao.selectAll();
    }
}
