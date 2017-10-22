package ru.velkomfood.services.mrp2.core;

import com.sap.conn.jco.*;
import com.sap.conn.jco.ext.DestinationDataProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.velkomfood.services.mrp2.model.Material;
import ru.velkomfood.services.mrp2.model.Requirement;
import ru.velkomfood.services.mrp2.model.Stock;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SapReader {

    final String DEST = "PRD";
    final String SUFFIX = ".jcoDestination";
    private JCoDestination destination;

    @Autowired
    private DbWriter dbWriter;
    @Autowired
    private AlphaTransformer alphaTransformer;

    private Map<String, JCoFunction> bapiMap;

    @PostConstruct
    public void initDestination() throws JCoException {

        Properties connectProperties = new Properties();
        connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST, "XXX");
        connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR, "XX");
        connectProperties.setProperty(DestinationDataProvider.JCO_R3NAME, "XXX");
        connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT, "XXX");
        connectProperties.setProperty(DestinationDataProvider.JCO_USER, "XXXXX");
        connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD, "XXXXXX");
        connectProperties.setProperty(DestinationDataProvider.JCO_LANG, "XX");

        createDestinationDataFile(DEST, connectProperties);

        destination = JCoDestinationManager.getDestination(DEST);

        if (destination != null && destination.isValid()) {
            buildBapiMap(destination);
        }

    }

    private void createDestinationDataFile(String destName, Properties props) {

        File cfg = new File(destName + SUFFIX);
        if (!cfg.exists()) {
            try {
                OutputStream fos = new FileOutputStream(cfg, false);
                props.store(fos, "Productive environment");
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    // Get all needed BAPI methods and RFC functions from SAP repository
    // and build the map with the RFC functions.
    private void buildBapiMap(JCoDestination dest) throws JCoException {

        bapiMap = new ConcurrentHashMap<>();

        JCoRepository repository = dest.getRepository();

        bapiMap.put("MAT_LIST", repository.getFunction("BAPI_MATERIAL_GETLIST"));
        bapiMap.put("MRP2_LIST", repository.getFunction("BAPI_MATERIAL_STOCK_REQ_LIST"));
        bapiMap.put("READ_TABLE", repository.getFunction("RFC_READ_TABLE"));
        bapiMap.put("Z_MAT_DETAIL", repository.getFunction("Z_RFC_GET_MATERIAL_INFO"));
//        bapiMap.put("Z_MARD", repository.getFunction("Z_RFC_GET_MARD"));
        bapiMap.put("Z_MARD2", repository.getFunction("Z_RFC_GET_MARD2"));

    }

    public JCoDestination getDestination() {
        return destination;
    }

    public void readMaterials() throws JCoException {

        try {

            JCoContext.begin(destination);

            JCoFunction bapiMats = bapiMap.get("MAT_LIST");
            JCoTable criteria = bapiMats.getTableParameterList().getTable("MATNRSELECTION");
            criteria.appendRow();
            criteria.setValue("SIGN", "I");
            criteria.setValue("OPTION", "BT");
            criteria.setValue("MATNR_LOW", "000000000010000000");
            criteria.setValue("MATNR_HIGH", "000000000019999999");
            bapiMats.execute(destination);
            JCoTable materials = bapiMats.getTableParameterList().getTable("MATNRLIST");
            if (materials.getNumRows() > 0) {
                JCoFunction matDetail = bapiMap.get("Z_MAT_DETAIL");
                do {
                    String key = materials.getString("MATERIAL");
                    Material material = new Material();
                    material.setId(Long.parseLong(key));
                    material.setDescription(materials.getString("MATL_DESC"));
                    matDetail.getImportParameterList().setValue("I_PLANT", "1000");
                    matDetail.getImportParameterList().setValue("I_MATERIAL", key);
                    matDetail.execute(destination);
                    JCoStructure mara = matDetail.getExportParameterList().getStructure("E_MARA");
                    for (JCoField f1: mara) {
                        if (f1.getName().equals("MEINS")) {
                            material.setUom(f1.getString());
                        }
                    }
                    Map<String, BigDecimal> prices = new HashMap<>();
                    JCoStructure mbew = matDetail.getExportParameterList().getStructure("E_MBEW");
                    for (JCoField f2: mbew) {
                        switch (f2.getName()) {
                            case "PEINH":
                                BigDecimal priceUnit = f2.getBigDecimal();
                                if (priceUnit.equals(alphaTransformer.getZERO())) {
                                    material.setUnit(alphaTransformer.getUNIT());
                                } else {
                                    material.setUnit(priceUnit);
                                }
                                break;
                            case "STPRS":
                                prices.put("S", f2.getBigDecimal());
                                break;
                            case "VERPR":
                                prices.put("V", f2.getBigDecimal());
                                break;
                        }
                    }
                    BigDecimal cost = prices.get("S");
                    if (cost.equals(alphaTransformer.getZERO())) {
                        cost = prices.get("V");
                    }
                    cost = cost.divide(material.getUnit());
                    material.setCost(cost);
                    dbWriter.saveMaterial(material);
                } while (materials.nextRow());
            }

        } finally {

            JCoContext.end(destination);

        }

    }  // get all materials


    public void readStocksByMaterialIdAndPeriod(String year, String month, long materialId) throws JCoException {

        String matId = alphaTransformer.transformLongValueToAlphaRow(materialId, 18);

        JCoFunction zMard2 = bapiMap.get("Z_MARD2");

        zMard2.getImportParameterList().setValue("I_MATNR", matId);
        zMard2.getImportParameterList().setValue("I_WERKS", "1000");
        zMard2.getImportParameterList().setValue("I_YEAR", year);
        zMard2.getImportParameterList().setValue("I_MONTH", month);

        zMard2.execute(destination);

        JCoTable mard = zMard2.getTableParameterList().getTable("T_MARD");

        if (mard.getNumRows() > 0) {

            do {

                Stock stock = new Stock(
                        mard.getLong("MATNR"),
                        mard.getInt("LGORT"),
                        mard.getInt("LFGJA"),
                        mard.getInt("LFMON"),
                        mard.getBigDecimal("LABST")
                );

                if (stock.getValue() == null) {
                    stock.setValue(alphaTransformer.getZERO());
                }

                dbWriter.saveStock(stock);

            } while (mard.nextRow());

        }

    }

    public void readRequirementsByMaterialId(long id) throws JCoException {

        String purGroup = "XXX";

        JCoFunction bapiMrp2 = bapiMap.get("MRP2_LIST");

        bapiMrp2.getImportParameterList().setValue("MATERIAL",
                alphaTransformer.transformLongValueToAlphaRow(id, 18));
        bapiMrp2.getImportParameterList().setValue("PLANT", "1000");
        bapiMrp2.getImportParameterList().setValue("PERIOD_INDICATOR", "M"); // monthly
        bapiMrp2.getImportParameterList().setValue("GET_IND_LINES", " ");
        bapiMrp2.getImportParameterList().setValue("GET_TOTAL_LINES", "X");

        bapiMrp2.execute(destination);

        JCoStructure mrpHeader = bapiMrp2.getExportParameterList().getStructure("MRP_LIST");
        for (JCoField f: mrpHeader) {
            if (f.getName().equals("PUR_GROUP")) {
                purGroup = f.getString();
                break;
            }
        }

        // For the inversions of the negative requirements
        BigDecimal minus = alphaTransformer.getMINUS_UNIT();

        JCoTable totals = bapiMrp2.getTableParameterList().getTable("MRP_TOTAL_LINES");

        if (totals.getNumRows() > 0) {

            do {

                if (totals.getString("PER_SEGMT")
                        .equals(alphaTransformer.getPATTERN1())) {
                    continue;
                }

                Requirement requirement = new Requirement();
                requirement.setMaterialId(id);
                requirement.setPurchaseGroupId(purGroup);

                java.sql.Date dt = new java.sql.Date(totals.getDate("AVAIL_DATE").getTime());
                Map<String, Integer> mapTimes = buildTimeMap(dt);
                requirement.setYear(mapTimes.get("year"));
                requirement.setMonth(mapTimes.get("month"));
                // Set the value of requirement
                requirement.setValue(minus.multiply(totals.getBigDecimal("REQMTS")));

                if (requirement.getValue() == null) {
                    requirement.setValue(alphaTransformer.getZERO());
                }

                dbWriter.saveRequirement(requirement);

            } while (totals.nextRow());

        }

    }

    private Map<String, Integer> buildTimeMap(java.sql.Date date) {

        Map<String, Integer> times = new HashMap<>();

        String[] txtDate = date.toString().split("-");

        if (txtDate.length > 0) {
            times.put("year", Integer.valueOf(txtDate[0]));
            times.put("month", Integer.valueOf(txtDate[1]));
        }

        return times;
    }


}
