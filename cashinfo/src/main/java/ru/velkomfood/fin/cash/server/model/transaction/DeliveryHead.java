package ru.velkomfood.fin.cash.server.model.transaction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by dpetrov on 27.06.17.
 */
@Entity
@Table(name = "delivery_head")
public class DeliveryHead implements Serializable {

    @Id
    private long id;
    @Column(name = "delivery_type_id", nullable = false)
    private int deliveryTypeId;


}
