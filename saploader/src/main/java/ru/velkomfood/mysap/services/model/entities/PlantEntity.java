package ru.velkomfood.mysap.services.model.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by DPetrov on 08.06.2016.
 */

@Entity
@Table(name = "t001w")
public class PlantEntity {

    @Id
    private int werks;

    private String name;

    public int getWerks() {
        return werks;
    }

    public void setWerks(int werks) {
        this.werks = werks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
