package ru.velkomfood.sap.tv.server4.model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface MaterialTotalsRepository extends CrudRepository<MaterialTotals, MaterialTotalsKey> {

    @Query("select mat from MaterialTotals mat where mat.date between ?1 and ?2 " +
            "and mat.inProcess >= 1.00 order by mat.time asc, mat.inProcess desc")
    List<MaterialTotals> findMaterialTotalsByDateBetween(java.sql.Date low, java.sql.Date high);

    @Query("select mt from MaterialTotals mt where mt.date = ?1 and mt.time between ?2 and ?3 " +
            "and mt.inProcess >= 1.00 order by mt.time asc, mt.inProcess desc")
    List<MaterialTotals> findMaterialTotalsByDateAndTimeBetween(java.sql.Date date, java.sql.Time timeLow, java.sql.Time timeHigh);

}
