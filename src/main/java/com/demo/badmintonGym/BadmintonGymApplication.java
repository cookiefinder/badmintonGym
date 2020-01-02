package com.demo.badmintonGym;

import com.demo.badmintonGym.api.BadmintonGymApi;
import com.demo.badmintonGym.domain.Reservation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Optional.ofNullable;

public class BadmintonGymApplication {

    public static final String[] BADMINTON_GYM_NAMES = new String[]{"A", "B", "C", "D"};
    private static final String CANCEL_COMMAND_FLAG = "C";

    public static void main(String[] args) {
        BadmintonGymApi badmintonGymApi = new BadmintonGymApi();
        while (true) {
            try {
                Scanner scanner = new Scanner(System.in);
                String inputLine = scanner.nextLine();
                if (inputLine.equals("exit")) {
                    break;
                }
                if (inputLine.equals("")) {
                    Map<String, List<Reservation>> summary = badmintonGymApi.summary();
                    printToConsole(summary);
                } else {
                    String[] arguments = inputLine.split(" ");
                    if (arguments.length > 4 && CANCEL_COMMAND_FLAG.equals(arguments[4])) {
                        badmintonGymApi.cancelReservation(inputLine);
                    } else {
                        badmintonGymApi.reserve(inputLine);
                    }
                    replySuccessToClient();
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void replySuccessToClient() {
        System.out.println("Success: the booking is accepted!");
    }

    private static void printToConsole(Map<String, List<Reservation>> summary) {
        List<Integer> fees = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        builder.append("收入汇总").append("\n");
        builder.append("---");

        Arrays.stream(BADMINTON_GYM_NAMES).forEach(badmintonGymName -> {
            AtomicInteger subtotalFee = new AtomicInteger();
            builder.append("\n").append("场地:").append(badmintonGymName).append("\n");
            ofNullable(summary.get(badmintonGymName)).ifPresent(reservations ->
                    reservations.forEach(reservation -> {
                        builder.append(reservation).append("\n");
                        subtotalFee.getAndAdd(reservation.getFee().intValue());
                    }));
            fees.add(subtotalFee.intValue());
            builder.append("小计：").append(subtotalFee.intValue()).append("元").append("\n");
        });
        builder.append("---").append("\n");
        int totalFee = fees.stream().mapToInt(Integer::intValue).sum();
        builder.append("总计：").append(totalFee);

        System.out.println(builder.toString());
    }
}
