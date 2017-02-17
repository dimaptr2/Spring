package ru.velkomfood.acs.manager.model;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by velkomfood on 17.02.17.
 */
public interface iEventDAO {

    List<Event> getEventByIdAndDate(Long id, String fromDate) throws SQLException;
    void update(String regDate, String regDateFull) throws SQLException;

}
