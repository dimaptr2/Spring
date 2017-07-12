package ru.velkomfood.fin.cash.server.model.transaction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.sql.Date;

/**
 * Created by dpetrov on 11.07.17.
 */
@Entity
@Table(name = "distr_head")
public class DistributedHead {

    @Id
    private long id;
    @Column(name = "delivery_type_id", nullable = false)
    private int deliveryTypeId;
    @Column(name = "company_id", length = 4, nullable = false)
    private String companyId;
    @Column(name = "partner_id", length = 12, nullable = false)
    private String partnerId;

    @Column(name = "posting_date")
    private java.sql.Date postingDate;

    @Column(name = "amount", precision = 20, scale = 2)
    private BigDecimal totalAmount;

    public DistributedHead() { }

    public DistributedHead(long id, int deliveryTypeId,
                           String companyId, String partnerId,
                           Date postingDate, BigDecimal totalAmount) {
        this.id = id;
        this.deliveryTypeId = deliveryTypeId;
        this.companyId = companyId;
        this.partnerId = partnerId;
        this.postingDate = postingDate;
        this.totalAmount = totalAmount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getDeliveryTypeId() {
        return deliveryTypeId;
    }

    public void setDeliveryTypeId(int deliveryTypeId) {
        this.deliveryTypeId = deliveryTypeId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public Date getPostingDate() {
        return postingDate;
    }

    public void setPostingDate(Date postingDate) {
        this.postingDate = postingDate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DistributedHead that = (DistributedHead) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

}
