package ru.bin.bank.info.model;

import java.io.Serializable;

/**
 * Created by petrovdmitry on 01.01.17.
 */
public class Employee implements Serializable {

    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;

    public Employee() { }

    public Employee(String firstName, String lastName, String middleName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

}
