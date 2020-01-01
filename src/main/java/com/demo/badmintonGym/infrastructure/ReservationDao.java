package com.demo.badmintonGym.infrastructure;

import com.demo.badmintonGym.domain.Reservation;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ReservationDao {
    private static final List<Reservation> db = new ArrayList<>();

    public void addReservation(Reservation reservation) {
        if (checkReservationTime(reservation)) {
            db.add(reservation);
        }
        throw new RuntimeException("预约时间发生冲突");
    }

    private boolean checkReservationTime(Reservation reservation) {
        long conflictedCount = db.stream()
                .filter(existedReservation -> isConflicted(existedReservation, reservation))
                .count();
        return conflictedCount == 0;
    }

    private boolean isConflicted(Reservation existedReservation, Reservation reservation) {
        if (reservation.getStartTime() >= 9 && reservation.getEndTime() <= 22) {
            if (existedReservation.getDate().equals(reservation.getDate()) &&
                    existedReservation.getBadmintonGymName().equals(reservation.getBadmintonGymName())) {
                // 与当前预约记录冲突 返回 true, 否则返回 false
                return !(existedReservation.getStartTime() >= reservation.getEndTime() ||
                        existedReservation.getEndTime() <= reservation.getStartTime());
            }
            return false;
        }
        throw new RuntimeException("球场预约时间应在9~22点之间");
    }

    public void updateReservation(Reservation reservation) {
        Optional<Reservation> optionalReservation = db.stream()
                .filter(existedReservation -> existedReservation.equals(reservation))
                .findFirst();
        if (optionalReservation.isPresent()) {
            optionalReservation.get().cancel();
        } else {
            throw new RuntimeException("未找到相应的预约记录");
        }
    }

    public List<Reservation> selectAllReservations() {
        return db;
    }
}
