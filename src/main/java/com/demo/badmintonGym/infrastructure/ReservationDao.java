package com.demo.badmintonGym.infrastructure;

import com.demo.badmintonGym.common.exception.ErrorEnums;
import com.demo.badmintonGym.domain.Reservation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReservationDao {
    private static final List<Reservation> db = new ArrayList<>();

    public void addReservation(Reservation reservation) throws IOException {
        checkReservationTime(reservation);
        reservation.init();
        db.add(reservation);
    }

    public void updateReservation(Reservation reservation) {
        Optional<Reservation> optionalReservation = db.stream()
                .filter(existedReservation -> existedReservation.equals(reservation))
                .findFirst();
        if (optionalReservation.isPresent()) {
            optionalReservation.get().cancel();
        } else {
            throw new RuntimeException(ErrorEnums.NOT_EXIST.getMsg());
        }
    }

    public List<Reservation> selectAllReservations() {
        return db;
    }

    private void checkReservationTime(Reservation reservation) {
        long conflictedCount = db.stream()
                .filter(reservation::isConflicted)
                .count();
        if (conflictedCount != 0) {
            throw new RuntimeException(ErrorEnums.TIME_CONFLICT.getMsg());
        }
    }
}
