package ru.velkomfood.mysap.mrp.controller;

import java.util.BitSet;

/**
 * Created by dpetrov on 19.10.16.
 */
public class DbStatus {

    private boolean initial;
    private boolean loading;

    public DbStatus() {

        initial = true;
        loading = false;

    }

    public boolean isInitial() {
        return initial;
    }

    public void setInitial(boolean initial) {
        this.initial = initial;
    }

    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }


}
