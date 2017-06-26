package ru.velkomfood.fin.cash.server.persistence;

import com.sap.conn.jco.*;
import com.sap.conn.jco.ext.DestinationDataProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.velkomfood.fin.cash.server.model.master.Company;
import ru.velkomfood.fin.cash.server.model.master.Material;
import ru.velkomfood.fin.cash.server.model.master.Partner;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;

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
        connProperties.setProperty(DestinationDataProvider.JCO_LANG, "RU");
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
                        }
                    }
                    if (!prices.isEmpty()) {
                        BigDecimal value = prices.get("STD_PRICE");
                        if (value.equals(new BigDecimal(0.000))) {
                            value = prices.get("MOVING_PR");
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

}
