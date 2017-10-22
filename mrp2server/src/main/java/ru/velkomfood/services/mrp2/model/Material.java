package ru.velkomfood.services.mrp2.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "materials")
public class Material implements Serializable {

    @Id
    private long id;
    @Column(length = 50)
    private String description;
    @Column(length = 3)
    private String uom;
    @Column(precision = 20, scale = 3)
    private BigDecimal unit;
    @Column(precision = 20, scale = 2)
    private BigDecimal cost;

    public Material() {
    }

    public Material(long id, String description,
                    String uom, BigDecimal unit,
                    BigDecimal cost) {
        this.id = id;
        this.description = description;
        this.uom = uom;
        this.unit = unit;
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

    public BigDecimal getUnit() {
        return unit;
    }

    public void setUnit(BigDecimal unit) {
        this.unit = unit;
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
