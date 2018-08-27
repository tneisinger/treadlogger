package logic.db;

import logic.ElapsedTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class SqliteDBTest {

    private static final String TEST_DB_PATH = "test_treadlogger.sqlite3";
    private SqliteDB db;

    @BeforeEach
    void setUp() {
        this.db = new SqliteDB(TEST_DB_PATH);
    }

    @AfterEach
    void tearDown() {
        File dbFile = new File(TEST_DB_PATH);
        dbFile.delete();
    }

    @Test
    void insertData_elapsedTimeAndDistanceIncludeTimeTrue() throws SQLException {
        // Insert one row into the treadDataPoints db table
        int inputMinutes = 33;
        double inputDistance = 0.52;
        this.db.insertData(new ElapsedTime(inputMinutes), inputDistance, true);

        assertTrue(tableHasOneRowWithTheseValues(inputMinutes, inputDistance, LocalDate.now(), LocalTime.now()));
    }

    @Test
    void insertData_elapsedTimeAndDistanceAndDateAndTimeIncludeTimeTrue() throws SQLException {
        // Insert one row into the treadDataPoints db table
        int inputMinutes = 33;
        double inputDistance = 0.52;
        LocalDate inputDate = LocalDate.of(2018, 8, 2);
        LocalTime inputTime = LocalTime.of(2,15);
        this.db.insertData(new ElapsedTime(inputMinutes), inputDistance, inputDate, inputTime);

        assertTrue(tableHasOneRowWithTheseValues(inputMinutes, inputDistance, inputDate, inputTime));
    }

    private boolean tableHasOneRowWithTheseValues(int inputMinutes, double inputDistance,
                                                     LocalDate inputDate, LocalTime inputTime) throws SQLException {
        // Request all the data from the treadDataPoints db table
        String sql = "SELECT * FROM treadDataPoints";
        Connection conn = DriverManager.getConnection("jdbc:sqlite:" + TEST_DB_PATH);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        // Store the data returned from the db into variables so we can perform an assert against it.
        int numResults = 0;
        int id = -1;
        int returnedMinutes = -1;
        double returnedDistance = -1;
        String returnedDate = "";
        String returnedTime = "";
        while (rs.next()) {
            numResults++;
            id = rs.getInt("id");
            returnedMinutes = rs.getInt("minutes");
            returnedDistance = rs.getDouble("distance");
            returnedDate = rs.getString("date");
            returnedTime = rs.getString("time");
        }

        return ( numResults == 1
                 && id == 1
                 && returnedMinutes == inputMinutes
                 && Math.abs(returnedDistance - inputDistance) <= 0.000001
                 && returnedDate.equals(inputDate.toString())
                 && returnedTime.equals(inputTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")))
        );
    }
}