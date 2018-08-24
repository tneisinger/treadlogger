package ui.cli;

import com.beust.jcommander.JCommander;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandAddTest {

    private CommandAdd commandAdd;
    private JCommander jCommander;

    @BeforeEach
    void setUp() {
        this.commandAdd = new CommandAdd();
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
}