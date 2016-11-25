package ru.velkomfood.mysap.services.model.entities;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by dpetrov on 22.06.16.
 */

public class MrpRequestEntity {

    private int werks;
    private String matnr;
    private String maktx;
    private Date mrpDate;
    private String baseUnit;
    private String purchaseGroup;
    private String pgrpDescription;

    private String per_segmt;
    private BigDecimal pri_req_quantity;
    private BigDecimal sec_req_quantity;
    private BigDecimal avail_quantity;

    public int getWerks() {
        return werks;
    }

    public void setWerks(int werks) {
        this.werks = werks;
    }

    public String getMatnr() {
        return matnr;
    }

    public void setMatnr(String matnr) {
        this.matnr = matnr;
    }

    public String getMaktx() {
        return maktx;
    }

    public void setMaktx(String maktx) {
        this.maktx = maktx;
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

    public String getPgrpDescription() {
        return pgrpDescription;
    }

    public void setPgrpDescription(String pgrpDescription) {
        this.pgrpDescription = pgrpDescription;
    }

    public String getPer_segmt() {
        return per_segmt;
    }

    public void setPer_segmt(String per_segmt) {
        this.per_segmt = per_segmt;
    }

    public BigDecimal getPri_req_quantity() {
        return pri_req_quantity;
    }

    public void setPri_req_quantity(BigDecimal pri_rq_quantity) {
        this.pri_req_quantity = pri_rq_quantity;
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
