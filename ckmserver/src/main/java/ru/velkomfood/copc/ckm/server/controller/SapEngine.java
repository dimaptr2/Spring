package ru.velkomfood.copc.ckm.server.controller;

import com.sap.conn.jco.*;
import com.sap.conn.jco.ext.DestinationDataProvider;
import ru.velkomfood.copc.ckm.server.model.AccountEntity;
import ru.velkomfood.copc.ckm.server.model.CostEstimateHeaders;
import ru.velkomfood.copc.ckm.server.model.MaterialEntity;
import ru.velkomfood.copc.ckm.server.model.MaterialValuationEntity;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class SapEngine {

    static String DEST_NAME = "PRD500";
    private JCoDestination jCoDestination;
    private Map<Integer, MaterialEntity> materials;
    private Map<Integer, MaterialValuationEntity> valuations;
    private List<AccountEntity> accounts;
    private List<CostEstimateHeaders> estimateHeaders;

    public SapEngine() {
        materials = new ConcurrentHashMap<>();
        valuations = new ConcurrentHashMap<>();
        accounts = new ArrayList<>();
        estimateHeaders = new ArrayList<>();
    }

    static {
    
    }

    static void createDestinationDataFile(String destinationName, Properties connProps) {

        File destCfg = new File(destinationName + ".jcoDestination");
        try {
            if (!destCfg.exists()) {
                FileOutputStream fos = new FileOutputStream(destCfg, false);
                connProps.store(fos, "SAP PRD");
                fos.close();
            }
        } catch (Exception e) {
            throw new RuntimeException("Unable to create the destination file", e);
        }
    }

    public void initDestination() throws JCoException {

        if (jCoDestination == null || !jCoDestination.isValid()) {
            jCoDestination = JCoDestinationManager.getDestination(DEST_NAME);
        }

    }

    public void searchMaterials() throws JCoException {

        if (!materials.isEmpty()) materials.clear();
        if (!valuations.isEmpty()) valuations.clear();

        JCoFunction bapiGetMatList = jCoDestination.getRepository().getFunction("BAPI_MATERIAL_GETLIST");
        if (bapiGetMatList == null) throw new RuntimeException("Cannot found FM BAPI_MATERIAL_GETLIST");

        JCoFunction bapiMatGetDetail = jCoDestination.getRepository().getFunction("BAPI_MATERIAL_GET_DETAIL");
        if (bapiMatGetDetail == null) throw new RuntimeException("Cannot found FM BAPI_MATERIAL_GET_DETAIL");

        JCoTable matnrSelection = bapiGetMatList.getTableParameterList().getTable("MATNRSELECTION");
        matnrSelection.appendRow();
        matnrSelection.setValue("SIGN", "I");
        matnrSelection.setValue("OPTION", "BT");
        matnrSelection.setValue("MATNR_LOW", "000000000000000000");
        matnrSelection.setValue("MATNR_HIGH", "999999999999999999");
        // Start the multiple BAPI execution
        try {

            JCoContext.begin(jCoDestination);
            bapiGetMatList.execute(jCoDestination);
            JCoTable matListTable = bapiGetMatList.getTableParameterList().getTable("MATNRLIST");
            if (!matListTable.isEmpty()) {
                for (int j = 0; j < matListTable.getNumRows(); j++) {
                    matListTable.setRow(j);
                    MaterialEntity me = new MaterialEntity();
                    int materialNumber = Integer.parseInt(matListTable.getString("MATERIAL"));
                    me.setId(materialNumber);
                    me.setDescription(matListTable.getString("MATL_DESC"));
                    materials.put(materialNumber, me);
                    MaterialValuationEntity mve = new MaterialValuationEntity();
                    bapiMatGetDetail.getImportParameterList().setValue("MATERIAL", materialNumber);
                    bapiMatGetDetail.getImportParameterList().setValue("PLANT", "1000");
                    bapiMatGetDetail.getImportParameterList().setValue("VALUATIONAREA", "1000");
                    bapiMatGetDetail.execute(jCoDestination);
                    mve.setId(materialNumber);
                    mve.setPlant(1000);
                    JCoStructure generalData = bapiMatGetDetail.
                            getExportParameterList().getStructure("MATERIAL_GENERAL_DATA");
                    for (JCoField field: generalData) {
                        if (field.getName().equals("BASE_UOM")) {
                            mve.setBaseUnit(field.getString());
                        }
                    }
                    JCoStructure materialValuation = bapiMatGetDetail.
                            getExportParameterList().getStructure("MATERIALVALUATIONDATA");
                    for (JCoField field: materialValuation) {
                        switch (field.getName()) {
                            case "PRICE_CTRL":
                                mve.setPriceControl(field.getString());
                                break;
                            case "MOVING_PR":
                                mve.setMovingPrice(field.getBigDecimal());
                                break;
                            case "STD_PRICE":
                                mve.setFixedPrice(field.getBigDecimal());
                                break;
                            case "PRICE_UNIT":
                                mve.setPriceUnit(field.getBigDecimal());
                                break;
                            case "CURRENCY":
                                mve.setCurrency(field.getString());
                                break;
                        }
                    } // for field
                    valuations.put(materialNumber, mve);
                } // for for table
            } // material list is empty

        } finally {
            JCoContext.end(jCoDestination);
        }
    }

    public void searchAccounts() throws JCoException {

        if (!accounts.isEmpty()) accounts.clear();

        JCoFunction bapiAccountList = jCoDestination.getRepository().getFunction("BAPI_GL_ACC_GETLIST");
        bapiAccountList.getImportParameterList().setValue("COMPANYCODE", "1000");
        bapiAccountList.execute(jCoDestination);
        JCoTable accountList = bapiAccountList.getTableParameterList().getTable("ACCOUNT_LIST");
        for (int i = 0; i < accountList.getNumRows(); i++) {
            accountList.setRow(i);
            AccountEntity ae = new AccountEntity();
            ae.setAccountNumber(accountList.getString("GL_ACCOUNT"));
            ae.setDescription(accountList.getString("LONG_TEXT"));
            accounts.add(ae);
        }

    } // search accounts

    public void searchCostEstimateHeaders() throws JCoException {

        if (!estimateHeaders.isEmpty()) estimateHeaders.clear();

    }

    public Map<Integer, MaterialEntity> getMaterials() {
        return materials;
    }

    public Map<Integer, MaterialValuationEntity> getValuations() {
        return valuations;
    }

    public List<AccountEntity> getAccounts() {
        return accounts;
    }

    public List<CostEstimateHeaders> getEstimateHeaders() {
        return estimateHeaders;
    }

}
