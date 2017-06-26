package ru.velkomfood.fin.cash.server.model.master;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by dpetrov on 23.06.17.
 */
public interface IMaterialRepository extends CrudRepository<Material, Long> {


    List<Material> findAll();
    Material findMaterialById(long id);
    List<Material> findMaterialByDescriptionLike(String mat);
    List<Material> findMaterialByIdBetween(long low, long high);

}
