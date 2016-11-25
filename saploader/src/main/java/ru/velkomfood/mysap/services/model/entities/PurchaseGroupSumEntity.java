package ru.velkomfood.mysap.services.model.entities;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by dpetrov on 08.07.16.
 */

public class PurchaseGroupSumEntity {

    private String purId;
    private Date availableDate;
    private BigDecimal psum;
    private BigDecimal ssum;
    private BigDecimal asum;

    public String getPurId() {
        return purId;
    }

    public void setPurId(String purId) {
        this.purId = purId;
    }

    public Date getAvailableDate() {
        return availableDate;
    }

    public void setAvailableDate(Date availableDate) {
        this.availableDate = availableDate;
    }

    public BigDecimal getPsum() {
        return psum;
    }

    public void setPsum(BigDecimal psum) {
        this.psum = psum;
    }

    public BigDecimal getSsum() {
        return ssum;
    }

    public void setSsum(BigDecimal ssum) {
        this.ssum = ssum;
    }

    public BigDecimal getAsum() {
        return asum;
    }

    public void setAsum(BigDecimal asum) {
        this.asum = asum;
    }
}
