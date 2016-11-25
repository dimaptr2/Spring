package ru.velkomfood.acs.inquisitors.model;

/**
 * Created by Velkomfood on 27.06.16.
 */

public class UsersEntity {

    private long uid;
    private String fullname;

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

}
