package ru.velkomfood.fin.cash.server.model.transaction;

import javax.persistence.Column;
import java.math.BigDecimal;

/**
 * Created by dpetrov on 10.07.17.
 */
public class PriceInfo {

    @Column(name = "delivery_id")
    private long deliveryId;
    @Column(name = "gross")
    private BigDecimal grossValue;

    public PriceInfo() { }

    public PriceInfo(long deliveryId, BigDecimal grossValue) {
        this.deliveryId = deliveryId;
        this.grossValue = grossValue;
    }

    public long getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(long deliveryId) {
        this.deliveryId = deliveryId;
    }

    public BigDecimal getGrossValue() {
        return grossValue;
    }

    public void setGrossValue(BigDecimal grossValue) {
        this.grossValue = grossValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PriceInfo priceInfo = (PriceInfo) o;

        return deliveryId == priceInfo.deliveryId;
    }

    @Override
    public int hashCode() {
        return (int) (deliveryId ^ (deliveryId >>> 32));
    }

}
