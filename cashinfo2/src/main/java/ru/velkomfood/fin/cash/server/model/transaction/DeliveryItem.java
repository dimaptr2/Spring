package ru.velkomfood.fin.cash.server.model.transaction;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by dpetrov on 28.06.17.
 */
@Entity
@Table(name = "delivery_item")
@IdClass(DeliveryItemId.class)
public class DeliveryItem implements Serializable {

    @Id
    private long id;
    @Id
    private long position;

    @Column(name = "material_id", nullable = false)
    private long materialId;

    @Column(length = 50)
    private String description;

    @Column(precision = 20, scale = 3)
    private BigDecimal quantity;

    @Column(precision = 20, scale = 2)
    private BigDecimal price;

    @Column(precision = 20, scale = 2)
    private BigDecimal vat;

    @Column(name = "vat_rate")
    private int vatRate;

    @Column(name = "net_price", precision = 20, scale = 2)
    private BigDecimal netPrice;

    @Column(name = "gross_price", precision = 20, scale = 2)
    private BigDecimal grossPrice;

    public DeliveryItem() { }

    public DeliveryItem(long id, long position,
                        long materialId, String description,
                        BigDecimal quantity, BigDecimal price,
                        BigDecimal vat, int vatRate, BigDecimal netPrice,
                        BigDecimal grossPrice) {
        this.id = id;
        this.position = position;
        this.materialId = materialId;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.vat = vat;
        this.vatRate = vatRate;
        this.netPrice = netPrice;
        this.grossPrice = grossPrice;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getVat() {
        return vat;
    }

    public void setVat(BigDecimal vat) {
        this.vat = vat;
    }

    public int getVatRate() {
        return vatRate;
    }

    public void setVatRate(int vatRate) {
        this.vatRate = vatRate;
    }

    public BigDecimal getNetPrice() {
        return netPrice;
    }

    public void setNetPrice(BigDecimal netPrice) {
        this.netPrice = netPrice;
    }

    public BigDecimal getGrossPrice() {
        return grossPrice;
    }

    public void setGrossPrice(BigDecimal grossPrice) {
        this.grossPrice = grossPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeliveryItem item = (DeliveryItem) o;

        if (id != item.id) return false;
        return position == item.position;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (position ^ (position >>> 32));
        return result;
    }

}
