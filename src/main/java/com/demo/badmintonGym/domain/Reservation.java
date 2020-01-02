package com.demo.badmintonGym.domain;

import com.demo.badmintonGym.common.BadmintonGymChargeStandard;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import static java.util.Calendar.SATURDAY;
import static java.util.Calendar.SUNDAY;

public class Reservation {
    private String userId;
    private String badmintonGymName;
    private Date date;
    // 只能是整数
    private Integer startTime;
    private Integer endTime;

    // 单位是 元
    private BigDecimal price;

    private BigDecimal penalty;

    private ReservationStatus status;

    public Reservation(String userId, String badmintonGymName, Date date, Integer startTime, Integer endTime) {
        this.userId = userId;
        this.badmintonGymName = badmintonGymName;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Date getDate() {
        return date;
    }

    public Integer getStartTime() {
        return startTime;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public String getBadmintonGymName() {
        return badmintonGymName;
    }

    public BigDecimal getFee() {
        return this.price != null ? price : penalty;
    }

    public void cancel() {
        if (isCancelled()) {
            throw new RuntimeException("the booking being cancelled does not exist!");
        }
        this.status = ReservationStatus.CANCELED;
        this.penalty = isWorkDay(date) ?
                price.multiply(BigDecimal.valueOf(0.5)) :
                price.multiply(BigDecimal.valueOf(0.25));
        this.price = null;
    }

    public boolean isCancelled() {
        return this.status == ReservationStatus.CANCELED;
    }

    public void init() {
        this.status = ReservationStatus.SUCCEED;
        this.penalty = null;
        this.price = calculatePrice(date, startTime, endTime);
    }

    private BigDecimal calculatePrice(Date date, Integer startTime, Integer endTime) {
        int price = 0;
        for (int i = startTime + 1; i <= endTime ; i++) {
            BigDecimal eachPrice = isWorkDay(date) ?
                    BadmintonGymChargeStandard.getWorkdayPriceWhen(i) :
                    BadmintonGymChargeStandard.getNonWorkdayPriceWhen(i);
            price += eachPrice.intValue();
        }
        return BigDecimal.valueOf(price);
    }

    private boolean isWorkDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int idx = calendar.get(Calendar.DAY_OF_WEEK);
        return !(idx == SATURDAY || idx == SUNDAY);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Reservation that = (Reservation) object;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(badmintonGymName, that.badmintonGymName) &&
                Objects.equals(date, that.date) &&
                Objects.equals(startTime, that.startTime) &&
                Objects.equals(endTime, that.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, badmintonGymName, date, startTime, endTime);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(new SimpleDateFormat("yyyy-MM-dd").format(date));
        builder.append(" ")
                .append(formatHour(startTime))
                .append("~")
                .append(formatHour(endTime))
                .append(" ");
        if (this.price != null) {
            builder.append(price.toString());
        } else {
            builder.append("违约金")
                    .append(" ")
                    .append(penalty.toString());
        }
        builder.append("元");
        return builder.toString();
    }

    private String formatHour(Integer startTime) {
        if (startTime < 10) {
            return "0" + startTime + ":00";
        }
        return startTime + ":00";
    }
}
