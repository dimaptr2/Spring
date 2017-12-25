package ru.velkomfood.mrp3.reports.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.velkomfood.mrp3.reports.model.md.*;
import ru.velkomfood.mrp3.reports.model.td.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

@Component
public class DbChanger {

    final BigDecimal ZERO = new BigDecimal(0.000);

    @Autowired
    private IWarehouse iWarehouse;
    @Autowired
    private IPurchaseGroup iPurchaseGroup;
    @Autowired
    private IMaterial iMaterial;
    @Autowired
    private IStock iStock;
    @Autowired
    private IRequirement iRequirement;
    @Autowired
    private IResultData iResultData;
    @Autowired
    private ICurrentStock iCurrentStock;

    public void refreshDatabase() {
        iStock.deleteAll();
        iRequirement.deleteAll();
    }
    // Saved section


    public void saveWarehouse(Warehouse warehouse) {
        iWarehouse.save(warehouse);
    }

    public void savePurchaseGroup(PurchaseGroup purchaseGroup) {
        iPurchaseGroup.save(purchaseGroup);
    }

    public void saveMaterial(Material material) {
        iMaterial.save(material);
    }

    public void saveStock(Stock stock) {
        iStock.save(stock);
    }

    public void saveRequirement(Requirement requirement) {
        iRequirement.save(requirement);
    }

    // Query section

    public List<Material> readAllMaterials() {
        return iMaterial.findAll();
    }

    public List<Material> readMaterialsBetweenIds(long fromId, long toId) {
        return iMaterial.findMaterialByIdBetween(fromId, toId);
    }

    public List<Stock> readStocksByMaterialIdAndYear(long id, int year) {
        return iStock.findStockByMaterialIdAndYear(id, year);
    }

    public List<Requirement> readAllRequirementsByMaterialAndYear(long id, int year) {
        return iRequirement.findRequirementByMaterialAndYear(id, year);
    }

    public List<ResultData> readResultDataByMaterialAndPeriod(long id, int month, int year) {
        return iResultData.findResultDataByMaterialIdAndMonthAndYear(id, month, year);
    }

    public List<Stock> readNonNullableFreeStock(long id, int year, BigDecimal value) {
        return iStock.findStockByMaterialIdAndYearAndFreeGreaterThan(id, year, value);
    }

    public List<Stock> readNonNullableMovingStock(long id, int year, BigDecimal value) {
        return iStock.findStockByMaterialIdAndYearAndMovingGreaterThan(id, year, value);
    }

    public List<Stock> readNonNullableQualityStock(long id, int year, BigDecimal value) {
        return iStock.findStockByMaterialIdAndYearAndQualityGreaterThan(id, year, value);
    }

    public List<Stock> readNonNullableBlockStock(long id, int year, BigDecimal value) {
        return iStock.findStockByMaterialIdAndYearAndBlockGreaterThan(id, year, value);
    }

    // Build the resulting table

    public void buildResultingData() {

        List<Requirement> reqs = iRequirement.findAll();

        if (!reqs.isEmpty()) {
            reqs.forEach(row -> {
                // January
                ResultData rd1 = new ResultData();
                rd1.setMaterialId(row.getMaterial());
                rd1.setPurchaseGroup(row.getPurchaseGroup());
                rd1.setMonth(1);
                rd1.setYear(row.getYear());
                rd1.setUom(row.getUom());
                rd1.setQuantity(row.getReq01());
                if (rd1.getQuantity() == null) rd1.setQuantity(ZERO);
                iResultData.save(rd1);
                // February
                ResultData rd2 = new ResultData();
                rd2.setMaterialId(row.getMaterial());
                rd2.setPurchaseGroup(row.getPurchaseGroup());
                rd2.setMonth(2);
                rd2.setYear(row.getYear());
                rd2.setUom(row.getUom());
                rd2.setQuantity(row.getReq02());
                if (rd2.getQuantity() == null) rd2.setQuantity(ZERO);
                iResultData.save(rd2);
                // March
                ResultData rd3 = new ResultData();
                rd3.setMaterialId(row.getMaterial());
                rd3.setPurchaseGroup(row.getPurchaseGroup());
                rd3.setMonth(3);
                rd3.setYear(row.getYear());
                rd3.setUom(row.getUom());
                rd3.setQuantity(row.getReq03());
                if (rd3.getQuantity() == null) rd3.setQuantity(ZERO);
                iResultData.save(rd3);
                // April
                ResultData rd4 = new ResultData();
                rd4.setMaterialId(row.getMaterial());
                rd4.setPurchaseGroup(row.getPurchaseGroup());
                rd4.setMonth(4);
                rd4.setYear(row.getYear());
                rd4.setUom(row.getUom());
                rd4.setQuantity(row.getReq04());
                if (rd4.getQuantity() == null) rd4.setQuantity(ZERO);
                iResultData.save(rd4);
                // May
                ResultData rd5 = new ResultData();
                rd5.setMaterialId(row.getMaterial());
                rd5.setPurchaseGroup(row.getPurchaseGroup());
                rd5.setMonth(5);
                rd5.setYear(row.getYear());
                rd5.setUom(row.getUom());
                rd5.setQuantity(row.getReq05());
                if (rd5.getQuantity() == null) rd5.setQuantity(ZERO);
                iResultData.save(rd5);
                // June
                ResultData rd6 = new ResultData();
                rd6.setMaterialId(row.getMaterial());
                rd6.setPurchaseGroup(row.getPurchaseGroup());
                rd6.setMonth(6);
                rd6.setYear(row.getYear());
                rd6.setUom(row.getUom());
                rd6.setQuantity(row.getReq06());
                if (rd6.getQuantity() == null) rd6.setQuantity(ZERO);
                iResultData.save(rd6);
                // July
                ResultData rd7 = new ResultData();
                rd7.setMaterialId(row.getMaterial());
                rd7.setPurchaseGroup(row.getPurchaseGroup());
                rd7.setMonth(7);
                rd7.setYear(row.getYear());
                rd7.setUom(row.getUom());
                rd7.setQuantity(row.getReq07());
                if (rd7.getQuantity() == null) rd7.setQuantity(ZERO);
                iResultData.save(rd7);
                // August
                ResultData rd8 = new ResultData();
                rd8.setMaterialId(row.getMaterial());
                rd8.setPurchaseGroup(row.getPurchaseGroup());
                rd8.setMonth(8);
                rd8.setYear(row.getYear());
                rd8.setUom(row.getUom());
                rd8.setQuantity(row.getReq08());
                if (rd8.getQuantity() == null) rd8.setQuantity(ZERO);
                iResultData.save(rd8);
                // September
                ResultData rd9 = new ResultData();
                rd9.setMaterialId(row.getMaterial());
                rd9.setPurchaseGroup(row.getPurchaseGroup());
                rd9.setMonth(9);
                rd9.setYear(row.getYear());
                rd9.setUom(row.getUom());
                rd9.setQuantity(row.getReq09());
                if (rd9.getQuantity() == null) rd9.setQuantity(ZERO);
                iResultData.save(rd9);
                // October
                ResultData rd10 = new ResultData();
                rd10.setMaterialId(row.getMaterial());
                rd10.setPurchaseGroup(row.getPurchaseGroup());
                rd10.setMonth(10);
                rd10.setYear(row.getYear());
                rd10.setUom(row.getUom());
                rd10.setQuantity(row.getReq10());
                if (rd10.getQuantity() == null) rd10.setQuantity(ZERO);
                iResultData.save(rd10);
                // November
                ResultData rd11 = new ResultData();
                rd11.setMaterialId(row.getMaterial());
                rd11.setPurchaseGroup(row.getPurchaseGroup());
                rd11.setMonth(11);
                rd11.setYear(row.getYear());
                rd11.setUom(row.getUom());
                rd11.setQuantity(row.getReq11());
                if (rd11.getQuantity() == null) rd11.setQuantity(ZERO);
                iResultData.save(rd11);
                // December
                ResultData rd12 = new ResultData();
                rd12.setMaterialId(row.getMaterial());
                rd12.setPurchaseGroup(row.getPurchaseGroup());
                rd12.setMonth(12);
                rd12.setYear(row.getYear());
                rd12.setUom(row.getUom());
                rd12.setQuantity(row.getReq12());
                if (rd12.getQuantity() == null) rd12.setQuantity(ZERO);
                iResultData.save(rd12);
            });
        }

    }

    // Search the current stock
    public void findAndSaveCurrentStock() {

        int year = LocalDate.now().getYear();

        List<Material> mats = iMaterial.findAll();
        if (!mats.isEmpty()) {
            mats.forEach(m -> {
                long id = m.getId();
                List<Stock> stsFree = readNonNullableFreeStock(id, year, ZERO);
                if (!stsFree.isEmpty()) {
                    Stock stock = stsFree.get(0);
                    CurrentStock csFree = new CurrentStock(
                            stock.getPlant(),
                            stock.getWarehouse(),
                            stock.getMaterialId(),
                            "free",
                            stock.getUom(),
                            stock.getFree()
                    );
                    iCurrentStock.save(csFree);
                }
                stsFree.clear();
                List<Stock> stsMoving = readNonNullableMovingStock(id, year, ZERO);
                if (!stsMoving.isEmpty()) {
                    Stock stock = stsMoving.get(0);
                    CurrentStock csMoving = new CurrentStock(
                            stock.getPlant(),
                            stock.getWarehouse(),
                            stock.getMaterialId(),
                            "moving",
                            stock.getUom(),
                            stock.getMoving()
                    );
                    iCurrentStock.save(csMoving);
                }
                stsMoving.clear();
                List<Stock> stsQuality = readNonNullableQualityStock(id, year, ZERO);
                if (!stsQuality.isEmpty()) {
                    Stock stock = stsQuality.get(0);
                    CurrentStock csQuality = new CurrentStock(
                            stock.getPlant(),
                            stock.getWarehouse(),
                            stock.getMaterialId(),
                            "quality",
                            stock.getUom(),
                            stock.getQuality()
                    );
                    iCurrentStock.save(csQuality);
                }
                stsQuality.clear();
                List<Stock> stsBlock = readNonNullableBlockStock(id, year, ZERO);
                if (!stsBlock.isEmpty()) {
                    Stock stock = stsBlock.get(0);
                    CurrentStock csBlock = new CurrentStock(
                            stock.getPlant(),
                            stock.getWarehouse(),
                            stock.getMaterialId(),
                            "block",
                            stock.getUom(),
                            stock.getBlock()
                    );
                    iCurrentStock.save(csBlock);
                }
                stsBlock.clear();
            });
        }

    }

    // Here is a main query for the web requests

}
