package ru.velkomfood.mysap.services.model.entities;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by dpetrov on 11.07.16.
 */

public class MrpTotalEntity {

    private int plant;
    private String material;
    private Date mrpDate;
    private String materialDescription;
    private String baseUnit;
    private String purchaseGroup;
    private String purDescription;

    private BigDecimal primaryReq;
    private BigDecimal secondaryReq;
    private BigDecimal availableQuantity;

    public int getPlant() {
        return plant;
    }

    public void setPlant(int plant) {
        this.plant = plant;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getMaterialDescription() {
        return materialDescription;
    }

    public void setMaterialDescription(String materialDescription) {
        this.materialDescription = materialDescription;
    }

    public Date getMrpDate() {
        return mrpDate;
    }

    public void setMrpDate(Date mrpDate) {
        this.mrpDate = mrpDate;
    }

    public String getBaseUnit() {
        return baseUnit;
    }

    public void setBaseUnit(String baseUnit) {
        this.baseUnit = baseUnit;
    }

    public String getPurchaseGroup() {
        return purchaseGroup;
    }

    public void setPurchaseGroup(String purchaseGroup) {
        this.purchaseGroup = purchaseGroup;
    }

    public String getPurDescription() {
        return purDescription;
    }

    public void setPurDescription(String purDescription) {
        this.purDescription = purDescription;
    }

    public BigDecimal getPrimaryReq() {
        return primaryReq;
    }

    public void setPrimaryReq(BigDecimal primaryReq) {
        this.primaryReq = primaryReq;
    }

    public BigDecimal getSecondaryReq() {
        return secondaryReq;
    }

    public void setSecondaryReq(BigDecimal secondaryReq) {
        this.secondaryReq = secondaryReq;
    }

    public BigDecimal getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(BigDecimal availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

}
