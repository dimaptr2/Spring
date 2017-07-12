package ru.velkomfood.fin.cash.server.model.master;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by dpetrov on 25.06.17.
 */
@Entity
@Table(name = "partner_type")
public class PartnerType implements Serializable {

    @Id
    private int id;
    @Column(length = 25)
    private String description;

    public PartnerType() { }

    public PartnerType(int id, String description) {
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

        PartnerType that = (PartnerType) o;

        if (id != that.id) return false;
        return description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return id;
    }

}
