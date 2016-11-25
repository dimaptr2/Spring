package ru.velkomfood.mysap.services.model.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.velkomfood.mysap.services.model.entities.MrpRequestEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by dpetrov on 29.06.16.
 */
public class MrpRequestMapper implements RowMapper<MrpRequestEntity> {

    @Override
    public MrpRequestEntity mapRow(ResultSet resultSet, int i) throws SQLException {

        MrpRequestEntity mrpEntity = new MrpRequestEntity();

        mrpEntity.setWerks(resultSet.getInt("werks"));
        mrpEntity.setMatnr(resultSet.getString("matnr"));
        mrpEntity.setMaktx(resultSet.getString("maktx"));
        mrpEntity.setMrpDate(resultSet.getDate("avdate"));
        mrpEntity.setBaseUnit(resultSet.getString("bu"));
        mrpEntity.setPer_segmt(resultSet.getString("per_segmt"));
        mrpEntity.setPurchaseGroup(resultSet.getString("purgroup"));
        mrpEntity.setPgrpDescription(resultSet.getString("pur_description"));
        // Get quantities
        mrpEntity.setPri_req_quantity(resultSet.getBigDecimal("pri_req_quantity"));
        mrpEntity.setSec_req_quantity(resultSet.getBigDecimal("sec_req_quantity"));
        mrpEntity.setAvail_quantity(resultSet.getBigDecimal("avail_quantity"));

        return mrpEntity;

    }

}
