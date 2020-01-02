package com.demo.badmintonGym.api;

import com.demo.badmintonGym.api.assembler.ConsoleAssembler;
import com.demo.badmintonGym.api.assembler.InputCommandLineAssembler;
import com.demo.badmintonGym.application.services.BadmintonGymApplicationService;
import com.demo.badmintonGym.domain.Reservation;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public class BadmintonGymApi {

    private final BadmintonGymApplicationService badmintonGymApplicationService;

    public BadmintonGymApi() {
        this.badmintonGymApplicationService = new BadmintonGymApplicationService();
    }

    public void reserve(String input) throws ParseException {
        String[] args = input.split(" ");
        badmintonGymApplicationService.reserve(InputCommandLineAssembler.toReservation(args));
    }

    public void cancelReservation(String input) throws ParseException {
        String[] args = input.split(" ");
        badmintonGymApplicationService.cancelReservation(InputCommandLineAssembler.toReservation(args));
    }

    public Map<String, List<Reservation>> summary() {
        List<Reservation> reservations = badmintonGymApplicationService.findAllReservations();
        return ConsoleAssembler.console(reservations);
    }

}
