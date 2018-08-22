package ui.cli;

import com.beust.jcommander.JCommander;

public class CommandLineUI {

    private ArgsObject argsObject;

    public CommandLineUI(String[] args) {
        this.argsObject = new ArgsObject();
        JCommander.newBuilder().addObject(argsObject).build().parse(args);
    }

    public void run() {
        System.out.println("Here are the parameters: ");
        for (String parameter : this.argsObject.getParameters()) {
            System.out.println("  " + parameter);
        }
    }
}
