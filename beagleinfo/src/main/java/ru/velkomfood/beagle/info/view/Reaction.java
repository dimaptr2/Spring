package ru.velkomfood.beagle.info.view;

import ru.velkomfood.beagle.info.model.Event;

import java.util.List;

/**
 * Created by dpetrov on 21.04.17.
 */
public class Reaction {

    private long id;
    private List<Event> result;
    private String content;

    public Reaction() { }

    public Reaction(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Event> getResult() {
        return result;
    }

    public void setResult(List<Event> result) {
        this.result = result;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
