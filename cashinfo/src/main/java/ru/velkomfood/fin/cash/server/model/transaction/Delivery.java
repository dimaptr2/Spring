package ru.velkomfood.fin.cash.server.model.transaction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dpetrov on 28.06.17.
 */
public class Delivery implements Serializable {

    private DeliveryHead head;
    private List<DeliveryItem> items;

    public Delivery() {
        head = new DeliveryHead();
        items = new ArrayList<>();
    }

    public Delivery(DeliveryHead head) {
        this.head = head;
        items = new ArrayList<>();
    }

    public DeliveryHead getHead() {
        return head;
    }

    public void setHead(DeliveryHead head) {
        this.head = head;
    }

    public List<DeliveryItem> getItems() {
        return items;
    }

    public void setItems(List<DeliveryItem> items) {
        this.items = items;
    }

    public void addItem(DeliveryItem it) {
        items.add(it);
    }

    public void removeItem(DeliveryItem it) {
        items.remove(it);
    }

    public void removeItemByIndex(int index) {
        items.remove(index);
    }

    public DeliveryItem getItemByIndex(int index) {
        return items.get(index);
    }

    public void clearItems() {
        items.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Delivery delivery = (Delivery) o;

        return head.equals(delivery.head);
    }

    @Override
    public int hashCode() {
        return head.hashCode();
    }

}
