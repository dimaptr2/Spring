package ru.velkomfood.dms.cache.model;

import java.io.Serializable;

public class DetailKey implements Serializable {

    private long id;
    private long documentId;

    public DetailKey() { }

    public DetailKey(long id, long documentId) {
        this.id = id;
        this.documentId = documentId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DetailKey detailKey = (DetailKey) o;

        if (id != detailKey.id) return false;
        return documentId == detailKey.documentId;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (documentId ^ (documentId >>> 32));
        return result;
    }

}
