package ru.velkomfood.mysap.services.model.templates;

import org.springframework.jdbc.core.JdbcTemplate;
import ru.velkomfood.mysap.services.model.DAO.PurchaseGroupDAO;
import ru.velkomfood.mysap.services.model.entities.PurchaseGroupEntity;
import ru.velkomfood.mysap.services.model.mappers.PurchaseGroupMapper;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by dpetrov on 11.07.16.
 */

public class PurchaseGroupJdbcTemplate implements PurchaseGroupDAO {

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplateObject;

    @Override
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        jdbcTemplateObject = new JdbcTemplate(dataSource);
    }

    @Override
    public List<PurchaseGroupEntity> findPurchaseGroupById(String purId) {

        List<PurchaseGroupEntity> result;

        String sql = "SELECT id, description FROM pur_groups ";
        sql = sql + "WHERE id = " + "\'" + purId + "\' ";
        sql = sql + "ORDER BY id";

        result = jdbcTemplateObject.query(sql, new Object[] {purId}, new PurchaseGroupMapper());

        return result;
    }

    @Override
    public List<PurchaseGroupEntity> findAllPurchaseGroups() {

        List<PurchaseGroupEntity> result;

        String sql = "SELECT id, description FROM pur_groups ";
        sql = sql + "ORDER BY id";

        result = jdbcTemplateObject.query(sql, new PurchaseGroupMapper());

        return result;
    }

}
