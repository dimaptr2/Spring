package ru.velkomfood.fin.cash.server.model.transaction;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by dpetrov on 28.06.17.
 */
public interface IDeliveryItemRepository extends CrudRepository<DeliveryItem, DeliveryItemId> {

    List<DeliveryItem> findAll();
    List<DeliveryItem> findDeliveryItemById(DeliveryItemId itemId);

}
