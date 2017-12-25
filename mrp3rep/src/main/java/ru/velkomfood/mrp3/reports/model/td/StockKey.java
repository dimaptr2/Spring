package ru.velkomfood.mrp3.reports.model.td;

import java.io.Serializable;
import java.util.Objects;

public class StockKey implements Serializable {

    private int plant;
    private String warehouse;
    private long materialId;
    private int period;
    private int year;

    public StockKey() {
    }

    public StockKey(int plant, String warehouse,
                    long materialId,
                    int period, int year) {
        this.plant = plant;
        this.warehouse = warehouse;
        this.materialId = materialId;
        this.period = period;
        this.year = year;
    }

    public int getPlant() {
        return plant;
    }

    public void setPlant(int plant) {
        this.plant = plant;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(long materialId) {
        this.materialId = materialId;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockKey stockKey = (StockKey) o;
        return plant == stockKey.plant &&
                materialId == stockKey.materialId &&
                period == stockKey.period &&
                year == stockKey.year &&
                Objects.equals(warehouse, stockKey.warehouse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(plant, warehouse, materialId, period, year);
    }

}
