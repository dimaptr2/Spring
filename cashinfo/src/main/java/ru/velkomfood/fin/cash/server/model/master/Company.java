package ru.velkomfood.fin.cash.server.model.master;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by dpetrov on 26.06.17.
 */

@Entity
@Table(name = "companies")
public class Company {

    @Id
    @Column(length = 4)
    private String id;
    @Column(length = 50)
    private String name1;
    @Column(length = 50)
    private String name2;
    @Column(length = 2)
    private String country;
    @Column(length = 15)
    private String postcode;
    @Column(length = 50)
    private String city;
    @Column(length = 50)
    private String street;
    @Column(length = 50)
    private String building;
    @Column(length = 30)
    private String phone;
    @Column(length = 30)
    private String fax;

    public Company() { }

    public Company(String id,
                   String name1, String name2, String country,
                   String postcode, String city,
                   String street, String building,
                   String phone, String fax) {
        this.id = id;
        this.name1 = name1;
        this.name2 = name2;
        this.country = country;
        this.postcode = postcode;
        this.city = city;
        this.street = street;
        this.building = building;
        this.phone = phone;
        this.fax = fax;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Company company = (Company) o;

        return id != null ? id.equals(company.id) : company.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id='" + id + '\'' +
                ", name1='" + name1 + '\'' +
                ", name2='" + name2 + '\'' +
                ", country='" + country + '\'' +
                ", postcode='" + postcode + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", building='" + building + '\'' +
                ", phone='" + phone + '\'' +
                ", fax='" + fax + '\'' +
                '}';
    }

}
