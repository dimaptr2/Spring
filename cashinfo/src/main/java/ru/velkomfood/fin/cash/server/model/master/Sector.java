package ru.velkomfood.fin.cash.server.model.master;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by dpetrov on 30.06.17.
 */

@Entity
@Table(name = "sector")
public class Sector {

    @Id
    @Column(length = 2)
    private String id;
    @Column(length = 50)
    private String description;

    public Sector() { }

    public Sector(String id, String description) {
        this.id = id;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

        Sector sector = (Sector) o;

        return id.equals(sector.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}
