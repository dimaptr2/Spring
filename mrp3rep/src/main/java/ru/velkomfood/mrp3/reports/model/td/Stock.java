package ru.velkomfood.mrp3.reports.model.td;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "stocks")
@IdClass(StockKey.class)
public class Stock implements Serializable {

    @Id
    private int plant;
    @Id
    @Column(length = 4)
    private String warehouse;
    @Id
    @Column(name = "material_id")
    private long materialId;
    @Id
    private int period;
    @Id
    private int year;

    @Column(length = 3)
    private String uom;
    @Column(precision = 20, scale = 3)
    private BigDecimal free;
    @Column(precision = 20, scale = 3)
    private BigDecimal moving;
    @Column(precision = 20, scale = 3)
    private BigDecimal quality;
    @Column(precision = 20, scale = 3)
    private BigDecimal block;

    public Stock() {
    }

    public Stock(int plant, String warehouse,
                 long materialId, int period,
                 int year, String uom,
                 BigDecimal free, BigDecimal moving,
                 BigDecimal quality, BigDecimal block) {
        this.plant = plant;
        this.warehouse = warehouse;
        this.materialId = materialId;
        this.period = period;
        this.year = year;
        this.uom = uom;
        this.free = free;
        this.moving = moving;
        this.quality = quality;
        this.block = block;
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

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public BigDecimal getFree() {
        return free;
    }

    public void setFree(BigDecimal free) {
        this.free = free;
    }

    public BigDecimal getMoving() {
        return moving;
    }

    public void setMoving(BigDecimal moving) {
        this.moving = moving;
    }

    public BigDecimal getQuality() {
        return quality;
    }

    public void setQuality(BigDecimal quality) {
        this.quality = quality;
    }

    public BigDecimal getBlock() {
        return block;
    }

    public void setBlock(BigDecimal block) {
        this.block = block;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return plant == stock.plant &&
                materialId == stock.materialId &&
                period == stock.period &&
                year == stock.year &&
                Objects.equals(warehouse, stock.warehouse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(plant, warehouse, materialId, period, year);
    }

}
