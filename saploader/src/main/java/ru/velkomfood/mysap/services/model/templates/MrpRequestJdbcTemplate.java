package ru.velkomfood.mysap.services.model.templates;

import org.springframework.jdbc.core.JdbcTemplate;
import ru.velkomfood.mysap.services.model.DAO.MrpRequestDAO;
import ru.velkomfood.mysap.services.model.entities.MrpRequestEntity;
import ru.velkomfood.mysap.services.model.mappers.MrpRequestMapper;

import javax.sql.DataSource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by dpetrov on 29.06.16.
 */

public class MrpRequestJdbcTemplate implements MrpRequestDAO {

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplateObject;

    @Override
    public void setDataSource(DataSource dataSource) {

        this.dataSource = dataSource;
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);

    }

    @Override
    public List<MrpRequestEntity> findMrpItemsByDateAndMaterial(Date from, Date to, String matnr) {

        List<MrpRequestEntity> result;

        String sql = buildBaseSqlTemplate();
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        String d_from = fmt.format(from);
        String d_to = fmt.format(to);

        sql = sql + " WHERE m.matnr = " + matnr + " mrp.avail_date BETWEEN " +
        "\'" + from + "\'" + " AND " + "\'" + to + "\'" +
                " GROUP BY m.matnr, mrp.avail_date ORDER BY m.matnr, mrp.avail_date";

        result = jdbcTemplateObject.query(sql, new Object[] {d_from, d_to, matnr}, new MrpRequestMapper());

        return result;

    }

    @Override
    public List<MrpRequestEntity> findAllMrpItems() {

        List<MrpRequestEntity> result;

        String sql = buildBaseSqlTemplate();

        sql = sql + " ORDER BY m.matnr, mrp.id";

        result = jdbcTemplateObject.query(sql, new MrpRequestMapper());

        return result;
    }

    // Build the framework of the SQL command.
    private String buildBaseSqlTemplate() {

        String sql;

        sql = "SELECT mrp.id, mrp.werks, m.matnr, m.maktx, mrp.avail_date AS avdate, mrp.base_unit AS bu,";
        sql = sql + " mrp.per_segmt, p.id AS purgroup, p.description AS pur_description,";
        sql = sql + " mrp.pri_req_quantity, mrp.sec_req_quantity , mrp.avail_quantity";
        sql = sql + " FROM mrpitems AS mrp";
        sql = sql + " INNER JOIN mara AS m";
        sql = sql + " ON m.matnr = mrp.matnr";
        sql = sql + " INNER JOIN pur_groups AS p";
        sql = sql + " ON p.id = mrp.pur_group";

        return sql;
    }

}
