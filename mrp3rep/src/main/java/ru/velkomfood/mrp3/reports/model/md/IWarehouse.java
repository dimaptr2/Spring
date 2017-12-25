package ru.velkomfood.mrp3.reports.model.md;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IWarehouse extends CrudRepository<Warehouse, String> {

    List<Warehouse> findAll();
    @Override
    Warehouse findOne(String id);

}
