package ru.velkomfood.fin.cash.server.model.transaction;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by dpetrov on 03.07.17.
 */
public interface IDistributedItemRepository extends CrudRepository<DistributedItem, DeliveryItemId> {

    List<DistributedItem> findAll();
    List<DistributedItem> findDistributedItemById(long id);
    DistributedItem findDistributedItemByIdAndPosition(long id, long position);

}
