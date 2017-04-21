package ru.velkomfood.beagle.info.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by dpetrov on 21.04.17.
 */
public class EventMapper implements RowMapper<Event> {

    @Override
    public Event mapRow(ResultSet rs, int rowNum) throws SQLException {
        Event event = new Event(
                rs.getInt("year"),
                rs.getString("month"),
                rs.getInt("day"),
                rs.getString("dayweek"),
                rs.getString("event_time"),
                rs.getBigDecimal("quantity"));
        return event;
    }

}
