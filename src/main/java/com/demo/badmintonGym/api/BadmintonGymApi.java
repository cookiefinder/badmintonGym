package com.demo.badmintonGym.api;

import com.demo.badmintonGym.api.assembler.InputCommandLineAssembler;
import com.demo.badmintonGym.application.services.BadmintonGymApplicationService;

import java.text.ParseException;

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

    public void summary() {
        badmintonGymApplicationService.summary();
    }

}
