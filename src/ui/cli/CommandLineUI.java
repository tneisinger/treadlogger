package ui.cli;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.MissingCommandException;

public class CommandLineUI {

    private CommandAdd commandAdd;
    private JCommander jCommander;

    public CommandLineUI(String[] args) {
        this.commandAdd = new CommandAdd();
        this.jCommander = JCommander.newBuilder()
                .addCommand("add", this.commandAdd)
                .build();

        // If an unrecognized command is parsed, handle the exception by informing the user
        try {
            this.jCommander.parse(args);
        } catch (MissingCommandException e) {
            System.out.println("An unrecognized treadlogger command was given. The valid commands are: ");
            for (String command : this.jCommander.getCommands().keySet()) {
                System.out.println("  " + command);
            }
        }
    }

    public void run() {
        // If no valid command was parsed, take no further action.
        if (this.jCommander.getParsedCommand() == null) {
            return;
        }

        System.out.println("The parsed command was: " + this.jCommander.getParsedCommand());

        String time = this.commandAdd.getTimeAndDistance().get(0);
        String distance = this.commandAdd.getTimeAndDistance().get(1);
        System.out.println("The time was: " + time);
        System.out.println("The distance was: " + distance);

    }
}
