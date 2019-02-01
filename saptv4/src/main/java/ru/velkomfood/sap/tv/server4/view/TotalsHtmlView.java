package ru.velkomfood.sap.tv.server4.view;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.Objects;

public class TotalsHtmlView implements Serializable {

    private String material;
    private java.sql.Date date;
    private java.sql.Time time;
    private String description;
    private String uom;
    private BigDecimal packed;
    private BigDecimal quantity;
    private BigDecimal inProcess;
    private BigDecimal transferred;

    public TotalsHtmlView() {
    }

    public TotalsHtmlView(String material, Date date, Time time,
                          String description, String uom,
                          BigDecimal packed, BigDecimal quantity,
                          BigDecimal inProcess, BigDecimal transferred) {
        this.material = material;
        this.date = date;
        this.time = time;
        this.description = description;
        this.uom = uom;
        this.packed = packed;
        this.quantity = quantity;
        this.inProcess = inProcess;
        this.transferred = transferred;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
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
        TotalsHtmlView that = (TotalsHtmlView) o;
        return material.equals(that.material) &&
                date.equals(that.date) &&
                Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(material, date, time);
    }

    @Override
    public String toString() {
        return "TotalsHtmlView{" +
                "material='" + material + '\'' +
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
