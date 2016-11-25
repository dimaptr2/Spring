package ru.velkomfood.mysap.services.model.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.velkomfood.mysap.services.model.entities.MaterialEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by dpetrov on 11.07.16.
 */
public class MaterialMapper implements RowMapper<MaterialEntity> {

    @Override
    public MaterialEntity mapRow(ResultSet resultSet, int i) throws SQLException {

        MaterialEntity me = new MaterialEntity();

        me.setMatnr(resultSet.getString("matnr"));
        me.setMaktx(resultSet.getString("maktx"));

        return me;
    }

}
