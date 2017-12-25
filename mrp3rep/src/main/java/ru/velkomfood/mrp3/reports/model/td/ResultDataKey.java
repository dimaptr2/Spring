package ru.velkomfood.mrp3.reports.model.td;

import java.io.Serializable;
import java.util.Objects;

public class ResultDataKey implements Serializable {

    private long materialId;
    private String purchaseGroup;
    private int month;
    private int year;

    public ResultDataKey() {
    }

    public ResultDataKey(long materialId, String purchaseGroup,
                         int month, int year) {
        this.materialId = materialId;
        this.purchaseGroup = purchaseGroup;
        this.month = month;
        this.year = year;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResultDataKey that = (ResultDataKey) o;
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
