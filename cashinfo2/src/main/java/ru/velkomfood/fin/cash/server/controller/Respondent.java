package ru.velkomfood.fin.cash.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.velkomfood.fin.cash.server.model.master.Material;
import ru.velkomfood.fin.cash.server.model.master.Partner;
import ru.velkomfood.fin.cash.server.model.transaction.*;
import ru.velkomfood.fin.cash.server.persistence.DBEngine;
import ru.velkomfood.fin.cash.server.persistence.DataFather;

import java.sql.Date;
import java.util.List;

/**
 * Created by dpetrov on 22.06.17.
 */
@RestController
public class Respondent {

    @Autowired
    private DBEngine dbEngine;
    @Autowired
    private DataFather dataFather;

    // Requests for materials

    @RequestMapping("/materials")
    public List<Material> readMaterials() {
        return dbEngine.readMaterialDictionary();
    }

    @RequestMapping("/material")
    public Material readMaterial(@RequestParam(value = "matId", defaultValue = "") String matId) {

        long id = Long.parseLong(matId);

        return dbEngine.readMaterialByKey(id);
    }


    @RequestMapping("/matbetween")
    public List<Material> readMaterialsBetween(
            @RequestParam(value = "low", defaultValue = "") String low,
            @RequestParam(value = "high", defaultValue = "") String high) {

        long vLow = Long.parseLong(low);
        long vHigh = Long.parseLong(high);

        return dbEngine.readMaterialsBetween(vLow, vHigh);
    }

    @RequestMapping(value = "/likematerial")
    public List<Material> readMaterialLikeIt(@RequestParam(value = "mat", defaultValue = "") String mat) {
        return dbEngine.readMaterialByDescLike(mat);
    }

    // Requests for partners

    @RequestMapping("/partners")
    public List<Partner> readPartners() {
        return dbEngine.readPartnerDictionary();
    }

    @RequestMapping("/partner")
    public Partner readPartner(@RequestParam(value = "parId", defaultValue = "") String parId) {
        return dbEngine.readPartnerByKey(parId);
    }

    @RequestMapping("/parbetween")
    public List<Partner> readPartnersBetween(
            @RequestParam(value = "low", defaultValue = "") String low,
            @RequestParam(value = "high", defaultValue = "") String high
    ) {
        return dbEngine.readPartnersBetween(low, high);
    }

    @RequestMapping("/likepartner")
    public List<Partner> readPartnerLikeIt(@RequestParam(value = "name", defaultValue = "") String name) {
        return dbEngine.readPartnerByNameLikeIt(name);
    }

    // Requests for cash documents

    @RequestMapping("/dockey")
    public CashDocument readCashDocumentByNumber(@RequestParam(value = "idValue", defaultValue = "") String idValue) {

        long id = Long.parseLong(idValue);

        return dbEngine.readCashDocumentByKey(id);
    }

    @RequestMapping("/cashdoc")
    public List<CashDocument> readCashDocumentsByDate(
            @RequestParam(value = "atValue", defaultValue = "") String atValue) {

        java.sql.Date atDate = Date.valueOf(atValue);

        return dbEngine.readCashDocumentsByDate(atDate);
    }

    @RequestMapping("/datebetween")
    public List<CashDocument> readCashDocumentsByDateRange(
            @RequestParam(value = "fromValue", defaultValue = "") String fromValue,
            @RequestParam(value = "toValue", defaultValue = "") String toValue
    ) {

//        try {
//            dataFather.takeNewDocuments();
//        } catch (SQLException | JCoException ex) {
//            ex.printStackTrace();
//        }

        java.sql.Date fromDate = Date.valueOf(fromValue);
        java.sql.Date toDate = Date.valueOf(toValue);


        return dbEngine.readCashDocumentsByDateBetween(fromDate, toDate);
    }

    // Outgoing deliveries
    @RequestMapping("/delivery")
    public Delivery readDeliveryInfo(
            @RequestParam(value = "delId", defaultValue = "") String delId) {

        long idValue = Long.parseLong(delId);

        Delivery delivery = new Delivery();

        delivery.setHead(dbEngine.readDeliveryHeadByKey(idValue));
        delivery.setItems(dbEngine.readDeliveryItemsByKey(idValue));

        return delivery;
    }

    @RequestMapping("/headkey")
    public DeliveryHead readDeliveryHeadByKey(
            @RequestParam(value = "key", defaultValue = "") String key
    ) {

        long delivery = Long.parseLong(key);

        return dbEngine.readDeliveryHeadByKey(delivery);
    }

    @RequestMapping("/itemskey")
    public List<DeliveryItem> readDeliveryItemsByKey(
        @RequestParam(value = "key", defaultValue = "") String key
    ) {

        long delivery = Long.parseLong(key);

        return dbEngine.readDeliveryItemsByKey(delivery);
    }

    @RequestMapping("/distrkey")
    public List<DistributedItem> readDistributedItemsByKey(
            @RequestParam(value = "key", defaultValue = "") String key
    ) {
        long delivery = Long.parseLong(key);
        return dbEngine.readDistributedItemsByKey(delivery);
    }



}
