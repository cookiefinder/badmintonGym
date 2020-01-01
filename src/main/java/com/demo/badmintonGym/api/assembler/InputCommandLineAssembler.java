package com.demo.badmintonGym.api.assembler;

import com.demo.badmintonGym.domain.Reservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class InputCommandLineAssembler {
    public static Reservation toReservation(String[] args) throws ParseException {
        try {
            String[] StartAndEndTime = args[2].split("~");
            checkInputTime(StartAndEndTime);
            checkInputBadmintonGym(args[3]);
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(args[1]);

            return new Reservation(args[0],
                    args[3],
                    date,
                    Integer.valueOf(StartAndEndTime[0].split(":")[0]),
                    Integer.valueOf(StartAndEndTime[1].split(":")[0]));

        } catch (RuntimeException | ParseException e) {
            throw e;
        }
    }

    private static void checkInputBadmintonGym(String badmintomGymName) {
        if (!Arrays.asList("A", "B", "C", "D").contains(badmintomGymName)) {
            throw new RuntimeException();
        }
    }

    private static void checkInputTime(String[] times) {
        String[] startHourAndMin = times[0].split(":");
        String[] endHourAndMin = times[1].split(":");
        if (!startHourAndMin[1].equals("00") || !endHourAndMin[1].equals("00")) {
            throw new RuntimeException();
        }
        if (Integer.valueOf(startHourAndMin[0]) >= Integer.valueOf(endHourAndMin[0])) {
            throw new RuntimeException();
        }
    }
}
