package ru.velkomfood.sap.tv.server4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.velkomfood.sap.tv.server4.model.MaterialTotals;
import ru.velkomfood.sap.tv.server4.model.MaterialTotalsKey;
import ru.velkomfood.sap.tv.server4.model.MaterialTotalsRepository;
import ru.velkomfood.sap.tv.server4.model.SumView;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

@Service
public class DbProcessor {

    private MaterialTotalsRepository materialTotalsRepository;
    private JdbcTemplate jdbcTemplate;
    private Properties queries;

    @Autowired
    public DbProcessor(MaterialTotalsRepository materialTotalsRepository, JdbcTemplate jdbcTemplate) {
        this.materialTotalsRepository = materialTotalsRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    @Qualifier("jdbcQueryProperties")
    public void setQueries(Properties queries) {
        this.queries = queries;
    }

    public void saveMaterialTotalsEntity(MaterialTotals materialTotalsEntity) {
        materialTotalsRepository.save(materialTotalsEntity);
    }

    public void refreshDataBeforeDate(java.sql.Date date) {
        jdbcTemplate.update(queries.getProperty("delete.old.rows"), date);
    }

    public List<MaterialTotals> selectShipmentsAtDate(java.sql.Date dateLow, java.sql.Date dateHigh) {

//        List<MaterialTotals> materialInfoList = new ArrayList<>();
//
//        jdbcTemplate.query(queries.getProperty("read.shipments.date.between"),
//                new Object[] {dateLow, dateHigh},
//                (rs, rowNum) -> materialInfoList.add(createMaterialTotalsRow(rs)));

        return materialTotalsRepository.findMaterialTotalsByDateBetween(dateLow, dateHigh);
    }

    public List<MaterialTotals> selectShipmentsAtDateTimeBetween(java.sql.Date date,
                                                                 java.sql.Time timeLow, java.sql.Time timeHigh) {

//                jdbcTemplate.query(queries.getProperty("read.shipments.date.time.between"),
//                new Object[] {date, timeLow, timeHigh},
//                (rs, rowNum) -> resultingList.add(createMaterialTotalsRow(rs)));
        return materialTotalsRepository.findMaterialTotalsByDateAndTimeBetween(date, timeLow, timeHigh);
    }

    public List<SumView> selectSumsBetweenDates(java.sql.Date low, java.sql.Date high) {

        List<SumView> values = new ArrayList<>();

        jdbcTemplate.query(queries.getProperty("select.sum.values.between.dates"),
                new Object[] {low, high},
                (rs, rowNum) -> values.add(createSumView(rs)));

        return values;
    }

    public MaterialTotals entityExists(MaterialTotals entity) {
        MaterialTotalsKey key = new MaterialTotalsKey(entity.getId(), entity.getDate(), entity.getTime());
        return materialTotalsRepository.findById(key).orElse(null);
    }

    public void deleteMaterialTotalsEntity(MaterialTotals entity) {
        materialTotalsRepository.delete(entity);
    }

    // private section

//    private MaterialTotals createMaterialTotalsRow(ResultSet rs) throws SQLException {
//        return new MaterialTotals(
//                rs.getLong("id"),
//                rs.getDate("dt"),
//                rs.getTime("tm"),
//                rs.getString("description"),
//                rs.getString("uom"),
//                rs.getBigDecimal("packed"),
//                rs.getBigDecimal("quantity"),
//                rs.getBigDecimal("in_process"),
//                rs.getBigDecimal("transferred")
//        );
//    }

    private SumView createSumView(ResultSet rs) throws SQLException {
        Optional<SumView> optionalSumView = Optional
                .of(new SumView(rs.getBigDecimal("q_value"), rs.getBigDecimal("p_value")));
        return optionalSumView.orElse(null);
    }

}
