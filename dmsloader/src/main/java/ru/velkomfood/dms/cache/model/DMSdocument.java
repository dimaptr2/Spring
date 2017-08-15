package ru.velkomfood.dms.cache.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "documents")
public class DMSdocument implements Serializable {

    @Id
    private long id;
    @Column(name = "doc_type", length = 3)
    private String docType;
    @Column(name = "doc_part", length = 3)
    private String docPart;
    @Column(length = 2)
    private String version;
    @Column(length = 50)
    private String description;
    @Column(length = 50)
    private String user;

    public DMSdocument() { }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getDocPart() {
        return docPart;
    }

    public void setDocPart(String docPart) {
        this.docPart = docPart;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DMSdocument that = (DMSdocument) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

}
