package ru.velkomfood.mrp3.reports.model.td;

import java.io.Serializable;
import java.util.Objects;

public class RequirementKey implements Serializable {

    private int plant;
    private long material;
    private String purchaseGroup;
    private int year;

    public RequirementKey() {
    }

    public RequirementKey(int plant, long material,
                          String purchaseGroup, int year) {
        this.plant = plant;
        this.material = material;
        this.purchaseGroup = purchaseGroup;
        this.year = year;
    }

    public int getPlant() {
        return plant;
    }

    public void setPlant(int plant) {
        this.plant = plant;
    }

    public long getMaterial() {
        return material;
    }

    public void setMaterial(long material) {
        this.material = material;
    }

    public String getPurchaseGroup() {
        return purchaseGroup;
    }

    public void setPurchaseGroup(String purchaseGroup) {
        this.purchaseGroup = purchaseGroup;
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
        RequirementKey that = (RequirementKey) o;
        return plant == that.plant &&
                material == that.material &&
                year == that.year &&
                Objects.equals(purchaseGroup, that.purchaseGroup);
    }

    @Override
    public int hashCode() {
        return Objects.hash(plant, material, purchaseGroup, year);
    }

}
