package com.demo.badmintonGym.infrastructure;

import com.demo.badmintonGym.domain.Reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReservationDao {
    private static final List<Reservation> db = new ArrayList<>();

    public void addReservation(Reservation reservation) {
        checkReservationTime(reservation);
        reservation.init();
        db.add(reservation);
    }

    private void checkReservationTime(Reservation reservation) {
        long conflictedCount = db.stream()
                .filter(existedReservation -> isConflicted(existedReservation, reservation))
                .count();
        if (conflictedCount != 0) {
            throw new RuntimeException("the booking conflicts with existing bookings!");
        }
    }

    private boolean isConflicted(Reservation existedReservation, Reservation reservation) {
        if (existedReservation.isCancelled()) {
            return false;
        }
        if (reservation.getStartTime() >= 9 && reservation.getEndTime() <= 22) {
            if (existedReservation.getDate().equals(reservation.getDate()) &&
                    existedReservation.getBadmintonGymName().equals(reservation.getBadmintonGymName())) {
                // 与当前预约记录冲突 返回 true, 否则返回 false
                return !(existedReservation.getStartTime() >= reservation.getEndTime() ||
                        existedReservation.getEndTime() <= reservation.getStartTime());
            }
            return false;
        }
        throw new RuntimeException("the booking is invalid!");
    }

    public void updateReservation(Reservation reservation) {
        Optional<Reservation> optionalReservation = db.stream()
                .filter(existedReservation -> existedReservation.equals(reservation))
                .findFirst();
        if (optionalReservation.isPresent()) {
            optionalReservation.get().cancel();
        } else {
            throw new RuntimeException("the booking being cancelled does not exist");
        }
    }

    public List<Reservation> selectAllReservations() {
        return db;
    }
}
