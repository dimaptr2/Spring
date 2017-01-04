package ru.bin.bank.info.model;

import java.io.Serializable;

/**
 * Created by petrovdmitry on 02.01.17.
 */
public class Phone implements Serializable {

    private Long id;
    private String phoneNumber;
    private String extension;

    public Phone() { }

    public Phone(String phoneNumber, String extension) {
        this.phoneNumber = phoneNumber;
        this.extension = extension;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

}
