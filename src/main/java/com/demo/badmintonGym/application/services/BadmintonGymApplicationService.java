package com.demo.badmintonGym.application.services;

import com.demo.badmintonGym.domain.Reservation;
import com.demo.badmintonGym.domain.ReservationRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public void summary() {
        List<Reservation> reservations = reservationRepository.findAllReservations();

        Map<String, List<Reservation>> mergedReservations = reservations.stream()
                .collect(Collectors.groupingBy(Reservation::getBadmintonGymName));

        List<Integer> fees = new ArrayList<>();
        System.out.println("收入汇总");
        System.out.println("---");
        Stream<Map.Entry<String, List<Reservation>>> sorted = mergedReservations.entrySet().stream().sorted();
        sorted.forEach(entry -> {
            System.out.println("场地:" + entry.getKey());
            entry.getValue().forEach(System.out::println);
            int fee = entry.getValue().stream().map(Reservation::getFee).mapToInt(BigDecimal::intValue).sum();
            fees.add(fee);
            System.out.println("小计:" + fee + "元");
            System.out.println();
        });
        System.out.println("---");
        System.out.println("总计:" + fees.stream().mapToInt(Integer::intValue).sum() + "元");
    }
}
