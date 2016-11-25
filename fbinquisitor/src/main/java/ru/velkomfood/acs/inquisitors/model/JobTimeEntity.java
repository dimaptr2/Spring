package ru.velkomfood.acs.inquisitors.model;

import java.util.Date;

/**
 * Created by dpetrov on 19.07.16.
 */

public class JobTimeEntity {

    private long sfcode;
    private Date startDate;
    private Date endDate;
    private Date startTime;
    private Date endTime;
    private long uid;

    public long getSfcode() {
        return sfcode;
    }

    public void setSfcode(long sfcode) {
        this.sfcode = sfcode;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

}
