package ru.velkomfood.mysap.mrp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by dpetrov on 19.10.16.
 */

@RestController
public class FrontendProvider {

    @Autowired
    private DbStatus dbStatus;

    @RequestMapping("/mrp")
    public String handleView(Model model) {

        if (dbStatus.isLoading()) return "Database is loaded";
        else return "Hi!";

    }

}
