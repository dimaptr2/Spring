package ru.velkomfood.production.beaglebone.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.velkomfood.production.beaglebone.backend.model.EventsEntity;
import ru.velkomfood.production.beaglebone.backend.view.MakerAnswers;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by dpetrov on 17.06.16.
 */

@RestController
public class WebRequestHandler {

    @Autowired
    SQLiteManager sqman;

    private AtomicLong counter = new AtomicLong();

    @RequestMapping("/boxinfo")
    public MakerAnswers sendRequest(String[] parameters) {

        String answer = "";
        List<EventsEntity> entities = null;
        MakerAnswers maker;

        if (sqman != null) {

            try {

                entities = sqman.readDatabase();

            } catch (SQLException sqe) {
                answer = sqe.getMessage();
            }

            answer += "Number rows are: " + entities.size();
        }

        maker = new MakerAnswers(counter.incrementAndGet(), String.format(answer));
        maker.setResult(entities);

        return maker;
    }
}
