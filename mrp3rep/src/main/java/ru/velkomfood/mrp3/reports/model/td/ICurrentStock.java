package ru.velkomfood.mrp3.reports.model.td;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ICurrentStock extends CrudRepository<CurrentStock, CurrentStockKey> {

    List<CurrentStock> findAll();
    CurrentStock findCurrentStockByMaterialId(long id);

}
