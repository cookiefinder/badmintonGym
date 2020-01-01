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

    public void reserve(Reservation reservation) {
        reservation.create();
        reservationDao.addReservation(reservation);
    }

    public void cancelReservation(Reservation reservation) {
        reservationDao.updateReservation(reservation);
    }

    public List<Reservation> findAllReservations() {
        return reservationDao.selectAllReservations();
    }
}
