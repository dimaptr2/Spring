package ru.velkomfood.services.mrp2.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IStock extends CrudRepository<Stock, StockKey> {

    List<Stock> findAll();

}
