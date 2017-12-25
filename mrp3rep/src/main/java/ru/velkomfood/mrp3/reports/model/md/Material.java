package ru.velkomfood.mrp3.reports.model.md;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "materials")
public class Material implements Serializable {

    @Id
    private long id;
    @Column(length = 50)
    private String description;

    public Material() {
    }

    public Material(long id, String description) {
        this.id = id;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
        Material material = (Material) o;
        return id == material.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
