package ru.velkomfood.mysap.services.model.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.velkomfood.mysap.services.model.entities.MaterialSumEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by dpetrov on 08.07.16.
 */
public class MaterialSumMapper implements RowMapper<MaterialSumEntity> {

    @Override
    public MaterialSumEntity mapRow(ResultSet resultSet, int i) throws SQLException {

        MaterialSumEntity matSumEntity = new MaterialSumEntity();

        matSumEntity.setMaterialNumber(resultSet.getString("matnr"));
        matSumEntity.setAvailableDate(resultSet.getDate("avail_date"));
        matSumEntity.setPsum(resultSet.getBigDecimal("pq"));
        matSumEntity.setSsum(resultSet.getBigDecimal("sq"));
        matSumEntity.setAsum(resultSet.getBigDecimal("aq"));

        return matSumEntity;
    }

}
