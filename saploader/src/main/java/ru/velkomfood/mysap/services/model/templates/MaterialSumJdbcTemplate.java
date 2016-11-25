package ru.velkomfood.mysap.services.model.templates;

import org.springframework.jdbc.core.JdbcTemplate;
import ru.velkomfood.mysap.services.model.DAO.MaterialSumDAO;
import ru.velkomfood.mysap.services.model.entities.MaterialSumEntity;
import ru.velkomfood.mysap.services.model.mappers.MaterialSumMapper;

import javax.sql.DataSource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by dpetrov on 08.07.16.
 */
public class MaterialSumJdbcTemplate implements MaterialSumDAO {

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplateObject;

    @Override
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        jdbcTemplateObject = new JdbcTemplate(dataSource);
    }

    @Override
    public List<MaterialSumEntity> findSumByMaterial(String matnr, Map<String, Date> when) {

        List<MaterialSumEntity> result;
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        final Date now = new Date();

        if (when.isEmpty()) {

            try {
                when.put("from", fmt.parse("1970-01-01"));
                when.put("to", fmt.parse(fmt.format(now)));
            } catch (ParseException ef) {
                ef.printStackTrace();
            }

        }

        String sql = buildBaseSqlTemplate();
        sql = sql + "WHERE matnr = " + "\'" + matnr + "\'";
        sql = sql + " AND " + "avail_date BETWEEN " + "\'" + when.get("from") + "\'";
        sql = sql + " AND " + "\'" + when.get("to") + "\' ";
        sql = sql + "GROUP BY matnr, avail_date";

        result = jdbcTemplateObject.query(sql, new Object[] {matnr, when}, new MaterialSumMapper());

        return result;
    }

    @Override
    public List<MaterialSumEntity> findSumForAllMaterials(Map<String, Date> when) {

        List<MaterialSumEntity> result;
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        final Date now = new Date();

        if (when.isEmpty()) {

            try {
                when.put("from", fmt.parse("1970-01-01"));
                when.put("to", fmt.parse(fmt.format(now)));
            } catch (ParseException ef) {
                ef.printStackTrace();
            }

        }

        String sql = buildBaseSqlTemplate();
        sql = sql + "WHERE avail_date BETWEEN " + "\'" + when.get("from") + "\'";
        sql = sql + " AND " + "\'" + when.get("to") + "\' ";
        sql = sql + "GROUP BY matnr, avail_date";

        result = jdbcTemplateObject.query(sql, new Object[] {when}, new MaterialSumMapper());

        return result;
    }

    private String buildBaseSqlTemplate() {

        String sql = "SELECT matnr, avail_date, SUM( pri_req_quantity ) AS pq, ";
        sql = sql + "SUM( sec_req_quantity ) AS sq, SUM( avail_quantity ) AS aq ";
        sql = sql + "FROM mrpitems ";

        return sql;
    }
}
