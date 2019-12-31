package com.demo.badmintonGym;

import com.demo.badmintonGym.api.ReserveApi;

import java.text.ParseException;
import java.util.Scanner;

public class BadmintonGymApplication {

    private static final ReserveApi reserveApi = new ReserveApi();

    public static void main(String[] args) throws ParseException {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String inputLine = scanner.nextLine();
            System.out.println("输入的内容: " + inputLine);
            if (inputLine.equals("exit")) {
                break;
            }
            if (inputLine.equals("")) {
                reserveApi.summary();
            } else {
                String[] arguments = inputLine.split(" ");
                if (arguments[arguments.length - 1].equals("C")) {
                    boolean result = reserveApi.cancelReservation(inputLine);
                    if (!result) {
                        System.out.println("取消订单失败");
                    }
                } else {
                    boolean result = reserveApi.reserve(inputLine);
                    if (!result) {
                        System.out.println("预约失败");
                    }
                }
            }
        }
    }
}
