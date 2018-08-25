package ui.cli;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.MissingCommandException;

public class CommandLineUI {

    private CommandAdd commandAdd;
    private JCommander jCommander;
    private boolean preventRun;

    public CommandLineUI(String[] args) {
        this.commandAdd = new CommandAdd();
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
        } catch (IllegalArgumentException e) {
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

        // Run the command that was parsed by JCommander
        switch (this.jCommander.getParsedCommand()) {
            case "add":
                try {
                    this.commandAdd.run();
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                    break;
                }
        }
    }
}
