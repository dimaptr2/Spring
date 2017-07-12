package ru.velkomfood.fin.cash.server.model.transaction;

import java.math.BigDecimal;

/**
 * Created by dpetrov on 10.07.17.
 */
public class PriceInfo {

    private long deliveryId;
    private long position;
    private long materialId;
    private BigDecimal price;
    private BigDecimal netValue;
    private BigDecimal vat;
    private BigDecimal grossValue;
    private int vatRate;

    public PriceInfo() { }

    public PriceInfo(long deliveryId, long position,
                     long materialId, BigDecimal price,
                     BigDecimal netValue, BigDecimal vat,
                     BigDecimal grossValue, int vatRate) {
        this.deliveryId = deliveryId;
        this.position = position;
        this.materialId = materialId;
        this.price = price;
        this.netValue = netValue;
        this.vat = vat;
        this.grossValue = grossValue;
        this.vatRate = vatRate;
    }

    public long getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(long deliveryId) {
        this.deliveryId = deliveryId;
    }

    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    public long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(long materialId) {
        this.materialId = materialId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getNetValue() {
        return netValue;
    }

    public void setNetValue(BigDecimal netValue) {
        this.netValue = netValue;
    }

    public BigDecimal getVat() {
        return vat;
    }

    public void setVat(BigDecimal vat) {
        this.vat = vat;
    }

    public BigDecimal getGrossValue() {
        return grossValue;
    }

    public void setGrossValue(BigDecimal grossValue) {
        this.grossValue = grossValue;
    }

    public int getVatRate() {
        return vatRate;
    }

    public void setVatRate(int vatRate) {
        this.vatRate = vatRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PriceInfo priceInfo = (PriceInfo) o;

        if (deliveryId != priceInfo.deliveryId) return false;
        if (position != priceInfo.position) return false;
        return materialId == priceInfo.materialId;
    }

    @Override
    public int hashCode() {
        int result = (int) (deliveryId ^ (deliveryId >>> 32));
        result = 31 * result + (int) (position ^ (position >>> 32));
        result = 31 * result + (int) (materialId ^ (materialId >>> 32));
        return result;
    }

}
