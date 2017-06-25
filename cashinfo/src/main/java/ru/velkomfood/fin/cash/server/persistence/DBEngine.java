package ru.velkomfood.fin.cash.server.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.velkomfood.fin.cash.server.model.master.*;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by dpetrov on 22.06.17.
 */
@Component
public class DBEngine {

    @Autowired
    private IUserRepository iUserRepository;
    @Autowired
    private IPartnerTypeRepository iPartnerTypeRepository;
    @Autowired
    private IMaterialRepository iMaterialRepository;
    @Autowired
    private IPartnerRepository iPartnerRepository;


    public void initUsersTable() throws SQLException {

        User user = new User("admin", "Administrator", "Administrator", "dubina");
        iUserRepository.save(user);

    }

    public void initPartnerTypesTable() throws SQLException {

        List<PartnerType> pTypes = iPartnerTypeRepository.findAll();

        if (pTypes == null || (pTypes != null && pTypes.isEmpty())) {

            PartnerType pt1 = new PartnerType(1, "Customer");
            iPartnerTypeRepository.save(pt1);

            PartnerType pt2 = new PartnerType(2, "Vendor");
            iPartnerTypeRepository.save(pt2);

            PartnerType pt3 = new PartnerType(3, "Employee");
            iPartnerTypeRepository.save(pt3);

            PartnerType pt4 = new PartnerType(4, "Shareholder");
            iPartnerTypeRepository.save(pt4);

        }

    }

    public void saveMaterial(Material mat) throws SQLException {

        Material material = iMaterialRepository.findMaterialById(mat.getId());

        if (material == null) {
            iMaterialRepository.save(mat);
        }

    }

    public void savePartner(Partner par) throws SQLException {

        Partner partner = iPartnerRepository.findPartnerById(par.getId());

        if (partner == null) {
            iPartnerRepository.save(par);
        }

    }

    public List<Material> readMaterialDictionary() throws SQLException {
        return iMaterialRepository.findAll();
    }

    public Material readMaterialByKey(long key) throws SQLException {
        return iMaterialRepository.findMaterialById(key);
    }

    public List<Material> readMaterialsBetween(long low, long high) throws SQLException {
        return iMaterialRepository.findMaterialByIdBetween(low, high);
    }

    public List<Material> readMaterialByDescLike(String description) throws SQLException {
        return iMaterialRepository.findMaterialByDescriptionLike(description);
    }

    public List<Partner> readPartnerDictionary() throws SQLException {
        return iPartnerRepository.findAll();
    }

    public Partner readPartnerByKey(String key) throws SQLException {
        return iPartnerRepository.findPartnerById(key);
    }


}
