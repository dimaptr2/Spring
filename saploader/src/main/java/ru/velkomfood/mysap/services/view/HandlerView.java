package ru.velkomfood.mysap.services.view;

import ru.velkomfood.mysap.services.model.entities.MrpTotalEntity;

import java.util.List;

/**
 * Created by dpetrov on 22.06.16.
 */

public class HandlerView {

    private final long id;
    private final String content;
    private List<MrpTotalEntity> result;

    public HandlerView(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public List<MrpTotalEntity> getResult() {
        return result;
    }

    public void setResult(List<MrpTotalEntity> result) {
        this.result = result;
    }
}
