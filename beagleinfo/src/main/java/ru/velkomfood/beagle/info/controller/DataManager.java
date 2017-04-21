package ru.velkomfood.beagle.info.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.velkomfood.beagle.info.model.Event;
import ru.velkomfood.beagle.info.model.EventJDBCtemplate;
import ru.velkomfood.beagle.info.view.Reaction;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by dpetrov on 21.04.17.
 */
@RestController
public class DataManager {

    private static final String pageTemplate = "%s";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    private EventJDBCtemplate eventJDBCtemplate;

    // GET or POST request and return an answer in the JSON form
    @RequestMapping("/info")
    public Reaction makeRequest(
            @RequestParam(value = "mode", defaultValue = "")
            String mode,
            @RequestParam(value = "year1", defaultValue = "")
            int year1,
            @RequestParam(value = "year2", defaultValue = "")
            int year2,
            @RequestParam(value = "month1", defaultValue = "")
            String month1,
            @RequestParam(value = "month2", defaultValue = "")
            String month2,
            @RequestParam(value = "day1", defaultValue = "")
            int day1,
            @RequestParam(value = "day2", defaultValue = "")
            int day2) {

        List<Event> result = eventJDBCtemplate.getAmount(mode, year1, year2, month1, month2, day1, day2);

        Reaction rct = new Reaction();
        rct.setId(counter.incrementAndGet());
        rct.setResult(result);
        rct.setContent(pageTemplate);

        return rct;
    }


}
