package ru.velkomfood.mrp3.reports.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.velkomfood.mrp3.reports.controller.DbChanger;
import ru.velkomfood.mrp3.reports.model.md.Material;

import java.time.LocalDate;
import java.util.List;

@RestController
public class IndexController {

    private DbChanger dbChanger;

    @Autowired
    public void setDbChanger(DbChanger dbChanger) {
        this.dbChanger = dbChanger;
    }

    @RequestMapping("/now")
    public LocalDate returnServerDate() {
        return LocalDate.now();
    }

    @RequestMapping("/materials")
    public List<Material> readAllMaterials() {
        return dbChanger.readAllMaterials();
    }

}
