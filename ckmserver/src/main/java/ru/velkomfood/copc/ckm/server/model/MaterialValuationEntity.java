package ru.velkomfood.copc.ckm.server.model;

import java.math.BigDecimal;

/**
 * Created by dpetrov on 26.08.2016.
 */
public class MaterialValuationEntity {

    private int id;
    private int plant;
    private String baseUnit;
    private String priceControl;
    private String currency;
    private BigDecimal movingPrice, fixedPrice, priceUnit;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlant() {
        return plant;
    }

    public void setPlant(int plant) {
        this.plant = plant;
    }

    public String getBaseUnit() {
        return baseUnit;
    }

    public void setBaseUnit(String baseUnit) {
        this.baseUnit = baseUnit;
    }

    public String getPriceControl() {
        return priceControl;
    }

    public void setPriceControl(String priceControl) {
        this.priceControl = priceControl;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getMovingPrice() {
        return movingPrice;
    }

    public void setMovingPrice(BigDecimal movingPrice) {
        this.movingPrice = movingPrice;
    }

    public BigDecimal getFixedPrice() {
        return fixedPrice;
    }

    public void setFixedPrice(BigDecimal fixedPrice) {
        this.fixedPrice = fixedPrice;
    }

    public BigDecimal getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(BigDecimal priceUnit) {
        this.priceUnit = priceUnit;
    }

}
