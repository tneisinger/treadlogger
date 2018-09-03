package ui.cli;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.MissingCommandException;
import com.beust.jcommander.ParameterException;
import logic.db.SqliteDB;

import java.sql.SQLException;

public class CommandLineUI {

    private CommandAdd commandAdd;
    private JCommander jCommander;
    private boolean preventRun;

    public CommandLineUI(String[] args) {

        SqliteDB db = new SqliteDB();
        this.commandAdd = new CommandAdd(db);
        this.jCommander = JCommander.newBuilder()
                .addCommand("add", this.commandAdd)
                .build();

        // Make jCommander case insensitive.
        this.jCommander.setCaseSensitiveOptions(false);

        // If an unrecognized command is parsed, handle the exception by informing the user
        try {
            this.jCommander.parse(args);
            this.preventRun = false;
        } catch (MissingCommandException e) {
            System.out.println("Error: An unrecognized command was given. The valid commands are: ");
            this.printCommands("    ");
            this.preventRun = true;
        } catch (IllegalArgumentException | ParameterException e) {
            System.out.println("Error: " + e.getMessage());
            this.preventRun = true;
        }
    }

    private void printCommands(String leaderString) {
        for (String command : this.jCommander.getCommands().keySet()) {
            System.out.println(leaderString + command);
        }
    }

    public void run() {
        // If the preventRun attribute is set to true, do nothing
        if (this.preventRun) {
            return;
        }

        // If no command was given, inform the user and do nothing else
        if (this.jCommander.getParsedCommand() == null) {
            System.out.println("Error: No command was given. The valid commands are: ");
            this.printCommands("    ");
            return;
        }

        String message = "";
        try {
            message = this.runParsedCommand();
        } catch (Exception e) {
            System.out.println("Error :" + e.getMessage());
            return;
        }
        System.out.println(message);
    }

    private String runParsedCommand() throws SQLException {
        String message = "";
        switch (this.jCommander.getParsedCommand()) {
            case "add":
                message = this.commandAdd.run();
                break;
        }
        return message;
    }
}
