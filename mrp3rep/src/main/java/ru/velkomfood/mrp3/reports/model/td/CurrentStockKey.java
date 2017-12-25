package ru.velkomfood.mrp3.reports.model.td;

import java.io.Serializable;
import java.util.Objects;

public class CurrentStockKey implements Serializable {

    private int plant;
    private String warehouse;
    private long materialId;
    private String stockType;

    public CurrentStockKey() {
    }

    public CurrentStockKey(int plant, String warehouse,
                           long materialId, String stockType) {
        this.plant = plant;
        this.warehouse = warehouse;
        this.materialId = materialId;
        this.stockType = stockType;
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

    public String getStockType() {
        return stockType;
    }

    public void setStockType(String stockType) {
        this.stockType = stockType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrentStockKey that = (CurrentStockKey) o;
        return plant == that.plant &&
                materialId == that.materialId &&
                Objects.equals(warehouse, that.warehouse) &&
                Objects.equals(stockType, that.stockType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(plant, warehouse, materialId, stockType);
    }

}
