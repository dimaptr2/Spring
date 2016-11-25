package ru.velkomfood.mysap.mrp.model;

import java.math.BigDecimal;

/**
 * Created by dpetrov on 21.10.16.
 */
public class Stock {

    private int id;
    private int material;
    private int plant;
    private int storage;
    private int year;
    private int month;
    private BigDecimal freeStock;
    private BigDecimal qualityStock;
    private BigDecimal blockedStock;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMaterial() {
        return material;
    }

    public void setMaterial(int material) {
        this.material = material;
    }

    public int getPlant() {
        return plant;
    }

    public void setPlant(int plant) {
        this.plant = plant;
    }

    public int getStorage() {
        return storage;
    }

    public void setStorage(int storage) {
        this.storage = storage;
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

    public BigDecimal getFreeStock() {
        return freeStock;
    }

    public void setFreeStock(BigDecimal freeStock) {
        this.freeStock = freeStock;
    }

    public BigDecimal getQualityStock() {
        return qualityStock;
    }

    public void setQualityStock(BigDecimal qualityStock) {
        this.qualityStock = qualityStock;
    }

    public BigDecimal getBlockedStock() {
        return blockedStock;
    }

    public void setBlockedStock(BigDecimal blockedStock) {
        this.blockedStock = blockedStock;
    }

}
