package ru.velkomfood.services.mrp2.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IRequirementRepository extends CrudRepository<Requirement, RequirementKey> {

    List<Requirement> findAll();
    List<Requirement> findRequirementByMaterialIdAndYearEquals(long id, int year);

    List<Requirement> findRequirementByMaterialIdAndPurchaseGroupIdAndYearAndMonthBetween(
            long materialId, String purchaseGroupId, int year, int fromMonth, int toMonth
    );

    List<Requirement> findRequirementByMaterialIdBetweenAndPurchaseGroupIdAndYearAndMonthBetween(
            long fromMatId, long toMatId, String purchaseGroupId,
            int year, int fromMonth, int toMonth
    );

    List<Requirement> findRequirementByYearBetween(int low, int high);
    List<Requirement> findRequirementByYearEqualsAndMonthBetween(int year, int fromMonth, int toMonth);

}
