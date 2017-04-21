package ru.velkomfood.beagle.info.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by dpetrov on 21.04.17.
 */
@Component
public class EventJDBCtemplate implements IEventDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Event> getAmount(
            String mode,
            int year1, int year2,
            String month1, String month2, int day1, int day2) {


        String sql = buildStatement(mode, year1, year2, month1, month2, day1, day2);

        return jdbcTemplate.query(
                sql,
                new Object[] {year1, year2, month1, month2, day1, day2},
                new EventMapper());
    }

    // Build a statement
    private String buildStatement(String typeSql,
                                  int year1, int year2,
                                  String month1, String month2, int day1, int day2) {

        StringBuilder sb = new StringBuilder(0);

        switch (typeSql) {
            case "amount":
                sb.append("SELECT year, month, day, dayweek, event_time, (MAX( quantity ) - MIN( quantity )) AS amount");
                sb.append(" ").append("FROM events").append(" ");
                sb.append("WHERE year IN (?, ?) AND (month BETWEEN ? AND ?) AND");
                sb.append(" ").append("day >= ? AND day <= ?").append(" ORDER BY year, month, day");
                break;
            case "items":
                sb.append("SELECT year, month, day, dayweek, event_time, quantity");
                sb.append(" ").append("FROM events").append(" ");
                sb.append("WHERE year IN (?, ?) AND (month BETWEEN ? AND ?) AND");
                sb.append(" ").append("day >= ? AND day <= ?").append(" ORDER BY year, month, day");
                break;
        }

        return sb.toString();
    }

}
