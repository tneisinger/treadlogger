package ui.cli;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.MissingCommandException;
import com.beust.jcommander.ParameterException;
import logic.ElapsedTimeFormatException;

import java.security.InvalidParameterException;
import java.time.DateTimeException;

public class CommandLineUI {

    private CommandAdd commandAdd;
    private JCommander jCommander;
    private boolean preventCommandRun;

    public CommandLineUI(String[] args) {
        this.commandAdd = new CommandAdd();
        this.jCommander = JCommander.newBuilder()
                .addCommand("add", this.commandAdd)
                .build();

        // If an unrecognized command is parsed, handle the exception by informing the user
        try {
            this.jCommander.parse(args);
            this.preventCommandRun = false;
        } catch (MissingCommandException e) {
            System.out.println("Error: An unrecognized command was given. The valid commands are: ");
            for (String command : this.jCommander.getCommands().keySet()) {
                System.out.println("    " + command);
            }
            this.preventCommandRun = true;
        } catch (InvalidParameterException | DateTimeException e) {
            System.out.println("Error: " + e.getMessage());
            this.preventCommandRun = true;
        }
    }

    public void run() {
        // If the preventCommandRun attribute is set to true, do nothing
        if (preventCommandRun) {
            return;
        }

        // Run the command the was parsed by JCommander
        switch (this.jCommander.getParsedCommand()) {
            case "add":
                try {
                    this.commandAdd.run();
                } catch (ElapsedTimeFormatException | ParameterException e) {
                    System.out.println("Error: " + e.getMessage());
                    break;
                }
        }
    }
}
