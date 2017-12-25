package ru.velkomfood.mrp3.reports.model.td;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IResultData extends CrudRepository<ResultData, ResultDataKey> {

    List<ResultData> findAll();
    List<ResultData> findResultDataByMaterialId(long id);
    List<ResultData> findResultDataByMaterialIdBetween(long low, long high);
    List<ResultData> findResultDataByMaterialIdAndMonthAndYear(long id, int month, int year);

}
