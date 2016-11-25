package ru.velkomfood.mysap.services.model.DAO;

import ru.velkomfood.mysap.services.model.entities.MrpRequestEntity;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;

/**
 * Created by dpetrov on 29.06.16.
 */
public interface MrpRequestDAO {

    void setDataSource(DataSource dataSource);
    List<MrpRequestEntity> findMrpItemsByDateAndMaterial(Date from, Date to, String matnr);
    List<MrpRequestEntity> findAllMrpItems();

}
