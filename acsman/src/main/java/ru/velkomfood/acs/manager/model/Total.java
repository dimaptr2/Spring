package ru.velkomfood.acs.manager.model;

/**
 * Created by velkomfood on 17.02.17.
 */
public class Total {

    private Long sfCode;
    private Long uid;
    private String startTime;
    private String endTime;

    public Long getSfCode() {
        return sfCode;
    }

    public void setSfCode(Long sfCode) {
        this.sfCode = sfCode;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

}
