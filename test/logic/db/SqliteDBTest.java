package logic.db;

import logic.ElapsedTime;
import logic.TreadDataPoint;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
        assertTrue(dbFile.delete());
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

    @Test
    void getDataBetweenDates_goodRequest() throws SQLException {
        int inputMinutes1 = 33;
        double inputDistance1 = 0.52;
        LocalDate inputDate1 = LocalDate.of(2018, 8, 2);
        LocalTime inputTime1 = LocalTime.of(2,15);
        this.db.insertData(new ElapsedTime(inputMinutes1), inputDistance1, inputDate1, inputTime1);

        int inputMinutes2 = 44;
        double inputDistance2 = 1.52;
        LocalDate inputDate2 = LocalDate.of(2018, 8, 26);
        LocalTime inputTime2 = LocalTime.of(4,30);
        this.db.insertData(new ElapsedTime(inputMinutes2), inputDistance2, inputDate2, inputTime2);

        int inputMinutes3 = 77;
        double inputDistance3 = 2.52;
        LocalDate inputDate3 = LocalDate.of(2018, 9, 3);
        LocalTime inputTime3 = LocalTime.of(4,59);
        this.db.insertData(new ElapsedTime(inputMinutes3), inputDistance3, inputDate3, inputTime3);

        int inputMinutes4 = 12;
        double inputDistance4 = 0.22;
        LocalDate inputDate4 = LocalDate.of(2018, 9, 1);
        LocalTime inputTime4 = LocalTime.of(9,50);
        this.db.insertData(new ElapsedTime(inputMinutes4), inputDistance4, inputDate4, inputTime4);

        List<TreadDataPoint> datapoints = this.db.getDataBetweenDates(
                LocalDate.of(2018, 8, 15),
                LocalDate.of(2018, 9, 1)
        );

        assertEquals(2, datapoints.size());
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