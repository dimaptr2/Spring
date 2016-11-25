package ru.velkomfood.mysap.services.model.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.velkomfood.mysap.services.model.entities.PurchaseGroupSumEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by dpetrov on 08.07.16.
 */
public class PurchaseGroupSumMapper implements RowMapper<PurchaseGroupSumEntity> {

    @Override
    public PurchaseGroupSumEntity mapRow(ResultSet resultSet, int i) throws SQLException {

        PurchaseGroupSumEntity pgSumEntity = new PurchaseGroupSumEntity();

        pgSumEntity.setPurId(resultSet.getString("pur_group"));
        pgSumEntity.setAvailableDate(resultSet.getDate("avail_date"));
        pgSumEntity.setPsum(resultSet.getBigDecimal("pq"));
        pgSumEntity.setSsum(resultSet.getBigDecimal("sq"));
        pgSumEntity.setAsum(resultSet.getBigDecimal("aq"));

        return pgSumEntity;

    }

}
