package ui.cli;

import com.beust.jcommander.JCommander;
import logic.ElapsedTimeFormatException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandAddTest {

    @Test
    void run_unparseableElapsedTime() {
        CommandAdd commandAdd = new CommandAdd();
        JCommander jCommander = JCommander.newBuilder()
                .addCommand("add", commandAdd)
                .build();

        String[] args = new String[]{"add", "unparseable", "1.0"};
        jCommander.parse(args);

        assertThrows(Exception.class, commandAdd::run);
    }
}