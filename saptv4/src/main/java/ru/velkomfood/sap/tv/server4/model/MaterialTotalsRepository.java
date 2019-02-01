package ru.velkomfood.sap.tv.server4.model;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface MaterialTotalsRepository extends CrudRepository<MaterialTotals, MaterialTotalsKey> {
    List<MaterialTotals> findMaterialTotalsByDateBefore(java.sql.Date date);
    List<MaterialTotals> findMaterialTotalsByDate(java.sql.Date date);
    List<MaterialTotals> findMaterialTotalsByDateBetween(java.sql.Date low, java.sql.Date high);
    List<MaterialTotals> findMaterialTotalsByDateAndTimeBetween(java.sql.Date date, java.sql.Time timeLow, java.sql.Time timeHigh);
}
