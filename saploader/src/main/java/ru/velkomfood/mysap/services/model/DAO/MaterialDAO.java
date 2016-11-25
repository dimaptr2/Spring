package ru.velkomfood.mysap.services.model.DAO;

import ru.velkomfood.mysap.services.model.entities.MaterialEntity;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by dpetrov on 11.07.16.
 */
public interface MaterialDAO {

    void setDataSource(DataSource dataSource);
    List<MaterialEntity> findMaterialByNumber(String matnr);
    List<MaterialEntity> findAllMaterials();

}
