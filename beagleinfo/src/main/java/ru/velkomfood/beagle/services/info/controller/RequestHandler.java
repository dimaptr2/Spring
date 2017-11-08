package ru.velkomfood.beagle.services.info.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.velkomfood.beagle.services.info.db.DbReader;
import ru.velkomfood.beagle.services.info.model.Tick;

import java.util.List;

@RestController
public class RequestHandler {

    @Autowired
    private DbReader dbReader;

    @RequestMapping(value = "/boxes", method = RequestMethod.POST)
    public List<Tick> getEventsData(
            @RequestParam(name = "fromDate") java.sql.Date dt1,
            @RequestParam(name = "toDate") java.sql.Date dt2,
            @RequestParam(name = "timeLow") java.sql.Time tm1,
            @RequestParam(name = "timeHigh") java.sql.Time tm2) {
        return dbReader
                .readTicksByDateAndTime(dt1, dt2, tm1, tm2);
    }

}
