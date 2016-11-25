package ru.velkomfood.copc.ckm.server.controller;

import com.sap.conn.jco.JCoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.velkomfood.copc.ckm.server.model.AccountEntity;
import ru.velkomfood.copc.ckm.server.model.CostEstimateHeaders;
import ru.velkomfood.copc.ckm.server.model.MaterialEntity;
import ru.velkomfood.copc.ckm.server.model.MaterialValuationEntity;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dpetrov on 25.08.2016.
 */

@Component
public class CacheEngine {

    private static boolean startFlag;

    @Autowired
    @Qualifier("h2data")
    private DataSource dataSource;

    @Autowired
    @Qualifier("sap_data_source")
    private SapEngine sapEngine;

    public CacheEngine() {
        startFlag = true;
    }
    // Run every 2 minutes
    @Scheduled(cron = "0 28 20 * * *")  // (second, minute, hour, day, month, day of week)
    public void updateSapInfo() {

        if (dataSource == null) {

            System.out.println("Data source was not defined");

        } else {

            System.out.println("That's all right!");
            Date start = new Date();
            long startPoint = start.getTime();

            try {

                sapEngine.initDestination();
                sapEngine.searchMaterials();
                sapEngine.searchAccounts();
                sapEngine.searchCostEstimateHeaders();
                Map<Integer, MaterialEntity> materials = sapEngine.getMaterials();
                Map<Integer, MaterialValuationEntity> valuations = sapEngine.getValuations();
                List<AccountEntity> accounts = sapEngine.getAccounts();
                List<CostEstimateHeaders> estimateHeaders = sapEngine.getEstimateHeaders();

                System.out.printf("Were found %d", materials.size());
                System.out.println(" materials");
                System.out.printf("Were found %d", valuations.size());
                System.out.println(" material valuations");
                System.out.printf("Were found %d", accounts.size());
                System.out.println(" accounts");
                System.out.printf("Were found %d", estimateHeaders.size());
                System.out.println(" cost estimate documents");

                if (startFlag) {
                    initDatabase(dataSource);
                    startFlag = false;
                }

                materialsUpload(materials, dataSource);
                valuationsUpload(valuations, dataSource);
                accountsUpload(accounts, dataSource);

                materials.clear();
                valuations.clear();
                accounts.clear();
                estimateHeaders.clear();

            } catch (JCoException | SQLException sapex) {
                sapex.printStackTrace();
            }

            Date finish = new Date();
            long endPoint = finish.getTime();
            long timeOfExecution = ( endPoint - startPoint ) / 60000;
            System.out.printf("Time of execution is %d", timeOfExecution);
            System.out.println(" minutes");

        } // if

    } // update SAP information

    private void initDatabase(DataSource ds) throws SQLException {

        String sql = "";
        Statement statement = ds.getConnection().createStatement();

        try {
            sql = "CREATE TABLE IF NOT EXISTS mara(id INT NOT NULL PRIMARY KEY, description VARCHAR(40))";
            statement.executeUpdate(sql);
            System.out.println("MARA table was created");
            sql = "CREATE TABLE IF NOT EXISTS mbew(id INT NOT NULL, plant INT NOT NULL, ";
            sql = sql + "base_unit VARCHAR(3), price_control VARCHAR(1), currency VARCHAR(3), ";
            sql = sql + "moving_price DECIMAL(20,3), fixed_price DECIMAL(20,3), price_unit DECIMAL(20,3), ";
            sql = sql + "PRIMARY KEY (id, plant))";
            statement.executeUpdate(sql);
            System.out.println("MBEW table was created");
            sql = "CREATE TABLE IF NOT EXISTS accounts(account_number VARCHAR(10) NOT NULL PRIMARY KEY, ";
            sql = sql + "description VARCHAR(50))";
            statement.executeUpdate(sql);
            System.out.println("ACCOUNTS table was created");
            sql = "CREATE TABLE IF NOT EXISTS ckmh(ref_object VARCHAR(1) NOT NULL, calc_number INT NOT NULL, ";
            sql = sql + "estimate_type VARCHAR(2), start_date DATE, version VARCHAR(2), variant VARCHAR(3), ";
            sql = sql + "valid_from DATE, valid_to DATE, status VARCHAR(2), PRIMARY KEY (ref_object, calc_number))";
            statement.executeUpdate(sql);
            System.out.println("CKMH table was created");
        } finally {
            statement.close();
        }

    } // end of method initDatabase()

    // Upload a dictionary of materials
    private void materialsUpload(Map<Integer, MaterialEntity> matCache,
                                 DataSource ds) throws SQLException {

        boolean skipNextStep;
        int counter = 0;
        final String INSERT = "INSERT INTO mara VALUES (?, ?)";
        PreparedStatement pstmt = ds.getConnection().prepareStatement(INSERT);
        Statement stmt = ds.getConnection().createStatement();

        try {

            if (!matCache.isEmpty()) {
                Iterator<Map.Entry<Integer, MaterialEntity>> it = matCache.entrySet().iterator();
                while (it.hasNext()) {
                    MaterialEntity me = it.next().getValue();
                    String select = "SELECT id FROM mara WHERE id = ";
                    select = select + me.getId();
                    ResultSet rs = stmt.executeQuery(select);
                    if (rs != null && rs.first()) skipNextStep = true;
                    else skipNextStep = false;
                    rs.close();
                    if (skipNextStep) continue;
                    String text = me.getDescription();
                    Pattern pattern = Pattern.compile("[^a-zA-Zа-яА-Я0-9]");
                    Matcher matcher = pattern.matcher(text);
                    while (matcher.find()) {
                        text = text.replace(Character.toString(text.charAt(matcher.start())), " ");
                    }
                    pstmt.setInt(1, me.getId());
                    pstmt.setString(2, text);
                    pstmt.addBatch();
                    counter++;
                } // While
                pstmt.executeBatch();
            } // If

        } finally {
            stmt.close();
            pstmt.close();
        }

        System.out.printf("%d rows", counter);
        System.out.println(" were inserted into \"mara\" table.");

    } // upload a dictionary of materials

    // Upload a material's valuations
    private void valuationsUpload(Map<Integer, MaterialValuationEntity> valCache,
                                  DataSource ds) throws SQLException {
        boolean skipNextStep;
        int counter = 0;
        Statement stmt = ds.getConnection().createStatement();
        final String INSERT = "INSERT INTO mbew VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = ds.getConnection().prepareStatement(INSERT);

        try {

            if (!valCache.isEmpty()) {
                Iterator<Map.Entry<Integer, MaterialValuationEntity>> it = valCache.entrySet().iterator();
                while (it.hasNext()) {
                    MaterialValuationEntity mve = it.next().getValue();
                    String select = "SELECT id FROM mbew WHERE id = ";
                    select = select + mve.getId();
                    ResultSet rs = stmt.executeQuery(select);
                    if (rs != null && rs.first()) skipNextStep = true;
                    else skipNextStep = false;
                    rs.close();
                    if (skipNextStep) continue;
                    pstmt.setInt(1, mve.getId());
                    pstmt.setInt(2, mve.getPlant());
                    pstmt.setString(3, mve.getBaseUnit());
                    pstmt.setString(4, mve.getPriceControl());
                    pstmt.setString(5, mve.getCurrency());
                    pstmt.setBigDecimal(6, mve.getMovingPrice());
                    pstmt.setBigDecimal(7, mve.getFixedPrice());
                    pstmt.setBigDecimal(8, mve.getPriceUnit());
                    pstmt.addBatch();
                    counter++;
                } // While
                pstmt.executeBatch();
            } // If

        } finally {
            stmt.close();
            pstmt.close();
        }

        System.out.printf("%d rows", counter);
        System.out.println(" were inserted into \"mbew\" table.");

    } // upload valuations

    private void accountsUpload(List<AccountEntity> accounts,
                                DataSource ds) throws SQLException {

        boolean skipNextStep;
        int counter = 0;
        Statement stmt = ds.getConnection().createStatement();
        final String INSERT = "INSERT INTO accounts VALUES (?, ?)";
        PreparedStatement pstmt = ds.getConnection().prepareStatement(INSERT);

        try {

            if (!accounts.isEmpty()) {
                Iterator<AccountEntity> it = accounts.iterator();
                while (it.hasNext()) {
                    AccountEntity ae = it.next();
                    String select = "SELECT account_number FROM accounts WHERE account_number = ";
                    select = select + "\'" + ae.getAccountNumber() + "\'";
                    ResultSet rs = stmt.executeQuery(select);
                    if (rs != null && rs.first()) skipNextStep = true;
                    else skipNextStep = false;
                    rs.close();
                    if (skipNextStep) continue;
                    pstmt.setString(1, ae.getAccountNumber());
                    pstmt.setString(2, ae.getDescription());
                    pstmt.addBatch();
                    counter++;
                }
                pstmt.executeBatch();
            }

        } finally {
            stmt.close();
            pstmt.close();
        }

        System.out.printf("%d rows", counter);
        System.out.println(" were inserted into \"accounts\" table.");

    } // upload accounts


}
