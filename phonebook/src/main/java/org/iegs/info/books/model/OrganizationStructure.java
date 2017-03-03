package org.iegs.info.books.model;

import javax.persistence.*;

/**
 * Created by petrovdmitry on 19.02.17.
 */
@Entity
@Table(name = "orgstructure")
public class OrganizationStructure {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;
    @Column(name = "employee_id")
    private Long employeeId;

    public OrganizationStructure() { }

    public OrganizationStructure(Long id, String name, Long employeeId) {
        this.id = id;
        this.name = name;
        this.employeeId = employeeId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

}
