package ru.velkomfood.beagle.services.info.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.velkomfood.beagle.services.info.model.Tick;
import ru.velkomfood.beagle.services.info.model.Tickable;

import java.util.List;

@Component
public class DbReader {

    @Autowired
    private Tickable tickable;

    public List<Tick> readTicksByDateAndTime(java.sql.Date dt1, java.sql.Date dt2,
            java.sql.Time tm1, java.sql.Time tm2) {
        return tickable
                .findTickByCurrentDateBetweenAndCurrentTimeBetween(dt1, dt2, tm1, tm2);
    }

}
