package ru.velkomfood.services.mrp2.model;

import java.io.Serializable;

public class RequirementKey implements Serializable {

    private long materialId;
    private int year;
    private int month;

    public RequirementKey() {
    }

    public RequirementKey(long materialId, int year, int month) {
        this.materialId = materialId;
        this.year = year;
        this.month = month;
    }

    public long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(long materialId) {
        this.materialId = materialId;
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

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RequirementKey that = (RequirementKey) o;

        if (materialId != that.materialId) return false;
        if (year != that.year) return false;
        return month == that.month;
    }

    @Override
    public int hashCode() {
        int result = (int) (materialId ^ (materialId >>> 32));
        result = 31 * result + year;
        result = 31 * result + month;
        return result;
    }

}
