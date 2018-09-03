package ui.cli;

import com.beust.jcommander.JCommander;
import logic.db.SqliteDB;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class CommandAddTest {

    private static final String TEST_DB_PATH = "test_treadlogger.sqlite3";
    private SqliteDB db;
    private CommandAdd commandAdd;
    private JCommander jCommander;

    @BeforeEach
    void setUp() {
        this.db = new SqliteDB(TEST_DB_PATH);
        this.commandAdd = new CommandAdd(db);
        this.jCommander = JCommander.newBuilder()
                .addCommand("add", this.commandAdd)
                .build();
    }

    @AfterEach
    void tearDown() {
        File dbFile = new File(TEST_DB_PATH);
        assertTrue(dbFile.delete());
    }

    @Test
    void run_unparseableElapsedTime() {
        String[] args = new String[]{"add", "unparseable", "1.0"};
        this.jCommander.parse(args);
        assertThrows(Exception.class, commandAdd::run);
    }

    @Test
    void run_noArgsGiven() {
        String[] args = new String[]{"add"};
        this.jCommander.parse(args);
        assertThrows(Exception.class, commandAdd::run);
    }

    @Test
    void run_noDistanceGiven() {
        String[] args = new String[]{"add", "1:11"};
        this.jCommander.parse(args);
        assertThrows(Exception.class, commandAdd::run);
    }

    @Test
    void run_unparseableDistance() {
        String[] args = new String[]{"add", "1:11", "unparseable"};
        this.jCommander.parse(args);
        assertThrows(Exception.class, commandAdd::run);
    }
}