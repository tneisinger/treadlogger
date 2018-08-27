package logic.db;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

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


}