package logic.db;

import logic.ElapsedTime;
import logic.TreadDataPoint;

import javax.xml.transform.Result;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SqliteDB {
    private static final String CONNECTION_STRING_PREFIX = "jdbc:sqlite:";
    private static final String DEFAULT_DB_FILEPATH = System.getProperty("user.home") + "/treadlog.sqlite3";

    private String url;

    public SqliteDB(String dbFilePath) {
        this.url = CONNECTION_STRING_PREFIX + dbFilePath;

        // Setup the db tables if they do not exist
        this.setupDatabaseTables();
    }

    public SqliteDB() {
        this(DEFAULT_DB_FILEPATH);
    }

    private Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(this.url);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return conn;
    }

    private void setupDatabaseTables() {
        String sql = "CREATE TABLE IF NOT EXISTS treadDataPoints (\n"
                + " id INTEGER PRIMARY KEY,\n"
                + " minutes INTEGER NOT NULL, \n"
                + " distance REAL NOT NULL, \n"
                + " date TEXT DEFAULT CURRENT_DATE,\n"
                + " time TEXT\n"
                + ");";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void insertData(ElapsedTime elapsedTime, double distance, boolean includeLocalTimeNow) throws SQLException {
        if (includeLocalTimeNow) {
            this.insertData(elapsedTime, distance, LocalDate.now(), LocalTime.now());
            return;
        }

        this.insertData(elapsedTime, distance, LocalDate.now(), false);
    }

    public void insertData(ElapsedTime elapsedTime, double distance, LocalDate date, boolean includeLocalTimeNow)
            throws SQLException {

        if (includeLocalTimeNow) {
            this.insertData(elapsedTime, distance, date, LocalTime.now());
            return;
        }

        String sql = "INSERT INTO treadDataPoints(minutes,distance,date) VALUES(?,?,?)";

        Connection conn = this.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setInt(1, elapsedTime.getMinutes());
        pstmt.setDouble(2, distance);
        pstmt.setString(3, date.toString());
        pstmt.executeUpdate();

        pstmt.close();
        conn.close();
    }

    public void insertData(ElapsedTime elapsedTime, double distance, LocalDate date, LocalTime time)
            throws SQLException {

        String sql = "INSERT INTO treadDataPoints(minutes,distance,date,time) VALUES(?,?,?,?)";

        Connection conn = this.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setInt(1, elapsedTime.getMinutes());
        pstmt.setDouble(2, distance);
        pstmt.setString(3, date.toString());
        pstmt.setString(4, time.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        pstmt.executeUpdate();

        pstmt.close();
        conn.close();
    }

    public List<TreadDataPoint> getDataBetweenDates(LocalDate startDate, LocalDate endDate) throws SQLException {
        String sql = "SELECT minutes, distance, date FROM treadDataPoints " +
                "WHERE date BETWEEN '" + startDate + "' AND '" + endDate + "'";

        Connection conn = this.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql);

        ResultSet rs = pstmt.executeQuery();

        List<TreadDataPoint> result = new ArrayList<>();

        while (rs.next()) {
            result.add(new TreadDataPoint(
                    rs.getInt("minutes"),
                    rs.getDouble("distance"),
                    rs.getString("date")
            ));
        }


        pstmt.close();
        conn.close();

        return result;
    }
}
