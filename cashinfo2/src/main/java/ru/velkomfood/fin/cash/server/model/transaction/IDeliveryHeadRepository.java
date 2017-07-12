package ru.velkomfood.fin.cash.server.model.transaction;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by dpetrov on 28.06.17.
 */
public interface IDeliveryHeadRepository extends CrudRepository<DeliveryHead, Long> {

    List<DeliveryHead> findAll();
    DeliveryHead findDeliveryHeadById(long id);
    List<DeliveryHead> findDeliveryHeadByIdBetween(long low, long high);
    List<DeliveryHead> findDeliveryHeadByPostingDateBetween(java.sql.Date from, java.sql.Date to);

}
