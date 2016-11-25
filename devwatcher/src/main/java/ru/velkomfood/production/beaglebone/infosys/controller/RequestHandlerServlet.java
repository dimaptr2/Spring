package ru.velkomfood.production.beaglebone.infosys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by dpetrov on 11.06.16.
 */

@Controller
public class RequestHandlerServlet {

    @Autowired
    BeagleBoneInfo beagleBoneInfo;

    @RequestMapping(value = "/boxinfo", method = RequestMethod.POST)
    public String giveAnswer(Model model) {

        return "result";
    }
}
