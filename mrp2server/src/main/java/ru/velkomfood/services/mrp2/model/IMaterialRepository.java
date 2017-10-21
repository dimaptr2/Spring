package ru.velkomfood.services.mrp2.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IMaterialRepository extends CrudRepository<Material, Long> {

    List<Material> findAll();
    Material findMaterialById(long id);
    List<Material> findMaterialByIdBetween(long low, long high);
    List<Material> findMaterialByDescriptionLike(String description);

}
