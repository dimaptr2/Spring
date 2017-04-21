package ru.velkomfood.beagle.info.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by dpetrov on 20.04.17.
 */

public class Event implements Serializable {


    private long year;
    private String month;
    private long day;
    private String dayweek;
    private String eventTime;

    private BigDecimal quantity;

    protected Event() { }

    public Event(long year, String month,
                 long day, String dayweek,
                 String eventTime, BigDecimal quantity) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.dayweek = dayweek;
        this.eventTime = eventTime;
        this.quantity = quantity;
    }

    public long getYear() {
        return year;
    }

    public void setYear(long year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public long getDay() {
        return day;
    }

    public void setDay(long day) {
        this.day = day;
    }

    public String getDayweek() {
        return dayweek;
    }

    public void setDayweek(String dayweek) {
        this.dayweek = dayweek;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Event{" +
                "year=" + year +
                ", month='" + month + '\'' +
                ", day=" + day +
                ", dayweek='" + dayweek + '\'' +
                ", eventTime='" + eventTime + '\'' +
                ", quantity=" + quantity +
                '}';
    }

}
