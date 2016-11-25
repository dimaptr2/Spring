package ru.velkomfood.mysap.services.controller;

import com.sap.conn.jco.*;
import ru.velkomfood.mysap.services.model.entities.MaterialEntity;
import ru.velkomfood.mysap.services.model.entities.MrpStockReqListEntity;
import ru.velkomfood.mysap.services.model.entities.PurchaseGroupEntity;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by DPetrov on 06.06.2016.
 */

public class ErpDataEngine {

    static String DEST_NAME = "PRD500";
    private JCoDestination jCoDestination;
    // Primary requirements
//    final String BAPI_MRP_LIST = "BAPI_MATERIAL_MRP_LIST";
    // Secondary requirements
    final String BAPI_STOCK_REQ_LIST = "BAPI_MATERIAL_STOCK_REQ_LIST";

    private List<Map<String, String>> materials;
    private List<PurchaseGroupEntity> purgroups;
    private List<MrpStockReqListEntity> stockReqListEntities;

    public void createErpConnection() throws JCoException {
            jCoDestination = JCoDestinationManager.getDestination(DEST_NAME);
    }

    public boolean testSapConnection() throws JCoException {

        if (jCoDestination.isValid()) {
            materials = new ArrayList<>();
            purgroups = new ArrayList<>();
            stockReqListEntities = new ArrayList<>();
            return true;
        }
        else {
            return false;
        }

    }

    // setters and getters


    // Business logic

    // For testing purpose
    public void showSapAttributes() throws JCoException {

        if (jCoDestination.isValid()) {

            jCoDestination.ping();
            System.out.println(jCoDestination.getAttributes());
            System.out.println();

        }

    }

    // Get list of materials from SAP.
    public List<Map<String, String>> getMaterials() throws JCoException {

        JCoFunction bapiMatList;
        JCoTable matselection;
        JCoTable matnr_table;

        if (!materials.isEmpty()) materials.clear();

        bapiMatList = jCoDestination.getRepository().getFunction("BAPI_MATERIAL_GETLIST");
        if (bapiMatList == null) {
            throw new RuntimeException("Function BAPI_MATERIAL_GETLIST not found");
        }

        // First of all, we must fill selection criteria.
        matselection = bapiMatList.getTableParameterList().getTable("MATNRSELECTION");
        matselection.appendRow();
        matselection.setValue("SIGN", "I");
        matselection.setValue("OPTION", "BT");
        matselection.setValue("MATNR_LOW", "0");
        matselection.setValue("MATNR_HIGH", "999999999999999999");

        // Execute BAPI method into SAP.
        // Get all materials from SAP
        bapiMatList.execute(jCoDestination);

        matnr_table = bapiMatList.getTableParameterList().getTable("MATNRLIST");

        for (int i = 0; i < matnr_table.getNumRows(); i++) {

            Map<String, String> materialEntity = new ConcurrentHashMap<>();

            matnr_table.setRow(i);
//            String temp = matnr_table.getString("MATERIAL");
//            // Remove initial zeros.
//            temp = temp.replaceFirst("^0*", "");
//            if (temp.isEmpty()) continue;
            materialEntity.put("matnr", matnr_table.getString("MATERIAL"));
            materialEntity.put("maktx", matnr_table.getString("MATL_DESC"));

            materials.add(materialEntity);

        }

        return materials;
    }

    public List<PurchaseGroupEntity> getPurgroups() throws JCoException {

//      Function module in SAP
//      for the reading any table
//      FM called: RFC_GET_TABLE_ENTRIES

        JCoFunction rfcGetTable;
        JCoTable entries;
        String numerals = "";
        String letters = "";


        if (!purgroups.isEmpty()) purgroups.clear();

        rfcGetTable = jCoDestination.getRepository().getFunction("RFC_GET_TABLE_ENTRIES");
        if (rfcGetTable == null) {
            throw new RuntimeException("Function RFC_GET_TABLE_ENTRIES was not found");
        }

        // SAP Table T024 contains data about purchase groups.
        rfcGetTable.getImportParameterList().setValue("TABLE_NAME", "T024");
        // Run BAPI function in SAP
        rfcGetTable.execute(jCoDestination);
        // Get data from SAP
        entries = rfcGetTable.getTableParameterList().getTable("ENTRIES");

        for (int k = 0; k <= entries.getNumRows(); k++) {
            entries.setRow(k);
            String[] row = entries.getString("WA").split(" ");
            for (char ch: row[0].toCharArray()) {
              if (ch == '0' || ch == '1' || ch == '2'
                      || ch == '3' || ch == '4' || ch == '5'
                      || ch == '6' || ch == '7' || ch == '8' || ch == '9') {
                  numerals += ch;
              } else {
                  letters += ch;
              }
            } // string cycle
            PurchaseGroupEntity pen = new PurchaseGroupEntity();
            pen.setId(numerals);
            numerals = "";
            pen.setDescription(letters);
            letters = "";
            purgroups.add(pen);
        }

        return purgroups;
    }

    public List<MrpStockReqListEntity> findMaterialRequirementsPlanning(MaterialEntity matInfo)
            throws JCoException {

        JCoFunction bapiMatReqList;
        JCoStructure mrpHeader;
        JCoTable mrpItems;

        if (!stockReqListEntities.isEmpty()) stockReqListEntities.clear();

        bapiMatReqList = jCoDestination.getRepository().getFunction(BAPI_STOCK_REQ_LIST);
        if (bapiMatReqList == null) {
            throw new RuntimeException("Function " + BAPI_STOCK_REQ_LIST + " not found");
        }

        bapiMatReqList.getImportParameterList().setValue("MATERIAL", matInfo.getMatnr());
        bapiMatReqList.getImportParameterList().setValue("PLANT", "1000");
        bapiMatReqList.getImportParameterList().setValue("PERIOD_INDICATOR", "M"); //per month
        bapiMatReqList.getImportParameterList().setValue("GET_IND_LINES", " ");
        bapiMatReqList.getImportParameterList().setValue("GET_TOTAL_LINES", "X");
        bapiMatReqList.execute(jCoDestination);
        mrpHeader = bapiMatReqList.getExportParameterList().getStructure("MRP_LIST");
        mrpItems = bapiMatReqList.getTableParameterList().getTable("MRP_TOTAL_LINES");

        // BAPI execution that processes the list "stockReqListEntities"
        processSapTable(mrpHeader, mrpItems);

        return stockReqListEntities;

    }  // findAllMaterialRequirementsPlanning

    private void processSapTable(JCoStructure head, JCoTable tab) {

        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

        for (int j = 0; j < tab.getNumRows(); j++) {
            // Items processing ...
            tab.setRow(j);

            MrpStockReqListEntity entity = new MrpStockReqListEntity();
            // Header processing ...
            for (JCoField field: head) {
                switch (field.getName()) {
                    case "MATERIAL":
                        entity.setMaterial(field.getString());
                        break;
                    case "PLANT":
                        entity.setPlant(field.getInt());
                        break;
                    case "BASE_UOM":
                        entity.setBaseUnit(field.getString());
                        break;
                    case "PUR_GROUP":
                        entity.setPurchaseGroup(field.getString());
                        break;
                }
            }

            entity.setMrpDate(tab.getString("AVAIL_DATE"));
            entity.setPer_segmt(tab.getString("PER_SEGMT"));
            entity.setPri_rq_quantity(tab.getBigDecimal("PLD_IND_REQS"));
            entity.setSec_req_quantity(tab.getBigDecimal("REQMTS"));
            entity.setAvail_quantity(tab.getBigDecimal("AVAIL_QTY"));
            stockReqListEntities.add(entity);
        }

    } // processSapTable

} // end of class
