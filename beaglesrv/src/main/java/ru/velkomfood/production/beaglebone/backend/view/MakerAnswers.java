package ru.velkomfood.production.beaglebone.backend.view;

import ru.velkomfood.production.beaglebone.backend.model.EventsEntity;

import java.util.List;

/**
 * Created by dpetrov on 17.06.16.
 */

public class MakerAnswers {

    private long id;
    private final String content;
    private List<EventsEntity> result;

    public MakerAnswers(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public List<EventsEntity> getResult() {
        return result;
    }

    public void setResult(List<EventsEntity> result) {
        this.result = result;
    }
}
