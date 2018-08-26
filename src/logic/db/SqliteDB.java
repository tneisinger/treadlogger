package logic.db;

import logic.ElapsedTime;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class SqliteDB {
    private static final String CONNECTION_STRING_PREFIX = "jdbc:sqlite:";
    private static final String DEFAULT_DB_FILEPATH = "treadlogger.sqlite3";

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

    public void setupDatabaseTables() {
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
            System.out.println("Table created!");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void insertData(ElapsedTime elapsedTime, double distance, boolean includeLocalTimeNow) {
        if (includeLocalTimeNow) {
            this.insertData(elapsedTime, distance, LocalDate.now(), LocalTime.now());
            return;
        }

        this.insertData(elapsedTime, distance, LocalDate.now(), false);
    }

    public void insertData(ElapsedTime elapsedTime, double distance, LocalDate date, boolean includeLocalTimeNow) {
        if (includeLocalTimeNow) {
            this.insertData(elapsedTime, distance, date, LocalTime.now());
            return;
        }

        String sql = "INSERT INTO treadDataPoints(minutes,distance,date) VALUES(?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, elapsedTime.getMinutes());
            pstmt.setDouble(2, distance);
            pstmt.setString(3, date.toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void insertData(ElapsedTime elapsedTime, double distance, LocalDate date, LocalTime time) {
        String sql = "INSERT INTO treadDataPoints(minutes,distance,date,time) VALUES(?,?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, elapsedTime.getMinutes());
            pstmt.setDouble(2, distance);
            pstmt.setString(3, date.toString());
            pstmt.setString(4, time.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
