package ru.velkomfood.fin.cash.server.controller;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.ext.DestinationDataProvider;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by dpetrov on 22.06.17.
 */
@Component
public class SAPEngine {

    final static String DEST = "PRD";
    final static String SUFFIX = ".jcoDestination";

    private Properties connProperties;
    private JCoDestination destination;

    public SAPEngine() {
        connProperties = new Properties();
        connProperties.setProperty(DestinationDataProvider.JCO_ASHOST, "XXX");
        connProperties.setProperty(DestinationDataProvider.JCO_SYSNR, "XX");
        connProperties.setProperty(DestinationDataProvider.JCO_R3NAME, "XXX");
        connProperties.setProperty(DestinationDataProvider.JCO_CLIENT, "XXX");
        connProperties.setProperty(DestinationDataProvider.JCO_USER, "XXXX");
        connProperties.setProperty(DestinationDataProvider.JCO_PASSWD, "XXXX");
        connProperties.setProperty(DestinationDataProvider.JCO_LANG, "XX");
        createDestinationDataFile(DEST, connProperties);
    }

    private void createDestinationDataFile(String destName, Properties props) {

        File destCfg = new File(destName + SUFFIX);

        if (destCfg.isDirectory() || !destCfg.exists()) {
            try {
                FileOutputStream fos = new FileOutputStream(destCfg, false);
                props.store(fos, "Productive environment");
                fos.close();
            } catch (IOException fe) {
                fe.printStackTrace();
            }
        }

    }

    // Run this method after the creation an instance of Bean
    @PostConstruct
    public void initSAPDestination() throws JCoException {

        destination = JCoDestinationManager.getDestination(DEST);

    }

}
