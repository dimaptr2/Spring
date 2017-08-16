package ru.velkomfood.dms.cache.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "limited")
public class LimitedContract implements Serializable {

    @Id
    private long id;

    @Column(length = 10)
    private String vendor;
    @Column(name = "vendor_name", length = 50)
    private String vendorName;
    @Column(length = 30)
    private String contract;
    @Column(nullable = false)
    private java.sql.Date deadline;
    @Column(length = 30)
    private String status;

    public LimitedContract() { }

    public LimitedContract(long id, String vendor,
                           String vendorName, String contract,
                           Date deadline, String status) {
        this.id = id;
        this.vendor = vendor;
        this.vendorName = vendorName;
        this.contract = contract;
        this.deadline = deadline;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LimitedContract that = (LimitedContract) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

}
