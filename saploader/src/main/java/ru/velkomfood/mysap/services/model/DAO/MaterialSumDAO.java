package ru.velkomfood.mysap.services.model.DAO;

import ru.velkomfood.mysap.services.model.entities.MaterialSumEntity;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by dpetrov on 08.07.16.
 */
public interface MaterialSumDAO {

    void setDataSource(DataSource dataSource);
    List<MaterialSumEntity> findSumByMaterial(String matnr, Map<String, Date> when);
    List<MaterialSumEntity> findSumForAllMaterials(Map<String, Date> when);

}
