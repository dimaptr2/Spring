package ru.velkomfood.sap.tv.server4.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.Objects;

@Entity
@Table(name = "material_totals")
@IdClass(MaterialTotalsKey.class)
public class MaterialTotals implements Serializable {

    @Id
    private long id;
    @Id
    @Column(name = "dt")
    private java.sql.Date date;
    @Id
    @Column(name = "tm")
    private java.sql.Time time;

    @Column(length = 50)
    private String description;
    @Column(length = 3)
    private String uom;
    @Column(precision = 20, scale = 2)
    private BigDecimal packed;
    @Column(precision = 20, scale = 2)
    private BigDecimal quantity;
    @Column(name = "in_process", precision = 20, scale = 2)
    private BigDecimal inProcess;
    @Column(precision = 20, scale = 2)
    private BigDecimal transferred;

    public MaterialTotals() {
    }

    public MaterialTotals(long id, Date date, Time time,
                          String description, String uom,
                          BigDecimal packed, BigDecimal quantity,
                          BigDecimal inProcess, BigDecimal transferred) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.description = description;
        this.uom = uom;
        this.packed = packed;
        this.quantity = quantity;
        this.inProcess = inProcess;
        this.transferred = transferred;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public BigDecimal getPacked() {
        return packed;
    }

    public void setPacked(BigDecimal packed) {
        this.packed = packed;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getInProcess() {
        return inProcess;
    }

    public void setInProcess(BigDecimal inProcess) {
        this.inProcess = inProcess;
    }

    public BigDecimal getTransferred() {
        return transferred;
    }

    public void setTransferred(BigDecimal transferred) {
        this.transferred = transferred;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MaterialTotals that = (MaterialTotals) o;
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
        return "MaterialTotals{" +
                "id=" + id +
                ", date=" + date +
                ", time=" + time +
                ", description='" + description + '\'' +
                ", uom='" + uom + '\'' +
                ", packed=" + packed +
                ", quantity=" + quantity +
                ", inProcess=" + inProcess +
                ", transferred=" + transferred +
                '}';
    }

}
