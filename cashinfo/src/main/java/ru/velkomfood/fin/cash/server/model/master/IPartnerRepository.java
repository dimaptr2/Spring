package ru.velkomfood.fin.cash.server.model.master;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by dpetrov on 23.06.17.
 */
public interface IPartnerRepository extends CrudRepository<Partner, String> {

    List<Partner> findAll();
    Partner findPartnerById(String id);
    List<Partner> findPartnerByIdBetween(String low, String high);
    List<Partner> findPartnerByNameLike(String name);

}
