package ru.velkomfood.dms.cache.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "details")
@IdClass(DetailKey.class)
public class Detail implements Serializable {

    @Id
    private long id;
    @Id
    @Column(name = "document_id", nullable = false)
    private long documentId;

    @Column(length = 30)
    private String characteristic;
    @Column(length = 30)
    private String value;

    public Detail() { }

    public Detail(long documentId,
                  String characteristic, String value) {
        this.documentId = documentId;
        this.characteristic = characteristic;
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(long documentId) {
        this.documentId = documentId;
    }

    public String getCharacteristic() {
        return characteristic;
    }

    public void setCharacteristic(String characteristic) {
        this.characteristic = characteristic;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Detail detail = (Detail) o;

        if (id != detail.id) return false;
        return documentId == detail.documentId;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (documentId ^ (documentId >>> 32));
        return result;
    }

}
