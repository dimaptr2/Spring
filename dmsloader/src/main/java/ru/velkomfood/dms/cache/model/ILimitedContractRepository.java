package ru.velkomfood.dms.cache.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ILimitedContractRepository extends CrudRepository<LimitedContract, Long> {

    List<LimitedContract> findAll();
    LimitedContract findLimitedContractById(long id);
    List<LimitedContract> findLimitedContractByDeadlineBefore(java.sql.Date deadline);
    List<LimitedContract> findLimitedContractByDeadlineAfter(java.sql.Date deadline);

}
