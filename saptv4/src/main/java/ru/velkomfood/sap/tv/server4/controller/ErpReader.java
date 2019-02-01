package ru.velkomfood.sap.tv.server4.controller;

import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.velkomfood.sap.tv.server4.model.MaterialTotals;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ErpReader {

    private DbProcessor dbProcessor;
    private Properties sapProperties;
    private Logger logger;

    @Autowired
    public ErpReader(DbProcessor dbProcessor) {
        this.dbProcessor = dbProcessor;
    }

    @Autowired
    @Qualifier("sapProperties")
    public void setSapProperties(Properties sapProperties) {
        this.sapProperties = sapProperties;
    }

    @Autowired
    @Qualifier("mainLogger")
    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public void takeOutgoingDeliveriesInfo(LocalDate date) throws JCoException {

        String sapDate = convertToSapDateFormat(date.toString());
        final JCoDestination destination = createSapDestination(sapProperties.getProperty("destination"));
        if (destination.isValid()) {
            final JCoFunction rfcGetShipments = destination.getRepository().getFunction("Z_RFC_GET_IN_PROCESS_SHIP");
            try {
                JCoContext.begin(destination);
                invokeRfcFunction(destination, rfcGetShipments, sapDate, sapProperties.getProperty("hierarchy1"));
                invokeRfcFunction(destination, rfcGetShipments, sapDate, sapProperties.getProperty("hierarchy2"));
            } finally {
                JCoContext.end(destination);
            }
        } else {
            logger.error(String.format("SAP destination %s is not valid", sapProperties.getProperty("destination")));
        }
    }

    // private section

    private String convertToSapDateFormat(String localDate) {

        String[] txtDate = localDate.split("-");
        StringBuilder dateBuilder = new StringBuilder(0);

        for (String element : txtDate) {
            dateBuilder.append(element);
        }

        return dateBuilder.toString();
    }

    private JCoDestination createSapDestination(String destinationName) throws JCoException {
        return JCoDestinationManager.getDestination(destinationName);
    }

    private void invokeRfcFunction(JCoDestination destination, JCoFunction function,
                                   String queryDate, String hierarchy) throws JCoException {

        function.getImportParameterList().setValue("I_MBDAT", queryDate);
        function.getImportParameterList().setValue("FROM_MAT", sapProperties.getProperty("material.low"));
        function.getImportParameterList().setValue("TO_MAT", sapProperties.getProperty("material.high"));
        function.getImportParameterList().setValue("I_PRODH", hierarchy);

        function.execute(destination);

        Map<String, String> matInfo = createMaterialsMap(function.getTableParameterList().getTable("T_MATERIALS"));

        JCoTable totals = function.getTableParameterList().getTable("T_SHIP_TOTAL");
        if (totals.getNumRows() > 0) {
            do {
                String materialTextId = totals.getString("MATNR");
                long id = Long.parseLong(materialTextId);
                MaterialTotals materialTotals = new MaterialTotals();
                materialTotals.setId(id);
                materialTotals.setDate(java.sql.Date.valueOf(totals.getString("MBDAT")));
                materialTotals.setTime(java.sql.Time.valueOf(totals.getString("ZDELIVTIME")));
                materialTotals.setDescription(matInfo.get(materialTextId));
                materialTotals.setUom(totals.getString("MEINS"));
                BigDecimal quantity = totals.getBigDecimal("LGMNG");
                if (quantity == null) {
                    quantity = BigDecimal.ZERO;
                }
                materialTotals.setQuantity(quantity);
                BigDecimal packed = totals.getBigDecimal("MENGE_PROT");
                if (packed == null) {
                    packed = BigDecimal.ZERO;
                }
                materialTotals.setPacked(packed);
                BigDecimal transferred = totals.getBigDecimal("MENGE");
                if (transferred == null) {
                    transferred = BigDecimal.ZERO;
                }
                materialTotals.setTransferred(transferred);
                materialTotals.setInProcess(calculateInProcessQuantity(quantity, packed, transferred));
                dbProcessor.saveMaterialTotalsEntity(materialTotals);
            } while (totals.nextRow());
        }

    }

    private Map<String, String> createMaterialsMap(JCoTable matTable) {

        Map<String, String> materials = new ConcurrentHashMap<>();

        if (matTable.getNumRows() > 0) {
            do {
                materials.put(matTable.getString("MATNR"), matTable.getString("MAKTX"));
            } while (matTable.nextRow());
        }

        return materials;
    }

    private BigDecimal calculateInProcessQuantity(BigDecimal quantity, BigDecimal valuePacked, BigDecimal valueTransfer) {
        double calculationValue = quantity.doubleValue() - (valuePacked.doubleValue() + valueTransfer.doubleValue());
        return new BigDecimal(calculationValue).setScale(3, RoundingMode.HALF_UP);
    }

}
