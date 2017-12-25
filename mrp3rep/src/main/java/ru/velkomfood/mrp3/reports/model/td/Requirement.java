package ru.velkomfood.mrp3.reports.model.td;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "mrp2total")
@IdClass(RequirementKey.class)
public class Requirement implements Serializable {

    @Id
    private int plant;
    @Id
    @Column(name = "material_id")
    private long material;
    @Id
    @Column(name = "pur_group", length = 3)
    private String purchaseGroup;
    @Id
    private int year;

    @Column(length = 3)
    private String uom;

    @Column(precision = 20, scale = 3)
    private BigDecimal req01;
    @Column(precision = 20, scale = 3)
    private BigDecimal req02;
    @Column(precision = 20, scale = 3)
    private BigDecimal req03;
    @Column(precision = 20, scale = 3)
    private BigDecimal req04;
    @Column(precision = 20, scale = 3)
    private BigDecimal req05;
    @Column(precision = 20, scale = 3)
    private BigDecimal req06;
    @Column(precision = 20, scale = 3)
    private BigDecimal req07;
    @Column(precision = 20, scale = 3)
    private BigDecimal req08;
    @Column(precision = 20, scale = 3)
    private BigDecimal req09;
    @Column(precision = 20, scale = 3)
    private BigDecimal req10;
    @Column(precision = 20, scale = 3)
    private BigDecimal req11;
    @Column(precision = 20, scale = 3)
    private BigDecimal req12;

    public Requirement() {
    }

    public Requirement(int plant, long material,
                       String purchaseGroup, int year,
                       String uom,
                       BigDecimal req01, BigDecimal req02,
                       BigDecimal req03, BigDecimal req04,
                       BigDecimal req05, BigDecimal req06,
                       BigDecimal req07, BigDecimal req08,
                       BigDecimal req09, BigDecimal req10,
                       BigDecimal req11, BigDecimal req12) {
        this.plant = plant;
        this.material = material;
        this.purchaseGroup = purchaseGroup;
        this.year = year;
        this.uom = uom;
        this.req01 = req01;
        this.req02 = req02;
        this.req03 = req03;
        this.req04 = req04;
        this.req05 = req05;
        this.req06 = req06;
        this.req07 = req07;
        this.req08 = req08;
        this.req09 = req09;
        this.req10 = req10;
        this.req11 = req11;
        this.req12 = req12;
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

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public BigDecimal getReq01() {
        return req01;
    }

    public void setReq01(BigDecimal req01) {
        this.req01 = req01;
    }

    public BigDecimal getReq02() {
        return req02;
    }

    public void setReq02(BigDecimal req02) {
        this.req02 = req02;
    }

    public BigDecimal getReq03() {
        return req03;
    }

    public void setReq03(BigDecimal req03) {
        this.req03 = req03;
    }

    public BigDecimal getReq04() {
        return req04;
    }

    public void setReq04(BigDecimal req04) {
        this.req04 = req04;
    }

    public BigDecimal getReq05() {
        return req05;
    }

    public void setReq05(BigDecimal req05) {
        this.req05 = req05;
    }

    public BigDecimal getReq06() {
        return req06;
    }

    public void setReq06(BigDecimal req06) {
        this.req06 = req06;
    }

    public BigDecimal getReq07() {
        return req07;
    }

    public void setReq07(BigDecimal req07) {
        this.req07 = req07;
    }

    public BigDecimal getReq08() {
        return req08;
    }

    public void setReq08(BigDecimal req08) {
        this.req08 = req08;
    }

    public BigDecimal getReq09() {
        return req09;
    }

    public void setReq09(BigDecimal req09) {
        this.req09 = req09;
    }

    public BigDecimal getReq10() {
        return req10;
    }

    public void setReq10(BigDecimal req10) {
        this.req10 = req10;
    }

    public BigDecimal getReq11() {
        return req11;
    }

    public void setReq11(BigDecimal req11) {
        this.req11 = req11;
    }

    public BigDecimal getReq12() {
        return req12;
    }

    public void setReq12(BigDecimal req12) {
        this.req12 = req12;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Requirement that = (Requirement) o;
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
