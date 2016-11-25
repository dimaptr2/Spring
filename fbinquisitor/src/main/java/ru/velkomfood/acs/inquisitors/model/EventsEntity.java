package ru.velkomfood.acs.inquisitors.model;

import java.util.Date;

/**
 * Created by Velkomfood on 27.06.16.
 */

public class EventsEntity {

    private long regid;
    private long uid;
    private long doorid;
    private Date regdate;
    private Date regdatefull;
    private Date regtime;
    private Date regtimefull;

    public long getRegid() {
        return regid;
    }

    public void setRegid(long regid) {
        this.regid = regid;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public long getDoorid() {
        return doorid;
    }

    public void setDoorid(long doorid) {
        this.doorid = doorid;
    }

    public Date getRegdate() {
        return regdate;
    }

    public void setRegdate(Date regdate) {
        this.regdate = regdate;
    }

    public Date getRegdatefull() {
        return regdatefull;
    }

    public void setRegdatefull(Date regdatefull) {
        this.regdatefull = regdatefull;
    }

    public Date getRegtime() {
        return regtime;
    }

    public void setRegtime(Date regtime) {
        this.regtime = regtime;
    }

    public Date getRegtimefull() {
        return regtimefull;
    }

    public void setRegtimefull(Date regtimefull) {
        this.regtimefull = regtimefull;
    }
}
