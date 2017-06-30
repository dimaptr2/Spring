package ru.velkomfood.fin.cash.server.model.master;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by dpetrov on 30.06.17.
 */
public interface ISectorRepository extends CrudRepository<Sector, String> {

    List<Sector> findAll();
    Sector findSectorById(String id);

}
