package ru.velkomfood.fin.cash.server.persistence;

import com.sap.conn.jco.*;
import com.sap.conn.jco.ext.DestinationDataProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.velkomfood.fin.cash.server.model.master.Company;
import ru.velkomfood.fin.cash.server.model.master.Material;
import ru.velkomfood.fin.cash.server.model.master.Partner;
import ru.velkomfood.fin.cash.server.model.transaction.CashDocument;
import ru.velkomfood.fin.cash.server.model.transaction.DeliveryHead;
import ru.velkomfood.fin.cash.server.model.transaction.DeliveryItem;
import ru.velkomfood.fin.cash.server.model.transaction.DeliveryItemId;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by dpetrov on 22.06.17.
 */
@Component
public class SAPEngine {

    final static String DEST = "PRD500";
    final static String SUFFIX = ".jcoDestination";

    private Properties connProperties;
    private JCoDestination destination;

    @Autowired
    private DBEngine dbEngine;

    public SAPEngine() {
        connProperties = new Properties();
        connProperties.setProperty(DestinationDataProvider.JCO_ASHOST, "");
        connProperties.setProperty(DestinationDataProvider.JCO_SYSNR, "");
        connProperties.setProperty(DestinationDataProvider.JCO_R3NAME, "");
        connProperties.setProperty(DestinationDataProvider.JCO_CLIENT, "");
        connProperties.setProperty(DestinationDataProvider.JCO_USER, "");
        connProperties.setProperty(DestinationDataProvider.JCO_PASSWD, "");
        connProperties.setProperty(DestinationDataProvider.JCO_LANG, "");
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

    // Run this method after the creation an instance of component
    @PostConstruct
    public void initSAPDestination() throws JCoException {

        destination = JCoDestinationManager.getDestination(DEST);

    }

    // Get all information about company code
    public Company readCompanyData() throws JCoException {

        Company company = new Company();
        company.setId("1000");

        JCoFunction bapiCompanyDetail = destination
                .getRepository()
                .getFunction("BAPI_COMPANYCODE_GETDETAIL");

        if (bapiCompanyDetail == null) {
            throw new RuntimeException("Function BAPI_COMPANYCODE_GETDETAIL not found");
        }

        bapiCompanyDetail
                .getImportParameterList()
                .setValue("COMPANYCODEID", company.getId());

        bapiCompanyDetail.execute(destination);

        JCoStructure companyData = bapiCompanyDetail
                .getExportParameterList()
                .getStructure("COMPANYCODE_ADDRESS");

        for (JCoField f: companyData) {
            switch (f.getName()) {
                case "NAME":
                    company.setName1(f.getString());
                    break;
                case "NAME_2":
                    company.setName2(f.getString());
                    break;
                case "COUNTRY":
                    company.setCountry(f.getString());
                    break;
                case "POSTL_COD1":
                    company.setPostcode(f.getString());
                    break;
                case "CITY":
                    company.setCity(f.getString());
                    break;
                case "STREET":
                    company.setStreet(f.getString());
                    break;
                case "HOUSE_NO":
                    company.setBuilding(f.getString());
                    break;
                case "TEL1_NUMBR":
                    company.setPhone(f.getString());
                    break;
                case "FAX_NUMBER":
                    company.setFax(f.getString());
                    break;
            }
        }

        return company;
    }

    public int readAllMaterialsFromSAP() throws JCoException, SQLException {

        final BigDecimal ZERO = new BigDecimal(0.000);

        int counter = 0;

        JCoFunction bapiMatList = destination
                .getRepository()
                .getFunction("BAPI_MATERIAL_GETLIST");

        if (bapiMatList == null) {
            throw new RuntimeException("Function BAPI_MATERIAL_GETLIST not found");
        }

        JCoFunction bapiMatDetails = destination
                .getRepository()
                .getFunction("BAPI_MATERIAL_GET_DETAIL");

        if (bapiMatDetails == null) {
            throw new RuntimeException("Function BAPI_MATERIAL_GET_DETAIL not found");
        }

        JCoTable matSels = bapiMatList.getTableParameterList().getTable("MATNRSELECTION");

        // Define material ranges for the searching
        matSels.appendRow();
        matSels.setValue("SIGN", "I");
        matSels.setValue("OPTION", "BT");
        matSels.setValue("MATNR_LOW", "000000000000070000");
        matSels.setValue("MATNR_HIGH", "000000000000079999");

        matSels.appendRow();
        matSels.setValue("SIGN", "I");
        matSels.setValue("OPTION", "BT");
        matSels.setValue("MATNR_LOW", "000000000010000001");
        matSels.setValue("MATNR_HIGH", "000000000019999999");

        matSels.appendRow();
        matSels.setValue("SIGN", "I");
        matSels.setValue("OPTION", "BT");
        matSels.setValue("MATNR_LOW", "000000000040000000");
        matSels.setValue("MATNR_HIGH", "000000000049999999");

        matSels.appendRow();
        matSels.setValue("SIGN", "I");
        matSels.setValue("OPTION", "BT");
        matSels.setValue("MATNR_LOW", "000000000060000000");
        matSels.setValue("MATNR_HIGH", "000000000069999999");

        // Multiple calls of BAPI (Business Application Programming Interface)
        // BAPI in SAP components are simple functions for remote invoking
        try {

            JCoContext.begin(destination);

            bapiMatList.execute(destination);
            JCoTable matab = bapiMatList.getTableParameterList().getTable("MATNRLIST");

            if (matab.getNumRows() > 0) {

                do {

                    Material m = new Material();

                    String txtMaterial = matab.getString("MATERIAL");
                    m.setId(matab.getLong("MATERIAL"));
                    m.setDescription(matab.getString("MATL_DESC"));
                    bapiMatDetails.getImportParameterList().setValue("MATERIAL", txtMaterial);
                    bapiMatDetails.getImportParameterList().setValue("PLANT", "1000");
                    bapiMatDetails.getImportParameterList().setValue("VALUATIONAREA", "1000");
                    // Detailed information about any material
                    bapiMatDetails.execute(destination);
                    JCoStructure generalData = bapiMatDetails.getExportParameterList().getStructure("MATERIAL_GENERAL_DATA");
                    // Unit of measurements
                    for (JCoField f: generalData) {
                        if (f.getName().equals("BASE_UOM")) {
                            m.setUom(f.getString());
                        }
                    }
                    // Cost of material
                    Map<String, BigDecimal> prices = new HashMap<>();
                    JCoStructure valuation = bapiMatDetails.getExportParameterList().getStructure("MATERIALVALUATIONDATA");
                    for (JCoField f: valuation) {
                        switch (f.getName()) {
                            case "STD_PRICE":
                                prices.put(f.getName(), f.getBigDecimal());
                                break;
                            case "MOVING_PR":
                                prices.put(f.getName(), f.getBigDecimal());
                                break;
                            case "PRICE_UNIT":
                                m.setPriceUnit(f.getBigDecimal());
                        }
                    }
                    if (!prices.isEmpty()) {
                        BigDecimal value = prices.get("STD_PRICE");
                        if (value.equals(ZERO)) {
                            value = prices.get("MOVING_PR");
                        }
                        if (!m.getPriceUnit().equals(ZERO)) {
                            value = value.divide(m.getPriceUnit());
                        }
                        m.setCost(value);
                    }
                    // Update or insert the material in the database
                    dbEngine.saveMaterial(m);
                    counter++;

                } while (matab.nextRow());

            } // number rows is not null

        } finally {

            JCoContext.end(destination);

        }

        return counter;

    } // read all materials

    public int readAllPartnersFromSAP() throws JCoException, SQLException {

        int counter = 0;

        final String DASH = "-";

        JCoFunction bapiCustomerList = destination.getRepository().getFunction("BAPI_CUSTOMER_GETLIST");
        if (bapiCustomerList == null) {
            throw new RuntimeException("Function BAPI_CUSTOMER_GETLIST not found");
        }

        JCoFunction bapiVendorList = destination.getRepository().getFunction("BBP_VENDOR_GET_LIST3");
        if (bapiVendorList == null) {
            throw new RuntimeException("Function BBP_VENDOR_GET_LIST3 not found");
        }

        JCoFunction bapiVendorDetail = destination.getRepository().getFunction("BAPI_VENDOR_GETDETAIL");
        if (bapiVendorDetail == null) {
            throw new RuntimeException("Function BAPI_VENDOR_GETDETAIL not found");
        }

        // multiple invoking to the RFC functions
        try {

            JCoContext.begin(destination);

            // Search only the headquarter
            JCoTable range = bapiCustomerList.getTableParameterList().getTable("IDRANGE");

            range.appendRow();
            range.setValue("SIGN", "I");
            range.setValue("OPTION", "BT");
            range.setValue("LOW", "0010000000");
            range.setValue("HIGH", "0019999999");

            bapiCustomerList.execute(destination);

            JCoTable customerInfo = bapiCustomerList.getTableParameterList().getTable("ADDRESSDATA");

            if (customerInfo.getNumRows() > 0) {

                do {

                    Partner p1 = new Partner();

                    p1.setIndicator(1);
                    p1.setId(p1.getIndicator() + DASH + customerInfo.getString("CUSTOMER"));
                    p1.setName(customerInfo.getString("NAME"));
                    p1.setCountry(customerInfo.getString("COUNTRYISO"));
                    p1.setPostcode(customerInfo.getString("POSTL_COD1"));
                    p1.setCity(customerInfo.getString("CITY"));
                    p1.setStreet(customerInfo.getString("STREET"));

                    dbEngine.savePartner(p1);
                    counter++;

                } while (customerInfo.nextRow());

            } // customer list processing

            // Now we can upload vendors
            bapiVendorList.getImportParameterList().setValue("WO_PURCH_ORG", "X");

            bapiVendorList.execute(destination);

            JCoTable vendorList = bapiVendorList.getTableParameterList().getTable("VENDOR_TAB");

            if (vendorList.getNumRows() > 0) {

                do {

                    Partner p2 = new Partner();

                    p2.setIndicator(2);
                    String txtVendor = vendorList.getString("LIFNR");
                    p2.setId(p2.getIndicator() + DASH + txtVendor);

                    bapiVendorDetail.getImportParameterList().setValue("VENDORNO", txtVendor);
                    bapiVendorDetail.getImportParameterList().setValue("COMPANYCODE", "1000");

                    bapiVendorDetail.execute(destination);

                    JCoStructure generalInfo = bapiVendorDetail
                            .getExportParameterList()
                            .getStructure("GENERALDETAIL");

                    for (JCoField f: generalInfo) {
                        switch (f.getName()) {
                            case "NAME":
                                p2.setName(f.getString());
                                break;
                            case "COUNTRYISO":
                                p2.setCountry(f.getString());
                                break;
                            case "POSTL_CODE":
                                p2.setPostcode(f.getString());
                                break;
                            case "CITY":
                                p2.setCity(f.getString());
                                break;
                            case "STREET":
                                p2.setStreet(f.getString());
                                break;
                        }
                    }

                    dbEngine.savePartner(p2);
                    counter++;

                } while (vendorList.nextRow());

            }

        } finally {

            JCoContext.end(destination);

        }

        return counter;
    }

    // Read cash documents and deliveries
    public void readCashDocumentsByDate(java.sql.Date fromDate, java.sql.Date toDate) throws JCoException {

        String sapDateFrom, sapDateTo;

        if (fromDate.equals(toDate)) {
            sapDateFrom = convertDateToSAPFormat(fromDate);
            sapDateTo = sapDateFrom;
        } else {
            sapDateFrom = convertDateToSAPFormat(fromDate);
            sapDateTo = convertDateToSAPFormat(toDate);
        }

        JCoFunction rfcCashDoc = destination.getRepository().getFunction("Z_RFC_GET_CASHDOC");
        if (rfcCashDoc == null) {
            throw new RuntimeException("Function Z_RFC_GET_CASHDOC not found");
        }

        rfcCashDoc.getImportParameterList().setValue("I_COMP_CODE", "1000");
        rfcCashDoc.getImportParameterList().setValue("I_CAJO_NUMBER", "1000");
        rfcCashDoc.getImportParameterList().setValue("FROM_DATE", sapDateFrom);
        rfcCashDoc.getImportParameterList().setValue("TO_DATE", sapDateTo);

        try {

            JCoContext.begin(destination);

            rfcCashDoc.execute(destination);
            JCoTable docs = rfcCashDoc.getTableParameterList().getTable("T_CJ_DOCS");

            if (docs.getNumRows() > 0) {

                do {

                    String txtPosition = docs.getString("POSITION_TEXT");

                    if (txtPosition.charAt(0) == '8' ||
                            (txtPosition.charAt(0) == '0' && txtPosition.charAt(1) == '8')) {

                        String[] posTxt = txtPosition.split(" ");
                        long delivery = Long.parseLong(posTxt[0]);

                        CashDocument cd = new CashDocument();
                        cd.setId(docs.getLong("POSTING_NUMBER"));
                        cd.setCajoNumber(docs.getString("CAJO_NUMBER"));
                        cd.setCompanyId(docs.getString("COMP_CODE"));
                        cd.setYear(docs.getInt("FISC_YEAR"));
                        cd.setPostingDate(java.sql.Date.valueOf(docs.getString("POSTING_DATE")));
                        cd.setPositionText(txtPosition);
                        cd.setDeliveryId(delivery);
                        cd.setAmount(docs.getBigDecimal("H_NET_AMOUNT"));
                        dbEngine.saveCashDocument(cd);
                        readDeliveryInfoByKey(delivery, cd.getCompanyId());
                    }

                } while (docs.nextRow());

            } // number rows

        } finally {

            JCoContext.end(destination);

        }

    } // cash documents, deliveries

    // Additional methods

    // Build an internal date format for SAP systems
    // You should send a date into ISO form "yyyy-MM-dd"
    private String convertDateToSAPFormat(java.sql.Date value) {


        String[] temp = value.toLocalDate().toString().split("-");

        String txtValue = temp[0] + temp[1] + temp[2]; // yyyyMMdd

        if (temp.length  != 3) {
            return "Error";
        } else {
            return txtValue;
        }
    }

    private void readDeliveryInfoByKey(long deliveryId, String companyCode) throws JCoException {

        JCoFunction bapiDeList = destination.getRepository().getFunction("BAPI_DELIVERY_GETLIST");
        if (bapiDeList == null) {
            throw new RuntimeException("Function BAPI_DELIVERY_GETLIST not found");
        }

        bapiDeList.getImportParameterList()
                .getStructure("IS_DLV_DATA_CONTROL")
                .setValue("ITEM", "X");
        bapiDeList.getImportParameterList()
                .getStructure("IS_DLV_DATA_CONTROL")
                .setValue("ITEM_STATUS", "X");
        bapiDeList.getImportParameterList()
                .getStructure("IS_DLV_DATA_CONTROL")
                .setValue("DOC_FLOW", "X");

        JCoTable tabVbeln = bapiDeList.getTableParameterList().getTable("IT_VBELN");
        tabVbeln.appendRow();
        tabVbeln.setValue("SIGN", "I");
        tabVbeln.setValue("OPTION", "EQ");
        tabVbeln.setValue("DELIV_NUMB_LOW", alphaTransform(deliveryId));
        tabVbeln.setValue("DELIV_NUMB_HIGH", alphaTransform(deliveryId));

        bapiDeList.execute(destination);

        JCoTable head = bapiDeList.getTableParameterList().getTable("ET_DELIVERY_HEADER");
        if (head.getNumRows() > 0) {
            do {
                DeliveryHead dh = new DeliveryHead();
                dh.setId(head.getLong("VBELN"));
                dh.setCompanyId(companyCode);
                dh.setPartnerId(head.getString("KUNAG"));
                java.util.Date dt = head.getDate("WADAT");
                java.sql.Date sdt = new java.sql.Date(dt.getTime());
                dh.setPostingDate(sdt);
                dbEngine.saveDeliveryHead(dh);
            } while (head.nextRow());
        }

        // Get the prices and VAT rates
        Map<Long, BigDecimal[]> salesInfo = readSalesOrderByDelivery(deliveryId);

        JCoTable items = bapiDeList.getTableParameterList().getTable("ET_DELIVERY_ITEM");
        if (items.getNumRows() > 0) {
            do {
                DeliveryItem dit = new DeliveryItem();
                dit.setId(items.getLong("VBELN"));
                dit.setPosition(items.getLong("POSNR"));
                dit.setMaterialId(items.getLong("MATNR"));
                dit.setQuantity(items.getBigDecimal("LFIMG"));
                BigDecimal[] sd = salesInfo.get(dit.getMaterialId());
                if (sd.length == 2) {
                    dit.setPrice(sd[0]);
                    dit.setVat(sd[1]);
                }
                dbEngine.saveDeliveryItem(dit);
            } while (items.nextRow());
        }

    } // read a delivery by key

    private Map<Long, BigDecimal[]> readSalesOrderByDelivery(long key) {

        // BigDecimal array with the length equals 2
        Map<Long, BigDecimal[]> prices = new ConcurrentHashMap<>();


        return prices;
    }

    // Add initial zeroes
    private String alphaTransform(long value) {

        final int MAX_LENGTH = 10; // Length of field in SAP
        String txtValue = String.valueOf(value);

        int diff = MAX_LENGTH - txtValue.length();

        if (diff > 0) {
            for (int i = 1; i <= diff; i++) {
                txtValue = "0" + txtValue;
            }
        }

        return txtValue;
    }

}