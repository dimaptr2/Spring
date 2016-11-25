package ru.velkomfood.mysap.services.controller;

import ru.velkomfood.mysap.services.model.entities.MaterialEntity;
import ru.velkomfood.mysap.services.model.entities.MrpStockReqListEntity;
import ru.velkomfood.mysap.services.model.entities.PlantEntity;
import ru.velkomfood.mysap.services.model.entities.PurchaseGroupEntity;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;


/**
 * Created by DPetrov on 06.06.2016.
 */
public class AnalyticDataEngine {

    private Connection connection;

    // setters and getters

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean connectionIsValid() throws SQLException {

        if (connection.isClosed() || connection == null) {

            return false;

        } else {

            Statement stmt = connection.createStatement();
            initDb(stmt);
            if (!stmt.isClosed()) stmt.close();
            return true;

        }

    }

    private void initDb(Statement st) throws SQLException {

        int counter = 0;

        String sql = "CREATE TABLE IF NOT EXISTS mara(matnr varchar(18) NOT NULL PRIMARY KEY" +
                ", maktx varchar(40))";
        st.execute(sql);
        sql = "CREATE TABLE IF NOT EXISTS t001w(werks INT NOT NULL PRIMARY KEY, name varchar(30))";
        st.execute(sql);
        sql = "CREATE TABLE IF NOT EXISTS pur_groups(id varchar(3) NOT NULL PRIMARY KEY, description VARCHAR(18))";
        st.execute(sql);

        // Plant's entity
        sql = "SELECT count( werks ) AS nrows FROM t001w";
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            counter = rs.getInt("nrows");
        }
        rs.close();

        // If the plant number doesn't exist.
        if (counter == 0) {
            PlantEntity pe = new PlantEntity();
            pe.setWerks(1000);
            pe.setName("ООО МК Павловская Слобода");
            sql = "INSERT INTO t001w VALUES(" + pe.getWerks() + ", \'" +
                    pe.getName() + "\')";
            st.execute(sql);
        }

        // Where are purchase groups?
        rs = st.executeQuery("SELECT count( id ) AS pnumbs FROM pur_groups");

        while (rs.next()) {
            counter = rs.getInt("pnumbs");
        }
        rs.close();

        // Create a dictionary with the purchase groups.
        if (counter == 0) {
            PreparedStatement pstmt = connection.prepareStatement("INSERT INTO pur_groups" +
                    " VALUES(?, ?)");
            pstmt.setString(1, "001");
            pstmt.setString(2, "Погост А.");
            pstmt.addBatch();
            pstmt.setString(1, "002");
            pstmt.setString(2, "Погост А.");
            pstmt.addBatch();
            pstmt.setString(1, "003");
            pstmt.setString(2, "Васильева О.");
            pstmt.addBatch();
            pstmt.setString(1, "004");
            pstmt.setString(2, "Богданова К.");
            pstmt.addBatch();
            pstmt.setString(1, "005");
            pstmt.setString(2, "Базаров А.");
            pstmt.addBatch();
            pstmt.setString(1, "006");
            pstmt.setString(2, "Мясосырье");
            pstmt.addBatch();
            pstmt.setString(1, "007");
            pstmt.setString(2, "Услуги");
            pstmt.addBatch();
            pstmt.setString(1, "010");
            pstmt.setString(2, "Базаров А.");
            pstmt.addBatch();
            pstmt.setString(1, "011");
            pstmt.setString(2, "Калинина А.");
            pstmt.addBatch();
            pstmt.setString(1, "012");
            pstmt.setString(2, "Ерёмин С.");
            pstmt.addBatch();
            pstmt.setString(1, "999");
            pstmt.setString(2, "Бухгалтерия");
            pstmt.addBatch();

            pstmt.executeBatch();
            pstmt.close();
        }


        // Create tables for items
        createBasicTables(st);

    }

    private void createBasicTables(Statement statement) throws SQLException {

        final String CREATE = "CREATE TABLE IF NOT EXISTS";

        String sql = CREATE + " mrpitems" + "(id bigint NOT NULL PRIMARY KEY " +
                "auto_increment, ";
        sql = sql + "werks int, matnr VARCHAR(18), ";
        sql = sql + "avail_date date, per_segmt VARCHAR(22), ";
        sql = sql + "base_unit VARCHAR(3), pur_group VARCHAR(3), ";
        sql = sql + "pri_req_quantity DECIMAL(20, 3), sec_req_quantity DECIMAL(20, 3), " +
                "avail_quantity DECIMAL(20, 3))";

        statement.execute(sql);

    }

    public void fillMaterialsTable(List<Map<String, String>> materials) throws SQLException {

        int nRows = 0;
        PreparedStatement pstmt;
        ResultSet res = null;

        Statement lstmt = connection.createStatement();

        lstmt.execute("DELETE FROM mara");
        lstmt.execute("DELETE FROM mrpitems");

        pstmt = connection.prepareStatement("INSERT INTO mara VALUES(?, ?)");

        try {

            for (Map<String, String> entity: materials) {
                pstmt.setString(1, entity.get("matnr"));
                pstmt.setString(2, entity.get("maktx"));
                pstmt.executeUpdate();
            }

            res = lstmt.executeQuery("SELECT count( matnr ) AS nmats FROM mara");

            while (res.next()) {
                nRows = res.getInt("nmats");
            }

        } finally {

            pstmt.close();
            res.close();
            lstmt.close();

        }

        System.out.println("Number rows in MARA: " + nRows);

    }

    public List<PlantEntity> findPlant() throws SQLException {

        List<PlantEntity> plants = new ArrayList<>();
        Statement l_stmt = null;
        ResultSet rs = null;

        try {

            l_stmt = connection.createStatement();
            rs = l_stmt.executeQuery("SELECT * FROM t001w");

            while (rs.next()) {
                PlantEntity pe = new PlantEntity();
                pe.setWerks(rs.getInt("werks"));
                pe.setName(rs.getString("name"));
                plants.add(pe);
            }

        } finally {

            rs.close();
            l_stmt.close();

        }

        return plants;

    }

    public List<PurchaseGroupEntity> findPurchaseGroups() throws SQLException {

        List<PurchaseGroupEntity> purg = new ArrayList<>();
        Statement l_stmt = connection.createStatement();
        ResultSet rs = l_stmt.executeQuery("SELECT * FROM pur_groups ORDER BY id");

        while (rs.next()) {
            PurchaseGroupEntity pur = new PurchaseGroupEntity();
            pur.setId(rs.getString("id"));
            pur.setDescription(rs.getString("description"));
            purg.add(pur);
        }

        return purg;

    }

    public List<MaterialEntity> findAllMaterials() throws SQLException {

        List<MaterialEntity> materials_list = new ArrayList<>();
        Statement l_stmt = null;
        ResultSet rs = null;

        try {

            l_stmt = connection.createStatement();
            rs = l_stmt.executeQuery("SELECT matnr, maktx FROM mara ORDER BY matnr");

            while (rs.next()) {
                MaterialEntity me = new MaterialEntity();
                me.setMatnr(rs.getString("matnr"));
                me.setMaktx(rs.getString("maktx"));
                materials_list.add(me);
            }

        } finally {

            rs.close();
            l_stmt.close();

        }

        return materials_list;

    }

    public void fillMrpStockReqList(List<MrpStockReqListEntity> mrplist) throws SQLException {

        String sql = "INSERT INTO mrpitems (werks, matnr, avail_date, per_segmt, base_unit, pur_group, " +
                "pri_req_quantity, sec_req_quantity, avail_quantity) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement pstmt = connection.prepareStatement(sql);

        Iterator<MrpStockReqListEntity> it = mrplist.iterator();

        try {
            while (it.hasNext()) {
                MrpStockReqListEntity me = it.next();
                pstmt.setInt(1, me.getPlant());
                pstmt.setString(2, me.getMaterial());
                pstmt.setString(3, me.getMrpDate());
                pstmt.setString(4, me.getPer_segmt());
                pstmt.setString(5, me.getBaseUnit());
                pstmt.setString(6, me.getPurchaseGroup());
                pstmt.setBigDecimal(7, me.getPri_rq_quantity());
                pstmt.setBigDecimal(8, me.getSec_req_quantity());
                pstmt.setBigDecimal(9, me.getAvail_quantity());
                pstmt.addBatch();
            }

            pstmt.executeBatch();
//            int[] numRows = pstmt.executeBatch();

//            System.out.print(".");

        } finally {
             pstmt.close();
        }

    }

    public int checkNumberItems() throws SQLException {

        Statement stmt = connection.createStatement();
        int counter = 0;

        ResultSet rs = stmt.executeQuery("SELECT count( id ) AS nums FROM mrpitems");

        if (rs != null) {
            while (rs.next()) {
                counter = rs.getInt("nums");
            }
        }

        return counter;

    }

    // Received parameters are in hash table type of (year: [], month: [])
    public List<MrpStockReqListEntity> findMrpReqData(Map<String, LocalDate[]> params) throws SQLException {

        List<MrpStockReqListEntity> mrpitems = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM mrpitems";

        if (params.size() > 0) {

            sql = sql + " WHERE ";

            for (Map.Entry<String, LocalDate[]> entry: params.entrySet()) {
                sql = sql + entry.getKey() + " BETWEEN (";
                LocalDate[] datum = entry.getValue();
                if (datum.length > 0 )
                    sql = sql + "\'" + datum[0] + "\', " + "\'" + datum[1] + "\')";
            } // end of cycle

        }

        sql = sql + " GROUP BY id, werks, matnr, avail_date";
        sql = sql + " ORDER BY id, werks, matnr, avail_date";


        try {

            stmt = connection.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {

            }

        } finally {

            rs.close();
            stmt.close();

        }

        return mrpitems;

    }

    public void closeConnection() throws SQLException {

        if (connection != null || !connection.isClosed())
            connection.close();

    }
}
