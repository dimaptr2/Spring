package ru.velkomfood.services.mrp2.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IStockRepository extends CrudRepository<Stock, StockKey> {

    List<Stock> findAll();
    List<Stock> findStockByMaterialId(long id);
    List<Stock> findStockByYearEqualsAndMonthBetween(int year, int lowMonth, int highMonth);

}
