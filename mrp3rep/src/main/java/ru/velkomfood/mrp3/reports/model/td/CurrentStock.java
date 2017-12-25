package ru.velkomfood.mrp3.reports.model.td;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "current_stocks")
@IdClass(CurrentStockKey.class)
public class CurrentStock implements Serializable {

    @Id
    private int plant;
    @Id
    @Column(length = 4)
    private String warehouse;
    @Id
    @Column(name = "material_id")
    private long materialId;
    @Id
    @Column(name = "stock_type", length = 10, nullable = false)
    private String stockType;

    @Column(length = 3)
    private String uom;


    @Column(precision = 20, scale = 3)
    private BigDecimal quantity;


    public CurrentStock() {
    }

    public CurrentStock(int plant, String warehouse,
                        long materialId, String stockType,
                        String uom, BigDecimal quantity) {
        this.plant = plant;
        this.warehouse = warehouse;
        this.materialId = materialId;
        this.stockType = stockType;
        this.uom = uom;
        this.quantity = quantity;
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

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrentStock that = (CurrentStock) o;
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
