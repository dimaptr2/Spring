package ru.velkomfood.mysap.services.model.DAO;

import ru.velkomfood.mysap.services.model.entities.PurchaseGroupSumEntity;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by dpetrov on 08.07.16.
 */
public interface PurchaseGroupSumDAO {

    void setDataSource(DataSource dataSource);
    List<PurchaseGroupSumEntity> findSumByPurchaseGroup(String purId, Map<String, Date> when);
    List<PurchaseGroupSumEntity> findSumForAllPurchaseGroups(Map<String,Date> when);

}
