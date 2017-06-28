package ru.velkomfood.fin.cash.server.model.transaction;

import java.io.Serializable;

/**
 * Created by dpetrov on 28.06.17.
 */

public class DeliveryItemId implements Serializable {

    private long id;
    private long position;

    public DeliveryItemId() { }

    public DeliveryItemId(long id, long position) {
        this.id = id;
        this.position = position;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeliveryItemId that = (DeliveryItemId) o;

        if (id != that.id) return false;
        return position == that.position;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (position ^ (position >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "DeliveryItemId{" +
                "id=" + id +
                ", position=" + position +
                '}';
    }

}
