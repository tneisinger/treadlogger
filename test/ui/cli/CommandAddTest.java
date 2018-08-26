package ui.cli;

import com.beust.jcommander.JCommander;
import logic.db.SqliteDB;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandAddTest {

    private CommandAdd commandAdd;
    private JCommander jCommander;

    @BeforeEach
    void setUp() {
        SqliteDB db = new SqliteDB();
        this.commandAdd = new CommandAdd(db);
        this.jCommander = JCommander.newBuilder()
                .addCommand("add", this.commandAdd)
                .build();
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
}