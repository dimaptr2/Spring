package ru.velkomfood.services.mrp2.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.velkomfood.services.mrp2.model.*;

import java.util.List;

@Component
public class DbWriter {

    @Autowired
    private IMaterialRepository iMaterialRepository;
    @Autowired
    private IStockRepository iStockRepository;
    @Autowired
    private IRequirementRepository iRequirementRepository;

    // Database updating

    public void saveMaterial(Material material) {
        iMaterialRepository.save(material);
    }

    public void saveStock(Stock stock) {
        iStockRepository.save(stock);
    }

    public void saveRequirement(Requirement requirement) {
        iRequirementRepository.save(requirement);
    }

    // Database reading

    public List<Material> readAllMaterials() {
        return iMaterialRepository.findAll();
    }

    public boolean existsMaterial(long id) {

        if (iMaterialRepository.findOne(id) == null) {
            return false;
        } else {
            return true;
        }

    }

    public Material readMaterialByKey(long key) {
        return iMaterialRepository.findMaterialById(key);
    }


}
