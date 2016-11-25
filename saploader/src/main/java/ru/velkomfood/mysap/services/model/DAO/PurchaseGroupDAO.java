package ru.velkomfood.mysap.services.model.DAO;

import ru.velkomfood.mysap.services.model.entities.PurchaseGroupEntity;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by dpetrov on 11.07.16.
 */
public interface PurchaseGroupDAO {

    void setDataSource(DataSource dataSource);
    List<PurchaseGroupEntity> findPurchaseGroupById(String purId);
    List<PurchaseGroupEntity> findAllPurchaseGroups();

}
