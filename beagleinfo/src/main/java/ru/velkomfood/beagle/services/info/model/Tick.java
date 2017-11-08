package ru.velkomfood.beagle.services.info.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "ticks")
public class Tick implements Serializable {

    @Id
    private long id;
    @Column(name = "cur_date")
    private java.sql.Date currentDate;
    @Column(name = "cur_time")
    private java.sql.Time currentTime;

    public Tick() {
    }

    public Tick(long id, Date currentDate, Time currentTime) {
        this.id = id;
        this.currentDate = currentDate;
        this.currentTime = currentTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public Time getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Time currentTime) {
        this.currentTime = currentTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tick tick = (Tick) o;

        return id == tick.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Tick{" +
                "id=" + id +
                ", currentDate=" + currentDate +
                ", currentTime=" + currentTime +
                '}';
    }

}
