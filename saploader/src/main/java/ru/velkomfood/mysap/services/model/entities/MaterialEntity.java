package ru.velkomfood.mysap.services.model.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by DPetrov on 08.06.2016.
 */

@Entity
@Table(name = "mara")
public class MaterialEntity {

    @Id
    private String matnr;

    private String maktx;


    public String getMatnr() {
        return matnr;
    }

    public void setMatnr(String matnr) {
        this.matnr = matnr;
    }

    public String getMaktx() {
        return maktx;
    }

    public void setMaktx(String maktx) {
        this.maktx = maktx;
    }

}
