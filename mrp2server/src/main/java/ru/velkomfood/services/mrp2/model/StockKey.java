package ru.velkomfood.services.mrp2.model;

import java.io.Serializable;

public class StockKey implements Serializable {

    private long materialId;
    private int warehouse;
    private int year;
    private int month;

    public StockKey() {
    }

    public StockKey(long materialId, int warehouse,
                    int year, int month) {
        this.materialId = materialId;
        this.warehouse = warehouse;
        this.year = year;
        this.month = month;
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

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StockKey stockKey = (StockKey) o;

        if (materialId != stockKey.materialId) return false;
        if (warehouse != stockKey.warehouse) return false;
        if (year != stockKey.year) return false;
        return month == stockKey.month;
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
