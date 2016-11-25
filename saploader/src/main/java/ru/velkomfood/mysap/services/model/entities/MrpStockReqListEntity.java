package ru.velkomfood.mysap.services.model.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * Created by dpetrov on 15.06.16.
 *  This is an entity for secondary requirements
 */

@Entity
@Table(name = "mrpitems")
public class MrpStockReqListEntity {

    @Id
    private long id;

    private int plant;
    private String material;
    private String mrpDate;
    private String baseUnit;
    private String purchaseGroup;

    private String per_segmt;
    private BigDecimal pri_rq_quantity;
    private BigDecimal sec_req_quantity;
    private BigDecimal avail_quantity;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public String getMrpDate() {
        return mrpDate;
    }

    public void setMrpDate(String mrpDate) {
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

    public String getPer_segmt() {
        return per_segmt;
    }

    public void setPer_segmt(String per_segmt) {
        this.per_segmt = per_segmt;
    }

    public BigDecimal getPri_rq_quantity() {
        return pri_rq_quantity;
    }

    public void setPri_rq_quantity(BigDecimal pri_rq_quantity) {
        this.pri_rq_quantity = pri_rq_quantity;
    }

    public BigDecimal getSec_req_quantity() {
        return sec_req_quantity;
    }

    public void setSec_req_quantity(BigDecimal sec_req_quantity) {
        this.sec_req_quantity = sec_req_quantity;
    }

    public BigDecimal getAvail_quantity() {
        return avail_quantity;
    }

    public void setAvail_quantity(BigDecimal avail_quantity) {
        this.avail_quantity = avail_quantity;
    }

}
