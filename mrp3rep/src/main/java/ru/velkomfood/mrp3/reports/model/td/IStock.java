package ru.velkomfood.mrp3.reports.model.td;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface IStock extends CrudRepository<Stock, StockKey> {

    List<Stock> findAll();
    List<Stock> findStockByMaterialId(long id);
    List<Stock> findStockByPeriodAndYear(int period, int year);
    List<Stock> findStockByMaterialIdAndYear(long id, int year);
    List<Stock> findStockByMaterialIdAndPeriodAndYear(long id, int period, int year);
    List<Stock> findStockByMaterialIdAndYearAndFreeGreaterThan(long id, int year, BigDecimal value);
    List<Stock> findStockByMaterialIdAndYearAndMovingGreaterThan(long id, int year, BigDecimal value);
    List<Stock> findStockByMaterialIdAndYearAndQualityGreaterThan(long id, int year, BigDecimal value);
    List<Stock> findStockByMaterialIdAndYearAndBlockGreaterThan(long id, int year, BigDecimal value);

}
