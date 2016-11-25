package ru.velkomfood.copc.ckm.server.model;

import java.sql.Date;

/**
 * Created by dpetrov on 26.08.2016.
 */
public class CostEstimateHeaders {

    private String referenceObject;
    private int costEstimateNumber;
    private String costEstimateType;
    private Date startDate;
    private String version;
    private String variant;
    private Date validFrom;
    private Date validTo;
    private String status;

    public String getReferenceObject() {
        return referenceObject;
    }

    public void setReferenceObject(String referenceObject) {
        this.referenceObject = referenceObject;
    }

    public int getCostEstimateNumber() {
        return costEstimateNumber;
    }

    public void setCostEstimateNumber(int costEstimateNumber) {
        this.costEstimateNumber = costEstimateNumber;
    }

    public String getCostEstimateType() {
        return costEstimateType;
    }

    public void setCostEstimateType(String costEstimateType) {
        this.costEstimateType = costEstimateType;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVariant() {
        return variant;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
