package ru.velkomfood.fin.cash.controller;

import com.sap.conn.jco.ext.DestinationDataProvider;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * Created by dpetrov on 03.03.2017.
 */
@Component
public class SapDataManager {

    private Properties connectProperties;
    String DEST_NAME = "PRD500R15";

    public SapDataManager() {
        connectProperties = new Properties();
        connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST, "rups15.eatmeat.ru");
    }
}
