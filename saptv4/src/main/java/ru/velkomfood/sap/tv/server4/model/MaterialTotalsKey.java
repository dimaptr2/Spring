package ru.velkomfood.sap.tv.server4.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.Objects;

public class MaterialTotalsKey implements Serializable {

    private long id;
    private java.sql.Date date;
    private java.sql.Time time;

    public MaterialTotalsKey() {
    }

    public MaterialTotalsKey(long id, Date date, Time time) {
        this.id = id;
        this.date = date;
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MaterialTotalsKey that = (MaterialTotalsKey) o;
        return id == that.id &&
                date.equals(that.date) &&
                Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, time);
    }

    @Override
    public String toString() {
        return "MaterialTotalsKey{" +
                "id=" + id +
                ", date=" + date +
                ", time=" + time +
                '}';
    }

}
