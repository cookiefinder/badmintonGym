package com.demo.badmintonGym.api.assembler;

import com.demo.badmintonGym.domain.Reservation;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ConsoleAssembler {
    public static Map<String, List<Reservation>> groupByBadmintonGymName(List<Reservation> reservations) {
        Map<String, List<Reservation>> mergedReservations = reservations.stream()
                .collect(Collectors.groupingBy(Reservation::getBadmintonGymName));

        mergedReservations.forEach((key, value) -> {
            List<Reservation> sortedReservations = value.stream()
                    .sorted((reservation1, reservation2) ->
                            reservation1.getDate().getTime() == reservation2.getDate().getTime() ?
                                    reservation1.getStartTime().compareTo(reservation2.getStartTime()) :
                                    Long.compare(reservation1.getDate().getTime(),
                                            reservation2.getDate().getTime())).collect(Collectors.toList());
            mergedReservations.put(key, sortedReservations);
        });
        return mergedReservations;
    }
}
