package ru.velkomfood.fin.cash.server.model.master;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by dpetrov on 25.06.17.
 */
public interface IPartnerTypeRepository extends CrudRepository<PartnerType, Integer> {

    List<PartnerType> findAll();
    PartnerType findPartnerTypeById(int id);

}
