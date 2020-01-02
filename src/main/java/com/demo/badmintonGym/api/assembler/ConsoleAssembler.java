package com.demo.badmintonGym.api.assembler;

import com.demo.badmintonGym.domain.Reservation;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ConsoleAssembler {
    public static Map<String, List<Reservation>> console(List<Reservation> reservations) {
        Map<String, List<Reservation>> mergedReservations = reservations.stream()
                .collect(Collectors.groupingBy(Reservation::getBadmintonGymName));

        mergedReservations.forEach((key, value) -> {
            List<Reservation> sortedReservations = value.stream()
                    .sorted((reservation1, reservation2) -> {
                        if (reservation1.getDate().getTime() == reservation2.getDate().getTime()) {
                            if (reservation1.getStartTime() > reservation2.getStartTime()) {
                                return 1;
                            } else {
                                return -1;
                            }
                        } else if (reservation1.getDate().getTime() > reservation2.getDate().getTime()) {
                            return 1;
                        } else {
                            return -1;
                        }
                    }).collect(Collectors.toList());
            mergedReservations.put(key, sortedReservations);
        });
        return mergedReservations;
    }
}
