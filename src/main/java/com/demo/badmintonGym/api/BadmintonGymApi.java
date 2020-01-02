package com.demo.badmintonGym.api;

import com.demo.badmintonGym.BadmintonGymApplication;
import com.demo.badmintonGym.api.assembler.ConsoleAssembler;
import com.demo.badmintonGym.api.assembler.InputCommandLineAssembler;
import com.demo.badmintonGym.application.services.BadmintonGymApplicationService;
import com.demo.badmintonGym.domain.Reservation;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class BadmintonGymApi {

    private final BadmintonGymApplicationService badmintonGymApplicationService;

    public BadmintonGymApi() {
        this.badmintonGymApplicationService = new BadmintonGymApplicationService();
    }

    public void reserve(String input) throws IOException {
        String[] args = input.split(BadmintonGymApplication.COMMAND_SEPARATOR);
        badmintonGymApplicationService.reserve(InputCommandLineAssembler.toReservation(args));
    }

    public void cancelReservation(String input) {
        String[] args = input.split(BadmintonGymApplication.COMMAND_SEPARATOR);
        badmintonGymApplicationService.cancelReservation(InputCommandLineAssembler.toReservation(args));
    }

    public Map<String, List<Reservation>> summary() {
        List<Reservation> reservations = badmintonGymApplicationService.findAllReservations();
        return ConsoleAssembler.groupByBadmintonGymName(reservations);
    }

}
