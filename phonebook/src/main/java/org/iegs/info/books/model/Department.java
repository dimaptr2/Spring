package org.iegs.info.books.model;

import javax.persistence.*;

/**
 * Created by petrovdmitry on 18.02.17.
 */
@Entity
@Table(name = "departments")
public class Department {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @Column(name = "name")
    private String name;

    public Department() { }

    public Department(Long id, String name) {
        this.id = id;
        this.name = name;
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

}
