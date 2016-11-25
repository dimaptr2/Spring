package ru.velkomfood.mysap.services.model.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by dpetrov on 21.06.16.
 */

@Entity
@Table(name = "pur_groups")
public class PurchaseGroupEntity {

    @Id
    private String id;
    private String description;

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

}
