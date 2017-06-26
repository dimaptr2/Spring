package ru.velkomfood.fin.cash.server.controller;

import com.fasterxml.jackson.core.json.UTF8DataInputJsonParser;
import org.apache.tomcat.util.buf.Utf8Decoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.velkomfood.fin.cash.server.model.master.Material;
import ru.velkomfood.fin.cash.server.model.master.Partner;
import ru.velkomfood.fin.cash.server.persistence.DBEngine;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by dpetrov on 22.06.17.
 */
@RestController
public class Respondent {

    @Autowired
    private DBEngine dbEngine;

    @RequestMapping("/materials")
    public List<Material> readMaterials() {

        List<Material> materials = null;

        try {
            materials = dbEngine.readMaterialDictionary();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return materials;
    }

    @RequestMapping("/material")
    public Material readMaterial(@RequestParam(value = "matId", defaultValue = "") String matId) {

        long id = Long.parseLong(matId);

        Material material = null;

        try {
            material = dbEngine.readMaterialByKey(id);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }


        return material;
    }

    @RequestMapping(value = "/likematerial")
    public List<Material> readMaterialLikeIt(@RequestParam(value = "mat", defaultValue = "") String mat) {

        List<Material> materials = null;
        mat += "%";

        try {
//            mat = URLDecoder.decode(mat, "UTF-8");
            materials = dbEngine.readMaterialByDescLike(mat);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return materials;
    }

    @RequestMapping("/matbetween")
    public List<Material> readMaterialsBetween(
            @RequestParam(value = "low", defaultValue = "") String low,
            @RequestParam(value = "high", defaultValue = "") String high) {


        long vLow = Long.parseLong(low);
        long vHigh = Long.parseLong(high);

        List<Material> materials = null;

        try {
            materials = dbEngine.readMaterialsBetween(vLow, vHigh);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return materials;
    }

    @RequestMapping("/partners")
    public List<Partner> readPartners() {

        List<Partner> partners = null;

        try {
            partners = dbEngine.readPartnerDictionary();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return partners;
    }

    @RequestMapping("/partner")
    public Partner readPartner(@RequestParam(value = "parId", defaultValue = "") String parId) {

        Partner partner = null;

        try {
            partner = dbEngine.readPartnerByKey(parId);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return partner;
    }



}
