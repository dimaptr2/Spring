package ru.velkomfood.fin.cash.server.model.master;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by dpetrov on 23.06.17.
 */
@Entity
@Table(name = "materials")
public class Material implements Serializable {

    @Id
    private long id;
    @Column(length = 50)
    private String description;
    @Column(length = 3)
    private String uom;

    @Column(name = "price_unit", precision = 20, scale = 3)
    private BigDecimal priceUnit;

    @Column(precision = 20, scale = 2)
    private BigDecimal cost;

    public Material() { }

    public Material(long id, String description,
                    String uom,
                    BigDecimal priceUnit,
                    BigDecimal cost) {
        this.id = id;
        this.description = description;
        this.uom = uom;
        this.priceUnit = priceUnit;
        this.cost = cost;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public BigDecimal getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(BigDecimal priceUnit) {
        this.priceUnit = priceUnit;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Material material = (Material) o;

        return id == material.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

}
