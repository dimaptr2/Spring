package ru.velkomfood.mrp3.reports.controller;

import com.sap.conn.jco.*;
import com.sap.conn.jco.ext.DestinationDataProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.velkomfood.mrp3.reports.model.md.Material;
import ru.velkomfood.mrp3.reports.model.md.PurchaseGroup;
import ru.velkomfood.mrp3.reports.model.md.Warehouse;
import ru.velkomfood.mrp3.reports.model.td.Requirement;
import ru.velkomfood.mrp3.reports.model.td.Stock;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Component
public class SapReader {

    final String DEST_NAME = "XXX";
    final String SUFFIX = ".jcoDestination";
    private Properties connProperties;
    private JCoDestination destination;

    @Autowired
    private DbChanger dbChanger;

    public SapReader() {
        connProperties = new Properties();
        connProperties.setProperty(DestinationDataProvider.JCO_ASHOST, "");
        connProperties.setProperty(DestinationDataProvider.JCO_SYSNR, "");
        connProperties.setProperty(DestinationDataProvider.JCO_R3NAME, "");
        connProperties.setProperty(DestinationDataProvider.JCO_CLIENT, "");
        connProperties.setProperty(DestinationDataProvider.JCO_USER, "");
        connProperties.setProperty(DestinationDataProvider.JCO_PASSWD, "");
        connProperties.setProperty(DestinationDataProvider.JCO_LANG, "");
    }

    @PostConstruct
    public void initialize() throws JCoException {
        createDestinationDataFile(connProperties);
        destination = JCoDestinationManager.getDestination(DEST_NAME);
    }

    // Read additional dictionaries
    public void readPurchaseStructure() throws JCoException {

        JCoFunction rfc1 = destination.getRepository().getFunction("RFC_READ_TABLE");
        if (rfc1 == null) throw new RuntimeException("Function RFC_READ_TABLE not found");

        rfc1.getImportParameterList().setValue("QUERY_TABLE", "T001L");
        rfc1.getImportParameterList().setValue("DELIMITER", "-");

        JCoTable options1 = rfc1.getTableParameterList().getTable("OPTIONS");
        options1.appendRow();
        options1.setValue("TEXT", "WERKS = 1000");

        JCoTable fields1 = rfc1.getTableParameterList().getTable("FIELDS");
        fields1.appendRow();
        fields1.setValue("FIELDNAME", "LGORT");
        fields1.appendRow();
        fields1.setValue("FIELDNAME", "LGOBE");

        rfc1.execute(destination);

        JCoTable data1 = rfc1.getTableParameterList().getTable("DATA");
        if (data1.getNumRows() > 0) {
            do {
                String[] txt1 = data1.getString("WA").split("-");
                Warehouse whs = new Warehouse(txt1[0], txt1[1]);
                dbChanger.saveWarehouse(whs);
            } while (data1.nextRow());
        }

        rfc1.getImportParameterList().setValue("QUERY_TABLE", "T024");
        rfc1.getImportParameterList().setValue("DELIMITER", "-");

        options1.deleteAllRows();
        fields1.deleteAllRows();
        data1.deleteAllRows();

        fields1.appendRow();
        fields1.setValue("FIELDNAME", "EKGRP");
        fields1.appendRow();
        fields1.setValue("FIELDNAME", "EKNAM");

        rfc1.execute(destination);

        data1 = rfc1.getTableParameterList().getTable("DATA");
        if (data1.getNumRows() > 0) {
            do {
                String[] txt2 = data1.getString("WA").split("-");
                PurchaseGroup pg = new PurchaseGroup(txt2[0], txt2[1]);
                dbChanger.savePurchaseGroup(pg);
            } while (data1.nextRow());
        }

    }

    // Read the materials dictionary
    public List<Material> readAllMaterials() throws JCoException {

        List<Material> materials = new ArrayList<>();

        JCoFunction bapi = destination.getRepository().getFunction("BAPI_MATERIAL_GETLIST");

        JCoTable selections = bapi.getTableParameterList().getTable("MATNRSELECTION");
        selections.appendRow();
        selections.setValue("SIGN", "I");
        selections.setValue("OPTION", "BT");
        selections.setValue("MATNR_LOW", "000000000010000000");
        selections.setValue("MATNR_HIGH", "000000000019999999");

        bapi.execute(destination);

        JCoTable matList = bapi.getTableParameterList().getTable("MATNRLIST");

        // Fill the database and a collection with materials
        if (matList.getNumRows() > 0) {
            do {
                Material m = new Material(matList.getLong("MATERIAL"), matList.getString("MATL_DESC"));
                materials.add(m);
                dbChanger.saveMaterial(m);
            } while (matList.nextRow());
        }

        return materials;
    }

    // Read stocks and material requirements
    public void readStocksAndRequirementsByMaterialId(long id) throws JCoException {

        JCoFunction rfc = destination.getRepository().getFunction("Z_RFC_READ_MRP2_DATA");
        if (rfc == null) {
            throw new RuntimeException("Function Z_RFC_READ_MRP2_DATA not found");
        }

        String txtId = alphaTrasformMaterialNumber(id);
        rfc.getImportParameterList().setValue("I_MATERIAL", txtId);
        rfc.getImportParameterList().setValue("I_PLANT", "1000");
        rfc.getImportParameterList().setValue("GET_DETAILS", "X");

        rfc.execute(destination);

        JCoTable tabStocks = rfc.getTableParameterList().getTable("T_STOCKS");
        if (tabStocks.getNumRows() > 0) {
            do {
                Stock stock = new Stock(
                        tabStocks.getInt("PLANT"),
                        tabStocks.getString("WAREHOUSE"),
                        tabStocks.getLong("MATERIAL"),
                        tabStocks.getInt("PERIOD"),
                        tabStocks.getInt("FYEAR"),
                        tabStocks.getString("UOM"),
                        tabStocks.getBigDecimal("FREEQ"),
                        tabStocks.getBigDecimal("MOVINGQ"),
                        tabStocks.getBigDecimal("QUALITYQ"),
                        tabStocks.getBigDecimal("BLOCKQ")
                );
                dbChanger.saveStock(stock);
            } while (tabStocks.nextRow());
        }

        JCoTable tabReqs = rfc.getTableParameterList().getTable("T_TOTALS");
        if (tabReqs.getNumRows() > 0) {
            do {
                Requirement req = new Requirement();
                req.setPlant(tabReqs.getInt("PLANT"));
                req.setMaterial(tabReqs.getLong("MATERIAL"));
                req.setPurchaseGroup(tabReqs.getString("PURGRP"));
                req.setYear(tabReqs.getInt("FYEAR"));
                req.setUom(tabReqs.getString("UOM"));
                req.setReq01(tabReqs.getBigDecimal("REQ01"));
                req.setReq02(tabReqs.getBigDecimal("REQ02"));
                req.setReq03(tabReqs.getBigDecimal("REQ03"));
                req.setReq04(tabReqs.getBigDecimal("REQ04"));
                req.setReq05(tabReqs.getBigDecimal("REQ05"));
                req.setReq06(tabReqs.getBigDecimal("REQ06"));
                req.setReq07(tabReqs.getBigDecimal("REQ07"));
                req.setReq08(tabReqs.getBigDecimal("REQ08"));
                req.setReq09(tabReqs.getBigDecimal("REQ09"));
                req.setReq10(tabReqs.getBigDecimal("REQ10"));
                req.setReq11(tabReqs.getBigDecimal("REQ11"));
                req.setReq12(tabReqs.getBigDecimal("REQ12"));
                dbChanger.saveRequirement(req);
            } while (tabReqs.nextRow());
        }

    }

    // PRIVATE SECTION

    private void createDestinationDataFile(Properties props) {

        File cfg = new File(DEST_NAME + SUFFIX);
        if (!cfg.exists()) {
            try {
                OutputStream os = new FileOutputStream(cfg, false);
                props.store(os, "Productive environment");
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Alpha transformation of material number
    private String alphaTrasformMaterialNumber(long matNumb) {

        String txtNumb = String.valueOf(matNumb);
        StringBuilder sb = new StringBuilder(0);

        final int maxLen = 18;
        int delta = maxLen - txtNumb.length();

        if (delta > 0) {
            for (int i = 1; i <= delta; i++) {
                sb.append("0");
            }
        }

        sb.append(txtNumb);

        return sb.toString();
    }

}
