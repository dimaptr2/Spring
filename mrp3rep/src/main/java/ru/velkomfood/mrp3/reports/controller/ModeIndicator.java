package ru.velkomfood.mrp3.reports.controller;

import org.springframework.stereotype.Component;

@Component
public class ModeIndicator {

    private boolean loading;

    public ModeIndicator() {
        loading = false;
    }

    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }

}
