package com.demo.badmintonGym.domain;

import com.demo.badmintonGym.infrastructure.ReservationDao;

import java.util.List;

public class ReservationRepository {
    private final ReservationDao reservationDao;

    public ReservationRepository() {
        this.reservationDao = new ReservationDao();
    }

    public void reserve(Reservation reservation) {
        reservationDao.addReservation(reservation);
    }

    public void cancelReservation(Reservation reservation) {
        reservationDao.updateReservation(reservation);
    }

    public List<Reservation> findAllReservations() {
        return reservationDao.selectAllReservations();
    }
}
