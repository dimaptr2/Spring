package ru.velkomfood.dms.cache.controller;

import com.sap.conn.jco.*;
import com.sap.conn.jco.ext.DestinationDataProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import ru.velkomfood.dms.cache.model.*;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
@EnableAsync
public class DataMaker {

    final String destinationName = "";
    final String SUFFIX = ".jcoDestination";

    private JCoDestination destination1, destination2;
    private Properties sapConnection1, sapConnection2;
    private Map<Long, LimitedContract> contracts;

    // Database entities
    @Autowired
    private IDMSdocumentRepository idmSdocumentRepository;
    @Autowired
    private IDetailRepository iDetailRepository;
    @Autowired
    private AlphaTransformator alphaTransformator;

    public DataMaker() {

        sapConnection1 = new Properties();
        sapConnection1.setProperty(DestinationDataProvider.JCO_ASHOST, "");
        sapConnection1.setProperty(DestinationDataProvider.JCO_SYSNR, "");
        sapConnection1.setProperty(DestinationDataProvider.JCO_R3NAME, "");
        sapConnection1.setProperty(DestinationDataProvider.JCO_CLIENT, "");
        sapConnection1.setProperty(DestinationDataProvider.JCO_USER, "");
        sapConnection1.setProperty(DestinationDataProvider.JCO_PASSWD, "");
        sapConnection1.setProperty(DestinationDataProvider.JCO_LANG, "");

        sapConnection2 = new Properties();
        sapConnection2.setProperty(DestinationDataProvider.JCO_ASHOST, "rups14.eatmeat.ru");
        sapConnection2.setProperty(DestinationDataProvider.JCO_SYSNR, "01");
        sapConnection2.setProperty(DestinationDataProvider.JCO_R3NAME, "PRD");
        sapConnection2.setProperty(DestinationDataProvider.JCO_CLIENT, "500");
        sapConnection2.setProperty(DestinationDataProvider.JCO_USER, "BGD_ADMIN");
        sapConnection2.setProperty(DestinationDataProvider.JCO_PASSWD, "123qweASD");
        sapConnection2.setProperty(DestinationDataProvider.JCO_LANG, "RU");

        contracts = new ConcurrentHashMap<>();

    }

    @PostConstruct
    public void initConnection() throws JCoException {

        String dest1 = destinationName + "1";
        String dest2 = destinationName + "2";

        createDestinationDataFile(dest1, sapConnection1);
        createDestinationDataFile(dest2, sapConnection2);

        destination1 = JCoDestinationManager.getDestination(dest1);
        destination2 = JCoDestinationManager.getDestination(dest2);

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

    public void takeDocuments() throws JCoException {

        System.out.println("Start the uploading DMS documents");
        long t1 = new Date().getTime();

        JCoFunction bapiDocList = destination1.getRepository().getFunction("BAPI_DOCUMENT_GETLIST");
        if (bapiDocList == null) {
            throw new RuntimeException("Function BAPI_DOCUMENT_GETLIST not found");
        }

        bapiDocList.getImportParameterList().setValue("DOCUMENTTYPE", "ZDO");
        bapiDocList.getImportParameterList().setValue("STATUSINTERN", "DB");
        bapiDocList.execute(destination1);

        JCoTable docs = bapiDocList.getTableParameterList().getTable("DOCUMENTLIST");
        if (docs.getNumRows() > 0) {
            do {
                DMSdocument doc = new DMSdocument();
                doc.setId(docs.getLong("DOCUMENTNUMBER"));
                doc.setDocType(docs.getString("DOCUMENTTYPE"));
                doc.setDocPart(docs.getString("DOCUMENTPART"));
                doc.setVersion(docs.getString("DOCUMENTVERSION"));
                doc.setDescription(docs.getString("DESCRIPTION"));
                doc.setUser(docs.getString("USERNAME"));
                idmSdocumentRepository.save(doc);
            } while (docs.nextRow());
        }

        long t2 = new Date().getTime();

        showMessageAboutExecutionTime(t1, t2);
        System.out.println("End the uploading DMS documents");


    }

    public List<DMSdocument> getAllDocumentNumbers() {
        return idmSdocumentRepository.findAll();
    }

    @Async
    public void takeDetails(List<DMSdocument> docs, int parity) throws JCoException, InterruptedException {

        boolean needed = false;

        JCoDestination destination;

        System.out.printf("Start the stage number %d\n", parity);

        if ((parity % 2) == 0) {
            destination = destination1;
        } else {
            destination = destination2;
        }

        JCoFunction bapiDetail = destination.getRepository().getFunction("BAPI_DOCUMENT_GETDETAIL2");
        if (bapiDetail == null) {
            throw new RuntimeException("Function BAPI_DOCUMENT_GETDETAIL2 not found");
        }

        JCoFunction bapiVendor = destination.getRepository().getFunction("BAPI_VENDOR_GETDETAIL");
        if (bapiVendor == null) {
            throw new RuntimeException("Function BAPI_VENDOR_GETDETAIL not found");
        }
        int index = 0;
        Iterator<DMSdocument> it1 = docs.iterator();
        while (it1.hasNext()) {
            DMSdocument doc = it1.next();
            bapiDetail.getImportParameterList().setValue("DOCUMENTTYPE", doc.getDocType());
            bapiDetail
                    .getImportParameterList()
                    .setValue("DOCUMENTNUMBER", alphaTransformator.transformToExternalForm(doc.getId(), 25));
            bapiDetail.getImportParameterList().setValue("DOCUMENTPART", doc.getDocPart());
            bapiDetail.getImportParameterList().setValue("DOCUMENTVERSION", doc.getVersion());
            bapiDetail.getImportParameterList().setValue("GETACTIVEFILES", " ");
            bapiDetail.getImportParameterList().setValue("GETDOCDESCRIPTIONS", " ");
            bapiDetail.getImportParameterList().setValue("GETDOCFILES", " ");
            bapiDetail.getImportParameterList().setValue("GETCLASSIFICATION", "X");
            bapiDetail.execute(destination);
            JCoTable details = bapiDetail.getTableParameterList().getTable("CHARACTERISTICVALUES");
            if (details.getNumRows() > 0) {
                do {
                    index++;
                    Detail detail = new Detail();
                    detail.setId(index);
                    detail.setDocumentId(doc.getId());
                    String characteristic = details.getString("CHARNAME");
                    if (characteristic != null) {
                        detail.setCharacteristic(characteristic);
                        needed = true;
                    }
                    String value = details.getString("CHARVALUE");
                    if (value != null) {
                        detail.setValue(value);
                    } else {
                        detail.setValue("deadline");
                    }
                    if (needed) {
                        iDetailRepository.save(detail);
                        needed = false;
                    }
                } while (details.nextRow());
            }
        } // iterator

        System.out.printf("End the stage number %d\n", parity);

    }

    @Async
    public void changeDetailData(String param) {

        List<Detail> details = iDetailRepository.findDetailByCharacteristicLike(param);
        Iterator<Detail> it = details.iterator();
        while (it.hasNext()) {
            Detail dtl = it.next();
            LimitedContract lc = new LimitedContract();
            switch (dtl.getCharacteristic()) {
                case "Z_DMS_EXTCONTRACT#":
                    break;
            }
            contracts.put(dtl.getDocumentId(), lc);
        }

    }

    private void refreshLimitedContracts() {

        if (!contracts.isEmpty()) {
            contracts.clear();
        }

    }

    private void showMessageAboutExecutionTime(long m1, long m2) {

        long delta = (m2 - m1) / 1000;

        String timeUnit = "sec";

        if (delta > 60) {
            timeUnit = "min";
            delta /= 60;
            if (delta > 60) {
                timeUnit = "hours";
                delta /= 60;
            }
        }

        System.out.printf("Time of execution is %d %s\n", delta, timeUnit);
    }

}
