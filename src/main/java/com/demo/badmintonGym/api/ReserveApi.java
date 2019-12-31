package com.demo.badmintonGym.api;

import com.demo.badmintonGym.api.assembler.RequestAssembler;
import com.demo.badmintonGym.application.services.ReservationService;

import java.text.ParseException;

public class ReserveApi {

    private final ReservationService reservationService;

    public ReserveApi() {
        this.reservationService = new ReservationService();
    }

    public boolean reserve(String input) throws ParseException {
        String[] args = input.split(" ");
        return reservationService.reserve(RequestAssembler.toReservation(args));
    }

    public boolean cancelReservation(String input) throws ParseException {
        String[] args = input.split(" ");
        return reservationService.cancelReservation(RequestAssembler.toReservation(args));
    }

    public void summary() {
        reservationService.summary();
    }

}
