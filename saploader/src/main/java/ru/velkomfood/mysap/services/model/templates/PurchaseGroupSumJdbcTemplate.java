package ru.velkomfood.mysap.services.model.templates;

import org.springframework.jdbc.core.JdbcTemplate;
import ru.velkomfood.mysap.services.model.DAO.PurchaseGroupSumDAO;
import ru.velkomfood.mysap.services.model.entities.PurchaseGroupSumEntity;
import ru.velkomfood.mysap.services.model.mappers.PurchaseGroupSumMapper;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by dpetrov on 08.07.16.
 */
public class PurchaseGroupSumJdbcTemplate implements PurchaseGroupSumDAO {

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplateObject;

    @Override
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }

    @Override
    public List<PurchaseGroupSumEntity> findSumByPurchaseGroup(String purId, Map<String, Date> when) {

        List<PurchaseGroupSumEntity> result;

        String sql = buildBaseSqlTemplate();
        sql = "WHERE pur_group = " + purId + " AND avail_date BETWEEN ";
        sql = sql + "\'" + when.get("from") + "\'" + " AND " + "\'" + when.get("to") + "\' ";
        sql = sql + "GROUP BY pur_group, avail_date";

        result = jdbcTemplateObject.query(sql, new Object[] {purId, when}, new PurchaseGroupSumMapper());

        return result;
    }

    @Override
    public List<PurchaseGroupSumEntity> findSumForAllPurchaseGroups(Map<String, Date> when) {

        List<PurchaseGroupSumEntity> result;

        String sql = buildBaseSqlTemplate();
        sql = "WHERE avail_date BETWEEN ";
        sql = sql + "\'" + when.get("from") + "\'" + " AND " + "\'" + when.get("to") + "\' ";
        sql = sql + "GROUP BY pur_group, avail_date";

        result = jdbcTemplateObject.query(sql, new Object[] {when}, new PurchaseGroupSumMapper());

        return result;
    }

    private String buildBaseSqlTemplate() {

        String sql = "SELECT pur_group, avail_date, SUM( pri_req_quantity ) AS pq, ";
        sql = sql + "SUM( sec_req_quantity ) AS sq, SUM( avail_quantity ) AS aq ";
        sql = sql + "FROM mrpitems ";

        return sql;
    }
}
