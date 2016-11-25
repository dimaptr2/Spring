package ru.velkomfood.production.beaglebone.backend.model;

import java.io.Serializable;
import java.sql.Time;

/**
 * Created by dpetrov on 16.06.16.
 */


public class EventsEntity implements Serializable {

    private int year;
    private String month;
    private int day;
    private String dayweek;
    private Time eventTime;
    private long numberRows;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getDayweek() {
        return dayweek;
    }

    public void setDayweek(String dayweek) {
        this.dayweek = dayweek;
    }

    public Time getEventTime() {
        return eventTime;
    }

    public void setEventTime(Time eventTime) {
        this.eventTime = eventTime;
    }

    public long getNumberRows() {
        return numberRows;
    }

    public void setNumberRows(long numberRows) {
        this.numberRows = numberRows;
    }
}
