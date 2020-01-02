package com.demo.badmintonGym.domain;

import com.demo.badmintonGym.common.exception.ErrorEnums;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Discount {
    private static final String FILE_NAME = "discount.txt";
    private static final String DISCOUNT_SEPARATOR = " ";
    private Date startDate;
    private Date endDate;
    private Integer discount;

    private Discount(Date startDate, Date endDate, Integer discount) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.discount = discount;
    }

    public boolean isDiscounted(Date date) {
        return startDate.getTime() <= date.getTime() && endDate.getTime() >= date.getTime();
    }

    public Integer getDiscount() {
        return discount;
    }

    public static List<Discount> getDiscounts() throws IOException {
        List<String> readLines = readDiscounts();
        return readLines.stream().map(string -> {
            String[] splitString = string.split(DISCOUNT_SEPARATOR);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date startDate = sdf.parse(splitString[0]);
                Date endDate = sdf.parse(splitString[1]);
                Integer discount = Integer.valueOf(splitString[2]);

                return new Discount(startDate, endDate, discount);
            } catch (ParseException e) {
                throw new RuntimeException("time format is error");
            }
        }).collect(Collectors.toList());
    }

    private static List<String> readDiscounts() throws IOException {
        List<String> readLines = new ArrayList<>();
        ClassLoader classLoader = Discount.class.getClassLoader();
        URL url = classLoader.getResource(FILE_NAME);
        if (url == null) {
            throw new RuntimeException(ErrorEnums.FILE_NOT_FOUND.getMsg());
        }
        try (FileInputStream fis = new FileInputStream(new File(url.getFile()));
             BufferedReader reader = new BufferedReader(new InputStreamReader(fis))) {
            String line;
            while ((line = reader.readLine()) != null) {
                readLines.add(line);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(ErrorEnums.FILE_NOT_FOUND.getMsg());
        }
        return readLines;
    }
}
