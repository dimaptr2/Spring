package ru.velkomfood.fin.cash.server.model.transaction;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by dpetrov on 27.06.17.
 */
@Entity
@Table(name = "delivery_item")
public class DeliveryItem implements Serializable {

    // composite primary key contains delivery_id and position number
    @EmbeddedId
    private ItemKey itemKey;

    @Embeddable
    class ItemKey {

        @Column(name = "delivery_id", nullable = false)
        private long deliveryId;
        @Column(nullable = false)
        private long position;

        public ItemKey(long deliveryId, long position) {
            this.deliveryId = deliveryId;
            this.position = position;
        }

        public long getDeliveryId() {
            return deliveryId;
        }

        public void setDeliveryId(long deliveryId) {
            this.deliveryId = deliveryId;
        }

        public long getPosition() {
            return position;
        }

        public void setPosition(long position) {
            this.position = position;
        }

    } // embedded class

    // Delivery fields

    public ItemKey getItemKey() {
        return itemKey;
    }

    public void setItemKey(ItemKey itemKey) {
        this.itemKey = itemKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeliveryItem that = (DeliveryItem) o;

        return itemKey != null ? itemKey.equals(that.itemKey) : that.itemKey == null;
    }

    @Override
    public int hashCode() {
        return itemKey != null ? itemKey.hashCode() : 0;
    }

}
