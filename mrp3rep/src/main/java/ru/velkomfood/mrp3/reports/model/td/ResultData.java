package ru.velkomfood.mrp3.reports.model.td;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "requirements")
@IdClass(ResultDataKey.class)
public class ResultData {

    @Id
    @Column(name = "material_id")
    private long materialId;
    @Id
    @Column(name = "pur_group", length = 3)
    private String purchaseGroup;
    @Id
    private int month;
    @Id
    private int year;

    @Column(length = 3)
    private String uom;

    @Column(precision = 20, scale = 3)
    private BigDecimal quantity;

    public ResultData() {
    }

    public ResultData(long materialId, String purchaseGroup,
                      int month, int year,
                      String uom, BigDecimal quantity) {
        this.materialId = materialId;
        this.purchaseGroup = purchaseGroup;
        this.month = month;
        this.year = year;
        this.uom = uom;
        this.quantity = quantity;
    }

    public long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(long materialId) {
        this.materialId = materialId;
    }

    public String getPurchaseGroup() {
        return purchaseGroup;
    }

    public void setPurchaseGroup(String purchaseGroup) {
        this.purchaseGroup = purchaseGroup;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
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

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResultData that = (ResultData) o;
        return materialId == that.materialId &&
                month == that.month &&
                year == that.year &&
                Objects.equals(purchaseGroup, that.purchaseGroup);
    }

    @Override
    public int hashCode() {
        return Objects.hash(materialId, purchaseGroup, month, year);
    }

}
