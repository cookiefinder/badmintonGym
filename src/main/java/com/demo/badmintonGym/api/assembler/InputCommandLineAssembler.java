package com.demo.badmintonGym.api.assembler;

import com.demo.badmintonGym.common.exception.ErrorEnums;
import com.demo.badmintonGym.domain.Reservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import static com.demo.badmintonGym.BadmintonGymApplication.BADMINTON_GYM_NAMES;

public class InputCommandLineAssembler {

    private static final String TIME_SEPARATOR = "~";
    private static final String HOUR_AND_MINUTE_SEPARATOR = ":";

    public static Reservation toReservation(String[] args) {
        try {
            String[] StartAndEndTime = args[2].split(TIME_SEPARATOR);
            checkInputTime(StartAndEndTime);
            checkInputBadmintonGym(args[3]);
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(args[1]);

            return new Reservation(args[0],
                    args[3],
                    date,
                    Integer.valueOf(StartAndEndTime[0].split(HOUR_AND_MINUTE_SEPARATOR)[0]),
                    Integer.valueOf(StartAndEndTime[1].split(HOUR_AND_MINUTE_SEPARATOR)[0]));

        } catch (RuntimeException | ParseException e) {
            throw new RuntimeException(ErrorEnums.INVALID_INPUT.getMsg());
        }
    }

    private static void checkInputBadmintonGym(String badmintonGymName) {
        if (!Arrays.asList(BADMINTON_GYM_NAMES).contains(badmintonGymName)) {
            throw new RuntimeException(ErrorEnums.INVALID_INPUT.getMsg());
        }
    }

    private static void checkInputTime(String[] times) {
        String[] startHourAndMin = times[0].split(HOUR_AND_MINUTE_SEPARATOR);
        String[] endHourAndMin = times[1].split(HOUR_AND_MINUTE_SEPARATOR);
        if (!startHourAndMin[1].equals("00") || !endHourAndMin[1].equals("00")) {
            throw new RuntimeException(ErrorEnums.INVALID_INPUT.getMsg());
        }
        if (Integer.valueOf(startHourAndMin[0]) >= Integer.valueOf(endHourAndMin[0])) {
            throw new RuntimeException(ErrorEnums.INVALID_INPUT.getMsg());
        }
    }
}
