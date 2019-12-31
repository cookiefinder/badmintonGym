package com.demo.badmintonGym.infrastructure;

import com.demo.badmintonGym.domain.Reservation;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class ReservationDao {
    private static final Map<String, List<Reservation>> db = new ConcurrentHashMap<>();

    public boolean addReservation(Reservation reservation) {
        if (checkReservationTime(reservation)) {
            List<Reservation> reservations = db.get(reservation.getUserId());
            if (reservations == null) {
                db.put(reservation.getUserId(), Collections.singletonList(reservation));
            } else {
                reservations.add(reservation);
            }
            return true;
        }
        return false;
    }

    private boolean checkReservationTime(Reservation reservation) {
        long conflictedCount = db.values().stream()
                .filter(reservations -> isConflicted(reservations, reservation))
                .count();
        return conflictedCount == 0;
    }

    private boolean isConflicted(List<Reservation> reservations, Reservation reservation) {
        return reservations.stream().noneMatch(existedReservation -> {
            if (reservation.getStartTime() >= 9 && reservation.getEndTime() <= 22) {
                if (existedReservation.getDate().equals(reservation.getDate())) {
                    // 与当前预约记录冲突 返回 true, 否则返回 false
                    return existedReservation.getStartTime() >= reservation.getEndTime() ||
                            existedReservation.getEndTime() <= reservation.getStartTime();
                }
                return true;
            }
            return true;
        });
    }

    public boolean updateReservation(Reservation reservation) {
        List<Reservation> reservations = db.get(reservation.getUserId());
        if (reservations != null && reservations.size() != 0) {
            Optional<Reservation> optionalReservation = reservations.stream()
                    .filter(existedReservation -> existedReservation.equals(reservation))
                    .findFirst();
            if (optionalReservation.isPresent()) {
                optionalReservation.get().cancel();
                return true;
            }
        }
        throw new RuntimeException();
    }

    public Map<String, List<Reservation>> selectAll() {
        return db;
    }
}
