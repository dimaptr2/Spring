package ru.velkomfood.mysap.mrp.model;

/**
 * Created by dpetrov on 21.10.16.
 */
public class Unit {

    private String UomSap;
    private String UomIso;
    private String description;

    public String getUomSap() {
        return UomSap;
    }

    public void setUomSap(String uomSap) {
        UomSap = uomSap;
    }

    public String getUomIso() {
        return UomIso;
    }

    public void setUomIso(String uomIso) {
        UomIso = uomIso;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
