package ru.velkomfood.mysap.services.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.velkomfood.mysap.services.config.InitialConfigurator;
import ru.velkomfood.mysap.services.model.entities.MrpRequestEntity;
import ru.velkomfood.mysap.services.model.entities.MrpTotalEntity;
import ru.velkomfood.mysap.services.model.templates.MrpRequestJdbcTemplate;
import ru.velkomfood.mysap.services.view.HandlerView;

import javax.sql.DataSource;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by dpetrov on 22.06.16.
 */

@RestController
public class MainRequestHandler {

    private String template = "MRP items: ";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    DataSource dataSource;
    @Autowired
    DiggerOfData diggerOfData;

    @RequestMapping(value = "/mrplist")
    public HandlerView HandleRequest() {

        HandlerView handlerView;
        List<MrpTotalEntity> result = null;

        AnnotationConfigApplicationContext ctx =
                new AnnotationConfigApplicationContext(InitialConfigurator.class);

        if (dataSource != null) {

            diggerOfData.setDataSource(dataSource);
            diggerOfData.receiveMaterialList();
            diggerOfData.receivePurchaseGroupsList();
            diggerOfData.findSumByMaterials();
            result = diggerOfData.getResultList();

        }

        handlerView = new HandlerView(counter.incrementAndGet(),
                                        String.format(template));
        handlerView.setResult(result);

        return handlerView;

    }

    @RequestMapping(value = "/mrptotals")
    public HandlerView HandleRequestForSums() {

        HandlerView handlerView = null;

        return handlerView;

    }
}
