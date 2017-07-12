package ru.velkomfood.fin.cash.server.model.transaction;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by dpetrov on 11.07.17.
 */
public interface IDistributedHeadRepository extends CrudRepository<DistributedHead, Long> {

    List<DistributedHead> findAll();
    DistributedHead findDistributedHeadById(long id);
    List<DistributedHead> findDistributedHeadByIdBetween(long low, long high);
    List<DistributedHead> findDistributedHeadByPostingDateBetween(java.sql.Date fromDate, java.sql.Date toDate);

}
