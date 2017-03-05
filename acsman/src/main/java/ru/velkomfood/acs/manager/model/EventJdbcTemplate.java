package ru.velkomfood.acs.manager.model;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by dpetrov on 17.02.17.
 */
public class EventJdbcTemplate implements iEventDAO {

    @Override
    public List<Event> getEventByIdAndDate(Long id, String fromDate) throws SQLException {
        return null;
    }

    @Override
    public void update(String regDate, String regDateFull) throws SQLException {

    }

}
