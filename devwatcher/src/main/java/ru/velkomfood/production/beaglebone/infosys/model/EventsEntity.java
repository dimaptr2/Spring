package ru.velkomfood.production.beaglebone.infosys.model;

import java.sql.Time;

/**
 * Created by dpetrov on 11.06.16.
 */


public class EventsEntity {

    private int year;
    private String month;
    private int day;
    private String dayweek;
    private Time eventtime;

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

    public Time getEventtime() {
        return eventtime;
    }

    public void setEventtime(Time eventtime) {
        this.eventtime = eventtime;
    }

}
