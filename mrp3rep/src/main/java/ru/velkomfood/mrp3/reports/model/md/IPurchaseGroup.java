package ru.velkomfood.mrp3.reports.model.md;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IPurchaseGroup extends CrudRepository<PurchaseGroup, String> {

    List<PurchaseGroup> findAll();
    @Override
    PurchaseGroup findOne(String id);

}
