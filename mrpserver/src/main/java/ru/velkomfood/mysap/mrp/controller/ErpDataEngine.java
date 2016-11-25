package ru.velkomfood.mysap.mrp.controller;

import com.sap.conn.jco.*;
import com.sap.conn.jco.ext.DestinationDataProvider;
import ru.velkomfood.mysap.mrp.model.Material;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.*;
import java.util.*;

/**
 * Created by dpetrov on 19.10.16.
 */
public class ErpDataEngine {

    static String DEST_NAME = "PRD500RUPS15";

    private Connection connection;
    private JCoDestination jCoDestination;

    int year, month;
    private List<Material> mats;

    // Constructor's section
    public ErpDataEngine() {
        mats = new ArrayList<>();
    }

    static {
        Properties connectProperties = new Properties();
        connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST, "XXX");
        connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR, "XX");
        connectProperties.setProperty(DestinationDataProvider.JCO_R3NAME, "XXX");
        connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT, "XXX");
        connectProperties.setProperty(DestinationDataProvider.JCO_USER, "XXXXXX");
        connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD, "XXXXXXXX");
        connectProperties.setProperty(DestinationDataProvider.JCO_LANG, "XX");
        createDestinationFile(DEST_NAME, connectProperties);
    }

    static void createDestinationFile(String destName, Properties props) {

        File destCfg = new File(destName + ".jcoDestination");

        if (!destCfg.exists() && !destCfg.isDirectory()) {

            try {
                FileOutputStream fos = new FileOutputStream(destCfg, false);
                props.store(fos, "PRD instance");
                fos.close();
            } catch (Exception e) {
                throw new RuntimeException("Unable to create the destination file", e);
            }

        }
    }

    // Main public section

    public void initConnection() throws JCoException {
        jCoDestination = JCoDestinationManager.getDestination(DEST_NAME);
    }

    // Create tables in not exists.
    public void initDatabase() throws SQLException {

        String init = "CREATE TABLE IF NOT EXISTS mara (matnr INTEGER NOT NULL PRIMARY KEY, description VARCHAR(50))";

        Statement stmt = connection.createStatement();

        try {

            stmt.addBatch(init);

            init = "CREATE TABLE IF NOT EXISTS units (uom_sap VARCHAR(3) NOT NULL PRIMARY KEY, uom_iso VARCHAR(3), "
                    + "description VARCHAR(30))";
            stmt.addBatch(init);

            init = "CREATE TABLE IF NOT EXISTS mbew (matnr INTEGER NOT NULL, werks INTEGER NOT NULL, "
                    + "base_uom VARCHAR(3), pur_group VARCHAR(3), "
                    + "price_control VARCHAR(1), fixed_price DECIMAL(20,2), weighted_price DECIMAL(20,2), "
                    + "price_unit DECIMAL(20,2), PRIMARY KEY (matnr, werks))";
            stmt.addBatch(init);

            // Create table for the stocks
            init = "CREATE TABLE IF NOT EXISTS stocks (id INTEGER NOT NULL, matnr INTEGER NOT NULL, werks INTEGER NOT NULL, "
                    + "lgort INTEGER NOT NULL, "
            + "year INT INTEGER NULL, month INTEGER NOT NULL, free_stock DECIMAL(20,3), "
                    + "quality_stock DECIMAL(20,3), blocked_stock DECIMAL(20,3), "
                    + "PRIMARY KEY (id, matnr, werks, lgort, year, month))";
            stmt.addBatch(init);

            init = "CREATE TABLE IF NOT EXISTS mrpitems (matnr INTEGER NOT NULL, werks INTEGER NOT NULL, "
            + "avail_date DATE NOT NULL, per_segment VARCHAR(22) NOT NULL, pur_group VARCHAR(3), "
            + "requirement DECIMAL(20,3), "
            + "PRIMARY KEY (matnr, werks, avail_date, per_segment))";
            stmt.addBatch(init);

            stmt.executeBatch();

        } finally {

            stmt.close();

        }

    }

    // In memory DB clearing
    public void refreshDatabase() throws SQLException {

        Statement statement = connection.createStatement();

        try {

            statement.addBatch("DELETE FROM units");
            statement.addBatch("DELETE FROM mara");
            statement.addBatch("DELETE FROM mbew");
            statement.addBatch("DELETE FROM stocks");
            statement.addBatch("DELETE FROM mrpitems");
            statement.executeBatch();

        } finally {

            statement.close();

        }

    }

    // Here is a main method for the reading and transferring of data.

    public void transferData() throws JCoException, SQLException {

        // Z_RFC_GET_UNITS_MEASURE (units of measurements)
        // Z_RFC_GET_MARD2 (stocks on the warehouse)

        JCoFunction bapiMatList, bapiMatDetails,
        rfcStocks, rfcUom, bapiMrpList;
        JCoStructure row;
        JCoTable selection, materials, stocks, uoms, mrpTotals;

        final String INSMAT = "INSERT INTO mara VALUES (?, ?)";
        final String INSVAL = "INSERT INTO mbew VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        final String INSUNITS = "INSERT INTO units VALUES (?, ?, ?)";
        final String INSSTOCKS = "INSERT INTO stocks VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        final String INSREQ = "INSERT INTO mrpitems VALUES (?, ?, ?, ?, ?, ?)";

        if (!mats.isEmpty()) mats.clear();

        try {

            JCoContext.begin(jCoDestination);

            // Get the references to RFC functions and BAPIs.

            bapiMatList = jCoDestination.getRepository().getFunction("BAPI_MATERIAL_GETLIST");
            if (bapiMatList == null)
                throw new RuntimeException("Function BAPI_MATERIAL_GETLIST not found");

            bapiMatDetails = jCoDestination.getRepository().getFunction("BAPI_MATERIAL_GET_DETAIL");
            if (bapiMatDetails == null)
                throw new RuntimeException("Function BAPI_MATERIAL_GET_DETAIL not found");

            rfcStocks = jCoDestination.getRepository().getFunction("Z_RFC_GET_MARD2");
            if (rfcStocks == null)
                throw new RuntimeException("Function Z_RFC_GET_MARD2 not found");

            rfcUom = jCoDestination.getRepository().getFunction("Z_RFC_GET_UNITS_MEASURE");
            if (rfcUom == null)
                throw new RuntimeException("Function Z_RFC_GET_UNITS_MEASURE not found");

            bapiMrpList = jCoDestination.getRepository().getFunction("BAPI_MATERIAL_STOCK_REQ_LIST");
            if (bapiMrpList == null)
                throw new RuntimeException("Function BAPI_MATERIAL_STOCK_REQ_LIST not found");

            selection = bapiMatList.getTableParameterList().getTable("MATNRSELECTION");
            selection.appendRow();
            selection.setValue("SIGN", "I");
            selection.setValue("OPTION", "BT");
            selection.setValue("MATNR_LOW", "000000000010000000");
            selection.setValue("MATNR_HIGH", "000000000019999999");
            bapiMatList.execute(jCoDestination);

            materials = bapiMatList.getTableParameterList().getTable("MATNRLIST");

            // Begin the transfer of data into in-memory database "mrpdata" (H2)
            PreparedStatement pstmt1 = connection.prepareStatement(INSMAT);
            PreparedStatement pstmt2 = connection.prepareStatement(INSVAL);
            PreparedStatement pstmt3 = connection.prepareStatement(INSSTOCKS);
            PreparedStatement pstmt4 = connection.prepareStatement(INSREQ);
            PreparedStatement pstmt5 = connection.prepareStatement(INSUNITS);

            try {



                // Start the data retrieving from SAP and uploading into database.
                for (int j = 0; j < materials.getNumRows(); j++) {

                    Material me = new Material();
                    materials.setRow(j);
                    me.setMatnr(materials.getInt("MATERIAL"));
                    me.setDescription(materials.getString("MATL_DESC"));
                    pstmt1.setInt(1, me.getMatnr());
                    pstmt1.setString(2, me.getDescription());
                    pstmt1.addBatch();
                    mats.add(me);

                    bapiMatDetails.getImportParameterList().setValue("MATERIAL", materials.getString("MATERIAL"));
                    bapiMatDetails.getImportParameterList().setValue("PLANT", "1000");
                    bapiMatDetails.getImportParameterList().setValue("VALUATIONAREA", "1000");
                    bapiMatDetails.execute(jCoDestination);
                    pstmt2.setInt(1, materials.getInt("MATERIAL"));
                    pstmt2.setInt(2, 1000);
                    row = bapiMatDetails.getExportParameterList().getStructure("MATERIAL_GENERAL_DATA");
                    for (JCoField field: row) {
                       if (field.getName().equals("BASE_UOM")) pstmt2.setString(3, row.getString("BASE_UOM"));
                    }
                    row = bapiMatDetails.getExportParameterList().getStructure("MATERIALPLANTDATA");
                    for (JCoField field: row) {
                        if (field.getName().equals("PUR_GROUP")) pstmt2.setString(4, row.getString("PUR_GROUP"));
                    }
                    row = bapiMatDetails.getExportParameterList().getStructure("MATERIALVALUATIONDATA");
                    for (JCoField field: row) {
                        switch (field.getName()) {
                            case "PRICE_CTRL":
                                pstmt2.setString(5, row.getString("PRICE_CTRL"));
                                break;
                            case "MOVING_PR":
                                pstmt2.setBigDecimal(6, row.getBigDecimal("MOVING_PR"));
                                break;
                            case "STD_PRICE":
                                pstmt2.setBigDecimal(7, row.getBigDecimal("STD_PRICE"));
                                break;
                            case "PRICE_UNIT":
                                pstmt2.setBigDecimal(8, row.getBigDecimal("PRICE_UNIT"));
                                break;
                        }
                    }
                    pstmt2.addBatch();
                }
                // end of material BAPI
                pstmt1.executeBatch();
                pstmt2.executeBatch();

                // Get the current stocks data

                StringBuilder strNow = new StringBuilder(0);
                String period;
                String financialYear = String.valueOf(year);

                if (month >= 1 && month <= 9)
                    period = strNow.append("0").append(month).toString();
                else period = strNow.append(month).toString();

                int counter = 1;
                rfcStocks.getImportParameterList().setActive("I_MATNR", false);
                rfcStocks.getImportParameterList().setValue("I_WERKS", "1000");
                rfcStocks.getImportParameterList().setValue("I_YEAR", financialYear);
                rfcStocks.getImportParameterList().setValue("I_MONTH", period);
                rfcStocks.execute(jCoDestination);
                stocks = rfcStocks.getTableParameterList().getTable("T_MARD");
                for (int i = 0; i < stocks.getNumRows(); i++) {
                    stocks.setRow(i);
                    pstmt3.setInt(1, counter);
                    pstmt3.setInt(2, stocks.getInt("MATNR"));
                    pstmt3.setInt(3, stocks.getInt("WERKS"));
                    pstmt3.setInt(4, stocks.getInt("LGORT"));
                    pstmt3.setInt(5, stocks.getInt("LFGJA"));
                    pstmt3.setInt(6, stocks.getInt("LFMON"));
                    pstmt3.setBigDecimal(7, stocks.getBigDecimal("LABST")); // free
                    pstmt3.setBigDecimal(8, stocks.getBigDecimal("INSME")); // quality inspection
                    pstmt3.setBigDecimal(9, stocks.getBigDecimal("SPEME")); // blocked
                    pstmt3.addBatch();
                    counter++;
                }
                pstmt3.executeBatch();

                // Get MRP items with details
                Iterator<Material> it = mats.iterator();
                while (it.hasNext()) {
                    Material me = it.next();
                    StringBuilder stringMaterial = new StringBuilder(0);
                    stringMaterial.append("0000000000").append(me.getMatnr());
                    // Get MRP items from ERP by every material
                    bapiMrpList.getImportParameterList().setValue("MATERIAL", stringMaterial.toString());
                    bapiMrpList.getImportParameterList().setValue("PLANT", "1000");
                    bapiMrpList.getImportParameterList().setValue("PERIOD_INDICATOR", "M");
                    bapiMrpList.getImportParameterList().setValue("GET_ITEM_DETAILS", " ");
                    bapiMrpList.getImportParameterList().setValue("GET_IND_LINES", " ");
                    bapiMrpList.getImportParameterList().setValue("GET_TOTAL_LINES", "X");
                    bapiMrpList.execute(jCoDestination);
                    mrpTotals = bapiMrpList.getTableParameterList().getTable("MRP_TOTAL_LINES");
                    for (int l = 0; l < mrpTotals.getNumRows(); l++) {
                        mrpTotals.setRow(l);
                        row = bapiMrpList.getExportParameterList().getStructure("MRP_LIST");
                        for (JCoField field : row) {
                            switch (field.getName()) {
                                case "MATERIAL":
                                    pstmt4.setInt(1, field.getInt());
                                    break;
                                case "PLANT":
                                    pstmt4.setInt(2, field.getInt());
                                    break;
                                case "PUR_GROUP":
                                    pstmt4.setString(5, field.getString());
                                    break;
                            }
                        }
                        pstmt4.setString(3, mrpTotals.getString("AVAIL_DATE"));
                        pstmt4.setString(4, mrpTotals.getString("PER_SEGMT"));
                        pstmt4.setBigDecimal(6, mrpTotals.getBigDecimal("REQMTS"));
                        pstmt4.addBatch();
                    } // bapi MRP list
                }

                // Get the current stocks data

                pstmt4.executeBatch();

                // Get all units of measurements
                rfcUom.getImportParameterList().setValue("LANGUAGE", "RU");
                rfcUom.execute(jCoDestination);

                uoms = rfcUom.getTableParameterList().getTable("UNITS");

                for (int k = 0; k < uoms.getNumRows(); k++) {
                    uoms.setRow(k);
                    pstmt5.setString(1, uoms.getString("UOM_SAP"));
                    pstmt5.setString(2, uoms.getString("UOM_ISO"));
                    pstmt5.setString(3, uoms.getString("UOM_DESCLONG"));
                    pstmt5.addBatch();
                }

                // database updating
                pstmt5.executeBatch();

            } finally {

                pstmt1.close();
                pstmt2.close();
                pstmt3.close();
                pstmt4.close();
                pstmt5.close();

            }

        } finally {

            JCoContext.end(jCoDestination);

        }

    } // end of data transferring



    // setters and getters


    public JCoDestination getjCoDestination() {
        return jCoDestination;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

}
