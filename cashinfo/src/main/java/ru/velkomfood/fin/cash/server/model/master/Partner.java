package ru.velkomfood.fin.cash.server.model.master;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by dpetrov on 23.06.17.
 */
@Entity
@Table(name = "partners")
public class Partner implements Serializable {

    @Id
    @Column(length = 12)
    private String id;
    @Column(length = 50)
    private String name;

    @Column(nullable = false)
    private int indicator;  // 1 - customer, 2 - vendor, 3 - employee

    @Column(length = 2)
    private String country;

    @Column(length = 15)
    private String postcode;

    @Column(length = 50)
    private String city;
    @Column(length = 50)
    private String street;

    public Partner() { }

    public Partner(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndicator() {
        return indicator;
    }

    public void setIndicator(int indicator) {
        this.indicator = indicator;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Partner partner = (Partner) o;

        return id != null ? id.equals(partner.id) : partner.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Partner{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", indicator=" + indicator +
                ", postcode='" + postcode + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                '}';
    }

}
