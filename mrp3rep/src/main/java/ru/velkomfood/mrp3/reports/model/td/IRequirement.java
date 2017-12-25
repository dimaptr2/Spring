package ru.velkomfood.mrp3.reports.model.td;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IRequirement extends CrudRepository<Requirement, RequirementKey> {

    List<Requirement> findAll();
    List<Requirement> findRequirementByMaterial(long material);
    List<Requirement> findRequirementByMaterialAndYear(long material, int year);

    @Query(value = "SELECT * FROM mrp2total WHERE material = ?1 AND year = ?2 " +
            "ORDER BY material year", nativeQuery = true)
    List<Requirement> readAllByMaterial(long material, int year);

    List<Requirement> findRequirementByMaterialBetween(long low, long high);

}
