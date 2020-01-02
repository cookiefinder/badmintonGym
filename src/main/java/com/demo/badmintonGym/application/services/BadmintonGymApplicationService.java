package com.demo.badmintonGym.application.services;

import com.demo.badmintonGym.domain.Reservation;
import com.demo.badmintonGym.domain.ReservationRepository;

import java.util.List;

public class BadmintonGymApplicationService {

    private final ReservationRepository reservationRepository;

    public BadmintonGymApplicationService() {
        this.reservationRepository = new ReservationRepository();
    }

    public void reserve(Reservation reservation) {
        reservationRepository.reserve(reservation);
    }


    public void cancelReservation(Reservation reservation) {
        reservationRepository.cancelReservation(reservation);
    }

    public List<Reservation> findAllReservations() {
        return reservationRepository.findAllReservations();
    }
}
