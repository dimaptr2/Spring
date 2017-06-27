package ru.velkomfood.fin.cash.server.model.master;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by dpetrov on 26.06.17.
 */
@Entity
@Table(name = "delivery_type")
public class DeliveryType implements Serializable {

    // 1 - inbound delivery
    // 2 - outbound delivery

    @Id
    private int id;
    @Column(length = 50)
    private String description;

    public DeliveryType() { }

    public DeliveryType(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeliveryType that = (DeliveryType) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "DeliveryType{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }

}
