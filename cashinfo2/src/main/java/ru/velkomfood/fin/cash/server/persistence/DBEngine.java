package ru.velkomfood.fin.cash.server.persistence;

import com.sap.conn.jco.JCoException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;
import org.springframework.stereotype.Component;
import ru.velkomfood.fin.cash.server.model.master.*;
import ru.velkomfood.fin.cash.server.model.transaction.*;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by dpetrov on 22.06.17.
 */
@Component
public class DBEngine {

    @Autowired
    private SAPEngine sapEngine;

    @Autowired
    @Qualifier("myDataSource")
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
    @Autowired
    private IChannelRepository iChannelRepository;
    @Autowired
    private ISectorRepository iSectorRepository;

    // transaction data
    @Autowired
    private ICashDocumentRepository iCashDocumentRepository;
    @Autowired
    private IDeliveryHeadRepository iDeliveryHeadRepository;
    @Autowired
    private IDeliveryItemRepository iDeliveryItemRepository;
    @Autowired
    private IDistributedHeadRepository iDistributedHeadRepository;
    @Autowired
    private IDistributedItemRepository iDistributedItemRepository;

    @PostConstruct
    public void startInstancePreparation() throws JCoException, SQLException {

        if (checkTableIsEmpty("materials")) {
            initUsersTable();
            initCompanyIdsTable();
            initPartnerTypesTable();
            initDeliveryTypes();
            initSalesStructure();
        } else {
            System.out.println("Database was initialized");
        }

    }

    public boolean checkTableIsEmpty(String tableName) throws SQLException {

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

    private void initUsersTable() {

        User user = new User("admin", "Administrator", "Administrator", "dubina");
        iUserRepository.save(user);

    }

    private void initCompanyIdsTable() throws JCoException {

        Company comId = iCompanyRepository.findCompanyById("1000");

        if (comId == null) {
            Company company = sapEngine.readCompanyData();
            iCompanyRepository.save(company);
        }

    }

    private void initPartnerTypesTable() {

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

    private void initDeliveryTypes() {

        List<DeliveryType> dt = iDeliveryTypeRepository.findAll();

        if (dt == null || dt.isEmpty()) {

            DeliveryType dt1 = new DeliveryType(1, "Входящая поставка");
            iDeliveryTypeRepository.save(dt1);

            DeliveryType dt2 = new DeliveryType(2, "Исходящая поставка");
            iDeliveryTypeRepository.save(dt2);

        }

    }

    private void initSalesStructure() {

        if (iChannelRepository.findAll().isEmpty()) {

            Channel ch10 = new Channel("10", "ТиФ сети");
            iChannelRepository.save(ch10);

            Channel ch11 = new Channel("11", "Локальные сети");
            iChannelRepository.save(ch11);

            Channel ch12 = new Channel("12", "Розница");
            iChannelRepository.save(ch12);

            Channel ch13 = new Channel("13", "Дистрибьютеры");
            iChannelRepository.save(ch13);

            Channel ch14 = new Channel("14", "Торговые дома");
            iChannelRepository.save(ch14);

            Channel ch15 = new Channel("15", "ВЕЛКОМ");
            iChannelRepository.save(ch15);

            Channel ch16 = new Channel("16", "HoReCa");
            iChannelRepository.save(ch16);

            Channel ch50 = new Channel("50", "В2В");
            iChannelRepository.save(ch50);

            Channel ch80 = new Channel("80", "Оплата недостач");
            iChannelRepository.save(ch80);

            Channel ch90 = new Channel("90", "Прочие продажи");
            iChannelRepository.save(ch90);

        }

        if (iSectorRepository.findAll().isEmpty()) {

            Sector s10 = new Sector("10", "ГП Колбасные изделия");
            iSectorRepository.save(s10);

            Sector s11 = new Sector("11", "ГП Мясные ПФ");
            iSectorRepository.save(s11);

            Sector s50 = new Sector("50", "Продукция B2b");
            iSectorRepository.save(s50);

            Sector s80 = new Sector("80", "Продажи сотрудникам");
            iSectorRepository.save(s80);

            Sector s90 = new Sector("90", "ТМЦ");
            iSectorRepository.save(s90);

        }

    }

    // Master data processing

    public void saveMaterial(Material mat) throws SQLException {

        Material material = iMaterialRepository.findMaterialById(mat.getId());

        if (material == null) {
            iMaterialRepository.save(mat);
        }

    }

    public void savePartner(Partner par) {

        Partner partner = iPartnerRepository.findPartnerById(par.getId());

        if (partner == null) {
            iPartnerRepository.save(par);
        }

    }

    // Data reading

    // General information about our company

    public Company readSingleCompany(String companiId) {
        return iCompanyRepository.findCompanyById(companiId);
    }

    public List<Company> readArrayCompanies() {
        return iCompanyRepository.findAll();
    }

    public List<Channel> readAllSalesChannels() {
        return iChannelRepository.findAll();
    }

    // materials

    public List<Material> readMaterialDictionary() {
        return iMaterialRepository.findAll();
    }

    public Material readMaterialByKey(long key) {
        return iMaterialRepository.findMaterialById(key);
    }

    public List<Material> readMaterialsBetween(long low, long high) {
        return iMaterialRepository.findMaterialByIdBetween(low, high);
    }

    public List<Material> readMaterialByDescLike(String description) {
        description += "%";
        return iMaterialRepository.findMaterialByDescriptionLike(description);
    }

    // partners

    public List<Partner> readPartnerDictionary() {
        return iPartnerRepository.findAll();
    }

    public Partner readPartnerByKey(String key) {
        return iPartnerRepository.findPartnerById(key);
    }

    public List<Partner> readPartnersBetween(String low, String high) {
        return iPartnerRepository.findPartnerByIdBetween(low, high);
    }

    public List<Partner> readPartnerByNameLikeIt(String name) {
        name += "%";
        return iPartnerRepository.findPartnerByNameLike(name);
    }

    // Transaction data processing

    public void refreshTransactionData() {
        iCashDocumentRepository.deleteAll();
        iDeliveryHeadRepository.deleteAll();
        iDeliveryItemRepository.deleteAll();
        iDistributedHeadRepository.deleteAll();
        iDistributedItemRepository.deleteAll();
    }

    // Cash Documents

    public void saveCashDocumentsByQueue(Queue<CashDocument> docs) {

        for (int j = (docs.size() - 1); j >= 0; j--) {
            CashDocument cd = docs.poll();
            if (cd != null) {
                CashDocument cashDocument = iCashDocumentRepository.findOne(cd.getId());
                if (cashDocument != null) {
                    continue;
                }
                saveCashDocument(cd);
            }
        }

    }

    public void saveCashDocument(CashDocument doc) {
        iCashDocumentRepository.save(doc);
    }

    public List<CashDocument> readAllCashDocuments() {
        return iCashDocumentRepository.findAll();
    }

    public CashDocument readCashDocumentByKey(long id) {
        return iCashDocumentRepository.findCashDocumentById(id);
    }

    public List<CashDocument> readCashDocumentsByDate(java.sql.Date postingDate) {
        return iCashDocumentRepository.findCashDocumentByPostingDate(postingDate);
    }

    public List<CashDocument> readCashDocumentsByIdBetween(long low, long high) {
        return iCashDocumentRepository.findCashDocumentByIdBetween(low, high);
    }

    public List<CashDocument> readCashDocumentsByYearBetween(int low, int high) {
        return iCashDocumentRepository.findCashDocumentByYearBetween(low, high);
    }

    public List<CashDocument> readCashDocumentsByDateBetween(java.sql.Date fromDate, java.sql.Date toDate) {
        return iCashDocumentRepository.findCashDocumentByPostingDateBetween(fromDate, toDate);
    }

    // Outbound deliveries

    public DeliveryHead readDeliveryHeadByKey(long id) {
        return iDeliveryHeadRepository.findDeliveryHeadById(id);
    }

    // Headers
    public void saveDeliveryHead(DeliveryHead deliveryHead) {
        iDeliveryHeadRepository.save(deliveryHead);
    }

    // Read all deliveries with the zeroes
    public Map<Long, List<DeliveryItem>> readDeliveriesWithNullAmount() {

        Map<Long, List<DeliveryItem>> deliveryMap = new ConcurrentHashMap<>();
        StringBuilder sb = new StringBuilder(0);
        sb.append("SELECT id FROM delivery_head WHERE amount = 0.00 ORDER BY id");

        Statement stmt = null;
        try {
            stmt = dataSource.getConnection().createStatement();
            ResultSet rs1 = stmt.executeQuery(sb.toString());
            while (rs1.next()) {
                long deliveryId = rs1.getLong("id");
                List<DeliveryItem> items = readDeliveryItemsByKey(deliveryId);
                if (items != null && !items.isEmpty()) {
                    deliveryMap.put(deliveryId, items);
                }
            }
            rs1.close();
        } catch (SQLException sqe1) {
            sqe1.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (SQLException sqe2) {
                sqe2.printStackTrace();
            }
        }

        return deliveryMap;
    }

    // Items

    public DeliveryItem readOneDeliveryItemByKey(DeliveryItemId delId) {
        return iDeliveryItemRepository.findOne(delId);
    }

    public void saveDeliveryItem(DeliveryItem deliveryItem) {
        iDeliveryItemRepository.save(deliveryItem);
    }

    public List<DeliveryItem> readDeliveryItemsByKey(long key) {
        return iDeliveryItemRepository.findDeliveryItemById(key);
    }

    public void deleteDeliveryItem(DeliveryItem deliveryItem) {
        iDeliveryItemRepository.delete(deliveryItem);
    }

    public List<DeliveryItem> readAllDeliveries() {
        return iDeliveryItemRepository.findAll();
    }

    public List<DeliveryHead> readDeliveriesHeadsByDate(java.sql.Date d) {
        return iDeliveryHeadRepository.findDeliveryHeadByPostingDateEquals(d);
    }

    public List<DeliveryHead> readDeliveriesHeadsBetweenDates(java.sql.Date d1, java.sql.Date d2) {
        return iDeliveryHeadRepository.findDeliveryHeadByPostingDateBetween(d1, d2);
    }

    // Get a total gross amount by delivery number
    public void updateTotalAmountsByDate(java.sql.Date d) {

        List<Long> heads = new ArrayList<>();

        List<CashDocument> docs = iCashDocumentRepository.findCashDocumentByPostingDate(d);
        // Read all numbers of outbound deliveries
        docs.forEach(doc -> {
            heads.add(doc.getDeliveryId());
        });

        for (long id: heads) {
            double sum = 0.00;
            DeliveryHead dh = iDeliveryHeadRepository.findDeliveryHeadById(id);
            if (dh != null) {
                List<DeliveryItem> its = iDeliveryItemRepository.findDeliveryItemById(id);
                for (DeliveryItem di: its) {
                    sum = sum + di.getGrossPrice().doubleValue();
                }
                dh.setTotalAmount(BigDecimal.valueOf(sum));
                iDeliveryHeadRepository.save(dh);
            }
        }

    }

    public void deleteZerosDeliveryItems() throws SQLException {

        StringBuilder sb = new StringBuilder(0)
                .append("DELETE FROM delivery_item WHERE quantity = 0.000");

        PreparedStatement pstmt = null;

        try {
            pstmt = dataSource.getConnection().prepareStatement(sb.toString());
            pstmt.executeUpdate();
        } finally {
            pstmt.close();
        }

    }

    // Distributed head
    public void saveDistributedHead(DistributedHead distributedHead) {
        iDistributedHeadRepository.save(distributedHead);
    }

    // Distributed items
    public List<DistributedItem> readDistributedItemsByKey(long id) {
        return iDistributedItemRepository.findDistributedItemById(id);
    }

    public DistributedItem singleReadDistributedItem(long id, long position) {
        return iDistributedItemRepository.findDistributedItemByIdAndPosition(id, position);
    }

    public List<DistributedHead> readDistributedHeadsBetweenDates(java.sql.Date d1, java.sql.Date d2) {
        return iDistributedHeadRepository.findDistributedHeadByPostingDateBetween(d1, d2);
    }

    public void saveDistributedItem(DistributedItem distributedItem) {
        iDistributedItemRepository.save(distributedItem);
    }


}
