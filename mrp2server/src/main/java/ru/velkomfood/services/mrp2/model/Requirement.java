package ru.velkomfood.services.mrp2.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "requirements")
@IdClass(RequirementKey.class)
public class Requirement implements Serializable {

    @Id
    @Column(name = "material_id")
    private long materialId;
    @Id
    @Column(name = "pur_group_id", length = 3)
    private String purchaseGroupId;
    @Id
    private int year;
    @Id
    private int month;


    @Column(precision = 20, scale = 3)
    private BigDecimal value;

    public Requirement() {
    }

    public Requirement(long materialId, String purchaseGroupId,
                       int year, int month,
                       BigDecimal value) {
        this.materialId = materialId;
        this.purchaseGroupId = purchaseGroupId;
        this.year = year;
        this.month = month;
        this.value = value;
    }

    public long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(long materialId) {
        this.materialId = materialId;
    }

    public String getPurchaseGroupId() {
        return purchaseGroupId;
    }

    public void setPurchaseGroupId(String purchaseGroupId) {
        this.purchaseGroupId = purchaseGroupId;
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

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Requirement that = (Requirement) o;

        if (materialId != that.materialId) return false;
        if (year != that.year) return false;
        if (month != that.month) return false;
        return purchaseGroupId.equals(that.purchaseGroupId);
    }

    @Override
    public int hashCode() {
        int result = (int) (materialId ^ (materialId >>> 32));
        result = 31 * result + purchaseGroupId.hashCode();
        result = 31 * result + year;
        result = 31 * result + month;
        return result;
    }

}
