package ru.velkomfood.mysap.services.model.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.velkomfood.mysap.services.model.entities.PurchaseGroupEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by dpetrov on 11.07.16.
 */
public class PurchaseGroupMapper implements RowMapper<PurchaseGroupEntity> {

    @Override
    public PurchaseGroupEntity mapRow(ResultSet resultSet, int i) throws SQLException {

        PurchaseGroupEntity pe = new PurchaseGroupEntity();

        pe.setId(resultSet.getString("id"));
        pe.setDescription(resultSet.getString("description"));

        return pe;
    }

}
