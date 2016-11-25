package ru.velkomfood.acs.inquisitors.controller;

import org.springframework.beans.factory.annotation.Autowired;
import ru.velkomfood.acs.inquisitors.model.EventsEntity;
import ru.velkomfood.acs.inquisitors.model.JobTimeEntity;
import ru.velkomfood.acs.inquisitors.model.UsersEntity;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.stream.Stream;

/**
 * Created by Velkomfood on 27.06.16.
 */

public class OurDataManager {

    private final String FILENAME = "conf.txt";
    private List<String> uids;
    private Properties properties;

    @Autowired
    private DataSource dataSource;

    private List<UsersEntity> usersList;
    private List<EventsEntity> redList;
    private List<EventsEntity> redList8;
    private List<JobTimeEntity> jobTimeEntities;

    public OurDataManager() {

        uids = new ArrayList<>();
        properties = new Properties();
        usersList = new ArrayList<>();
        redList = new ArrayList<>();
        redList8 = new ArrayList<>();
        jobTimeEntities = new ArrayList<>();

    }


    public DataSource getDataSource() {
        return dataSource;
    }

    // Read file with "uids"
    public void readUidFromFile() throws IOException {

        if (!uids.isEmpty()) uids.clear();

        Stream<String> stream = Files.lines(Paths.get(FILENAME));

        // Read the stream of the data line by line with the little help of
        // my friend (He's a multithreading method, called "forEach()").
        stream.forEach(row -> { uids.add(row); });

        FileInputStream fis = new FileInputStream("actual.properties");
        properties.load(fis);
        fis.close();


        // If set an initial date, then we must process the data for current day.
        if (properties.getProperty("day").equals("0000-00-00")) {

            Date now = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String currentDate = format.format(now);
            properties.setProperty("day", currentDate);

        }

        System.out.println("The day is my enemy: " + properties.getProperty("day"));
    }

    // Retrieve the users list from database
    public void buildUsersEntities() throws SQLException {

        uids.forEach(uid -> {

            try {

                Connection connection = dataSource.getConnection();

                String sql = "SELECT uid, fullname FROM users WHERE uid = ?";
                PreparedStatement pstmt = connection.prepareStatement(sql);
                pstmt.setLong(1, Long.parseLong(uid));

                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    UsersEntity ue = new UsersEntity();
                    ue.setUid(rs.getLong("UID"));
                    ue.setFullname(rs.getString("FULLNAME"));
                    usersList.add(ue);
                }

                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (connection != null) connection.close();

            } catch (SQLException sqe) {

                sqe.printStackTrace();

            }

        }); // for Each

    }

    // Doorid /They are keys of doors/ { 1 - Central door, 8 - Administration }

    public void readEvents() throws SQLException {

        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date begin, end, endOfDay, event;

        for (UsersEntity usr: usersList) {

            if (!redList.isEmpty()) {
                redList.clear();
                redList8.clear();
            }

            if (!jobTimeEntities.isEmpty()) jobTimeEntities.clear();

            try {

                Connection connection = dataSource.getConnection();
                Statement stmt = connection.createStatement();
                String sql = "SELECT regid, uid, regdate, doorid, regdatefull FROM graph_fact_events ";
                sql = sql + " WHERE uid = " + usr.getUid();
                sql = sql + " AND regdate LIKE " + "\'" + properties.getProperty("day") + "%" + "\'";
                sql = sql + " AND doorid IN (1, 8) ORDER BY regid, uid, regdate, doorid";

                ResultSet rs = stmt.executeQuery(sql);

                while (rs.next()) {
                    EventsEntity ee = new EventsEntity();
                    ee.setRegid(rs.getLong("REGID"));
                    ee.setUid(rs.getLong("UID"));
                    ee.setRegdate(rs.getDate("REGDATE"));
                    ee.setDoorid(rs.getLong("DOORID"));
                    ee.setRegdatefull(rs.getDate("REGDATEFULL"));
                    ee.setRegtime(rs.getTime("REGDATE"));
                    ee.setRegtimefull(rs.getTime("REGDATEFULL"));
                    int doorId = (int) ee.getDoorid();
                    switch (doorId) {
                        case 1:
                            redList.add(ee);
                            break;
                        case 8:
                            redList8.add(ee);
                            break;
                    }
                }

                if (rs != null) rs.close();

                sql = "SELECT sfcode, starttime, endtime, uid FROM graph_fact";
                sql = sql + " WHERE starttime LIKE " + "\'" + properties.getProperty("day") + "%" + "\'";
                sql = sql + " AND uid = " + usr.getUid();
                sql = sql + " ORDER BY sfcode, uid";

                rs = stmt.executeQuery(sql);

                while (rs.next()) {
                    JobTimeEntity je = new JobTimeEntity();
                    je.setSfcode(rs.getLong("SFCODE"));
                    je.setStartDate(rs.getDate("STARTTIME"));
                    je.setEndDate(rs.getDate("ENDTIME"));
                    je.setStartTime(rs.getTime("STARTTIME"));
                    je.setEndTime(rs.getTime("ENDTIME"));
                    je.setUid(rs.getLong("UID"));
                    jobTimeEntities.add(je);
                }

                if (rs != null) rs.close();
                if (stmt != null) stmt.close();

                if (!redList.isEmpty()) {

                    EventsEntity input = redList.get(0);
                    EventsEntity input8 = redList8.get(0);

//                    EventsEntity output = redList.get((redList.size() - 1));

                    try {

                        begin = fmt.parse(properties.getProperty("day") + " 08:40:00");
                        end = fmt.parse(properties.getProperty("day") + " 17:30:00");


                        String tempDate = input.getRegdate() + " " + input.getRegtime();
                        event = fmt.parse(tempDate);

                        String hours;
                        String seconds = "00";

                        // Data processing ...

                        int value = generateRandomValue(25, 35);

                        if (event.after(begin) && event.before(end)) {

                            hours = "08";

                            // '1' door
                            changeTime(input, value, hours, seconds, connection);
                            // '8' door
                            changeTime(input8, value, hours, seconds, connection);

                            if (!jobTimeEntities.isEmpty()) {

                                JobTimeEntity jte = jobTimeEntities.get(0);
                                changeTimeInterval(jte, value, hours, seconds, connection);

                            }

                        } else if (event.after(end)) {
                            hours = "17";
//                            changeTime(output, value, hours, seconds, connection);
                        }

                        // end of data processing ...

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                } // red list is empty

                    if (connection != null) connection.close();

            } catch(SQLException sqe){

                sqe.printStackTrace();

            }

        } // usersList

    } // read events

    // Change items
    private void changeTime(EventsEntity ee,
                            int value,
                            String hours, String seconds, Connection conn) throws SQLException {

        String newTime = properties.getProperty("day") + " " + hours + ":" + value + ":" + seconds;
        String sql = "UPDATE graph_fact_events SET regdate = " + "\'" + newTime + "\', ";
        sql = sql + "regdatefull = " + "\'" + newTime + "\' ";
        sql = sql + "WHERE regid = " + ee.getRegid() + " AND " + "uid = " + ee.getUid();

        Statement statement = conn.createStatement();
        statement.executeUpdate(sql);

        statement.close();

    }

    // Change totals
    private void changeTimeInterval(JobTimeEntity jte,
                                    int value,
                                    String hours, String seconds, Connection conn) throws SQLException {

        String newTime = properties.getProperty("day") + " " + hours + ":" + value + ":" + seconds;
        String sql = "UPDATE graph_fact SET ";

        switch (hours) {
            case "08":
                sql = sql + "starttime = " + "\'" + newTime + "\'";
                break;
            case "17":
//                sql = sql + "endtime = " + "\'" + newTime + "\'";
                return;
//                break;
        }

        sql = sql + " WHERE sfcode = " + jte.getSfcode() + " AND " + "uid = " + jte.getUid();

        Statement statement = conn.createStatement();
        statement.executeUpdate(sql);

        statement.close();
    }

    // Random generating
    private int generateRandomValue(int begin, int end) {

        if (begin >= end) {
            throw new IllegalArgumentException("end must be greater than begin");
        }

        Random r = new Random();

        return (r.nextInt((end - begin) + 1) + begin);

    }

}
