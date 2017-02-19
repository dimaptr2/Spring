package org.iegs.info.books.model;

import javax.persistence.*;

/**
 * Created by petrovdmitry on 18.02.17.
 */
@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "middle_name")
    private  String middleName;
    @Column(name = "mobile")
    private String mobile;
    @Column(name = "phone")
    private String phone;
    @Column(name = "extension")
    private String extension;
    @Column(name = "email")
    private String email;

    public Employee() { }

    public Employee(Long id, String firstName, String
            lastName, String middleName,
                    String mobile, String phone, String extension, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.mobile = mobile;
        this.phone = phone;
        this.extension = extension;
        this.email = email;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
