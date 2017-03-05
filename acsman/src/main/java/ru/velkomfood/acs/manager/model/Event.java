package ru.velkomfood.acs.manager.model;

/**
 * Created by velkomfood on 17.02.17.
 */
public class Event {

    private Long regid;
    private Long uid;
    private Long doorId;
    private String regDate;
    private String regDateFull;

    public Long getRegid() {
        return regid;
    }

    public void setRegid(Long regid) {
        this.regid = regid;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getDoorId() {
        return doorId;
    }

    public void setDoorId(Long doorId) {
        this.doorId = doorId;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getRegDateFull() {
        return regDateFull;
    }

    public void setRegDateFull(String regDateFull) {
        this.regDateFull = regDateFull;
    }

}
