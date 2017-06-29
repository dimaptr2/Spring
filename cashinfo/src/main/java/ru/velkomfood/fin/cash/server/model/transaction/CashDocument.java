package ru.velkomfood.fin.cash.server.model.transaction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

/**
 * Created by dpetrov on 26.06.17.
 */
@Entity
@Table(name = "cash_journal")
public class CashDocument implements Serializable {

    @Id
    private long id;

    @Column(name = "cajo_number", length = 4)
    private String cajoNumber;
    @Column(name = "company_id", length = 4, nullable = false)
    private String companyId;

    private int year;

    @Column(name = "posting_date")
    private java.sql.Date postingDate;

    @Column(name = "position_text", length = 50)
    private String positionText;
    @Column(name = "delivery_id")
    private long deliveryId;

    // Amount from the receipt
    @Column(precision = 20, scale = 2)
    private BigDecimal amount;

    public CashDocument() { }

    public CashDocument(long id, String cajoNumber, String companyId,
                        int year, Date postingDate, long deliveryId, BigDecimal amount) {
        this.id = id;
        this.cajoNumber = cajoNumber;
        this.companyId = companyId;
        this.year = year;
        this.postingDate = postingDate;
        this.deliveryId = deliveryId;
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCajoNumber() {
        return cajoNumber;
    }

    public void setCajoNumber(String cajoNumber) {
        this.cajoNumber = cajoNumber;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Date getPostingDate() {
        return postingDate;
    }

    public void setPostingDate(Date postingDate) {
        this.postingDate = postingDate;
    }

    public String getPositionText() {
        return positionText;
    }

    public void setPositionText(String positionText) {
        this.positionText = positionText;
    }

    public long getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(long deliveryId) {
        this.deliveryId = deliveryId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CashDocument that = (CashDocument) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }


}
