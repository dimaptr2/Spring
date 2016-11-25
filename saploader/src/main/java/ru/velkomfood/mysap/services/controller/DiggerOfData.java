package ru.velkomfood.mysap.services.controller;

import org.springframework.beans.factory.annotation.Autowired;
import ru.velkomfood.mysap.services.model.entities.MaterialEntity;
import ru.velkomfood.mysap.services.model.entities.MaterialSumEntity;
import ru.velkomfood.mysap.services.model.entities.MrpTotalEntity;
import ru.velkomfood.mysap.services.model.entities.PurchaseGroupEntity;
import ru.velkomfood.mysap.services.model.templates.MaterialJdbcTemplate;
import ru.velkomfood.mysap.services.model.templates.MaterialSumJdbcTemplate;
import ru.velkomfood.mysap.services.model.templates.PurchaseGroupJdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by dpetrov on 11.07.16.
 */

public class DiggerOfData {

    private List<MaterialEntity> matnrList;
    private Map<String, Date> when;
    private List<PurchaseGroupEntity> purIdList;
    private List<MaterialSumEntity> matnrSums;

    private List<MrpTotalEntity> resultList;

    private DataSource dataSource;

    @Autowired
    private MaterialJdbcTemplate materials;
    @Autowired
    private PurchaseGroupJdbcTemplate purchaseGroups;
    @Autowired
    private MaterialSumJdbcTemplate sumByMaterial;

    public void setDataSource(DataSource dataSource) {

        this.dataSource = dataSource;
        materials.setDataSource(dataSource);
        purchaseGroups.setDataSource(dataSource);
        sumByMaterial.setDataSource(dataSource);
        resultList = new ArrayList<>();

    }

    public void setWhen(Map<String, Date> when) {
        this.when = when;
    }

    public void receiveMaterialList() {
        matnrList = materials.findAllMaterials();
    }

    public void receivePurchaseGroupsList() {
        purIdList = purchaseGroups.findAllPurchaseGroups();
    }

    public void findSumByMaterials() {

        for (MaterialEntity me: matnrList) {

            if (!matnrSums.isEmpty()) matnrSums.clear();
            matnrSums = sumByMaterial.findSumByMaterial(me.getMatnr(), when);
            MrpTotalEntity mrpTotalEntity = new MrpTotalEntity();
            for (MaterialSumEntity mse: matnrSums) {
                mrpTotalEntity.setMaterial(mse.getMaterialNumber());
                mrpTotalEntity.setMrpDate(mse.getAvailableDate());
                mrpTotalEntity.setPrimaryReq(mse.getPsum());
                mrpTotalEntity.setSecondaryReq(mse.getSsum());
                mrpTotalEntity.setAvailableQuantity(mse.getAsum());
                resultList.add(mrpTotalEntity);
            }

        }

    }

    public List<MrpTotalEntity> getResultList() {
        return resultList;
    }

}
