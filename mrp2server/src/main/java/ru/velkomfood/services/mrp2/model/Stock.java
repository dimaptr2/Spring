package ru.velkomfood.services.mrp2.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "stocks")
@IdClass(StockKey.class)
public class Stock implements Serializable {

    @Id
    @Column(name = "material_id")
    private long materialId;
    @Id
    private int warehouse;
    @Id
    private int year;
    @Id
    private int month;

    @Column(precision = 20, scale = 3)
    private BigDecimal value;

    public Stock() {
    }

    public Stock(long materialId, int warehouse,
                 int year, int month,
                 BigDecimal value) {
        this.materialId = materialId;
        this.warehouse = warehouse;
        this.year = year;
        this.month = month;
        this.value = value;
    }

    public long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(long materialId) {
        this.materialId = materialId;
    }

    public int getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(int warehouse) {
        this.warehouse = warehouse;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Stock stock = (Stock) o;

        if (materialId != stock.materialId) return false;
        if (warehouse != stock.warehouse) return false;
        if (year != stock.year) return false;
        return month == stock.month;
    }

    @Override
    public int hashCode() {
        int result = (int) (materialId ^ (materialId >>> 32));
        result = 31 * result + warehouse;
        result = 31 * result + year;
        result = 31 * result + month;
        return result;
    }

}
