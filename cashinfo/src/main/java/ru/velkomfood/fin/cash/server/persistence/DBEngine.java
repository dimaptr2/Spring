package ru.velkomfood.fin.cash.server.persistence;

import com.sap.conn.jco.JCoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.velkomfood.fin.cash.server.model.master.*;
import ru.velkomfood.fin.cash.server.model.transaction.CashDocument;
import ru.velkomfood.fin.cash.server.model.transaction.ICashDocumentRepository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Created by dpetrov on 22.06.17.
 */
@Component
public class DBEngine {

    @Autowired
    private SAPEngine sapEngine;

    @Autowired
    private DataSource dataSource;

    // master data
    @Autowired
    private ICompanyRepository iCompanyRepository;
    @Autowired
    private IUserRepository iUserRepository;
    @Autowired
    private IPartnerTypeRepository iPartnerTypeRepository;
    @Autowired
    private IDeliveryTypeRepository iDeliveryTypeRepository;
    @Autowired
    private IMaterialRepository iMaterialRepository;
    @Autowired
    private IPartnerRepository iPartnerRepository;

    // transaction data
    @Autowired
    private ICashDocumentRepository iCashDocumentRepository;

    @PostConstruct
    public void startInstancePreparation() throws JCoException, SQLException {

        if (checkTableIsEmpty("materials")) {
            initUsersTable();
            initCompanyIdsTable();
            initPartnerTypesTable();
            initDeliveryTypes();
        } else {
            System.out.println("Database was initialized");
        }

    }

    private boolean checkTableIsEmpty(String tableName) throws SQLException {

        boolean nothing;
        long counter = 0;

        StringBuilder sb = new StringBuilder(0)
                .append("SELECT COUNT( id ) AS number_rows FROM ")
                .append(tableName);

        Statement stmt = dataSource.getConnection().createStatement();

        try {

            ResultSet rs = stmt.executeQuery(sb.toString());
            while (rs.next()) {
                counter = rs.getLong("number_rows");
            }
            if (counter == 0) {
                nothing = true;
            } else {
                nothing = false;
            }
            rs.close();

        } finally {

            stmt.close();

        }

        return nothing;
    }

    private void initUsersTable() throws SQLException {

        User user = new User("admin", "Administrator", "Administrator", "dubina");
        iUserRepository.save(user);

    }

    private void initCompanyIdsTable() throws JCoException, SQLException {

        Company comId = iCompanyRepository.findCompanyById("1000");

        if (comId == null) {
            Company company = sapEngine.readCompanyData();
            iCompanyRepository.save(company);
        }

    }

    private void initPartnerTypesTable() throws SQLException {

        List<PartnerType> pTypes = iPartnerTypeRepository.findAll();

        if (pTypes == null || pTypes.isEmpty()) {

            PartnerType pt1 = new PartnerType(1, "Покупатель");
            iPartnerTypeRepository.save(pt1);

            PartnerType pt2 = new PartnerType(2, "Поставщик");
            iPartnerTypeRepository.save(pt2);

            PartnerType pt3 = new PartnerType(3, "Сотрудник");
            iPartnerTypeRepository.save(pt3);

            PartnerType pt4 = new PartnerType(4, "Акционер");
            iPartnerTypeRepository.save(pt4);

        }

    }

    private void initDeliveryTypes() throws SQLException {

        List<DeliveryType> dt = iDeliveryTypeRepository.findAll();

        if (dt == null || dt.isEmpty()) {

            DeliveryType dt1 = new DeliveryType(1, "Входящая поставка");
            iDeliveryTypeRepository.save(dt1);

            DeliveryType dt2 = new DeliveryType(2, "Исходящая поставка");
            iDeliveryTypeRepository.save(dt2);

        }

    }

    // Master data processing

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

    // Transaction data processing

    // Cash Documents
    public void saveCashDocument(CashDocument doc) throws SQLException {
        iCashDocumentRepository.save(doc);
    }

    public List<CashDocument> readAllCashDocuments() throws SQLException {
        return iCashDocumentRepository.findAll();
    }

    public List<CashDocument> readCashDocumentsByDate(java.sql.Date postingDate) throws SQLException {
        return iCashDocumentRepository.findCashDocumentByPostingDate(postingDate);
    }

    public List<CashDocument> readCashDocumentsByIdBetween(long low, long high) throws SQLException {
        return iCashDocumentRepository.findCashDocumentByIdBetween(low, high);
    }
    // Outgoing deliveries

    // Sales orders
}
