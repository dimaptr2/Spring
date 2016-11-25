package ru.velkomfood.mysap.mrp.model;

import java.math.BigDecimal;

/**
 * Created by dpetrov on 20.10.16.
 */


public class Valuations {

    private int matnr;
    private int plant;
    private int baseUom;
    private String purchaseGroup;
    private String priceControl;
    private BigDecimal fixedPrice;
    private BigDecimal weightedPrice;
    private BigDecimal priceUnit;

    public int getMatnr() {
        return matnr;
    }

    public void setMatnr(int matnr) {
        this.matnr = matnr;
    }

    public int getPlant() {
        return plant;
    }

    public void setPlant(int plant) {
        this.plant = plant;
    }

    public int getBaseUom() {
        return baseUom;
    }

    public void setBaseUom(int baseUom) {
        this.baseUom = baseUom;
    }

    public String getPurchaseGroup() {
        return purchaseGroup;
    }

    public void setPurchaseGroup(String purchaseGroup) {
        this.purchaseGroup = purchaseGroup;
    }

    public String getPriceControl() {
        return priceControl;
    }

    public void setPriceControl(String priceControl) {
        this.priceControl = priceControl;
    }

    public BigDecimal getFixedPrice() {
        return fixedPrice;
    }

    public void setFixedPrice(BigDecimal fixedPrice) {
        this.fixedPrice = fixedPrice;
    }

    public BigDecimal getWeightedPrice() {
        return weightedPrice;
    }

    public void setWeightedPrice(BigDecimal weightedPrice) {
        this.weightedPrice = weightedPrice;
    }

    public BigDecimal getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(BigDecimal priceUnit) {
        this.priceUnit = priceUnit;
    }

}
