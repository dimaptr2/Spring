package ru.velkomfood.mysap.services.model.templates;

import org.springframework.jdbc.core.JdbcTemplate;
import ru.velkomfood.mysap.services.model.DAO.MaterialDAO;
import ru.velkomfood.mysap.services.model.entities.MaterialEntity;
import ru.velkomfood.mysap.services.model.mappers.MaterialMapper;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by dpetrov on 11.07.16.
 */
public class MaterialJdbcTemplate implements MaterialDAO {

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplateObject;

    @Override
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        jdbcTemplateObject = new JdbcTemplate(dataSource);
    }

    @Override
    public List<MaterialEntity> findMaterialByNumber(String matnr) {

        List<MaterialEntity> result;

        String sql = "SELECT matnr, maktx FROM mara WHERE matnr = " + "\'" + matnr + "\' ";
        sql = sql + "ORDER BY matnr";

        result = jdbcTemplateObject.query(sql, new Object[] {matnr}, new MaterialMapper());

        return result;
    }

    @Override
    public List<MaterialEntity> findAllMaterials() {

        List<MaterialEntity> result;

        String sql = "SELECT matnr, maktx FROM mara ORDER BY matnr";

        result = jdbcTemplateObject.query(sql, new MaterialMapper());

        return result;
    }

}
