package ui.cli;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.beust.jcommander.Parameters;
import logic.ElapsedTime;
import logic.ElapsedTimeFormatException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Parameters(commandDescription = "Add a treadmill data point")
public class CommandAdd {

    @Parameter(description = "The duration of time walked followed by the distance walked in miles")
    private List<String> commandArgs;

    @Parameter(names = {"--date", "-d"}, converter = LocalDateConverter.class)
    private LocalDate date;

    @Parameter(names = {"--time", "-t"}, converter = LocalTimeConverter.class)
    private LocalTime time;

    @Parameter(names = {"--notime", "-n"}, description = "Do not include a log time when adding this data")
    private boolean noLogTime = false;

    private ElapsedTime getElapsedTime() throws ElapsedTimeFormatException {
        // If no command arguments were given, throw an error
        if (this.commandArgs == null || this.commandArgs.isEmpty()) {
            throw new ParameterException("You did not provide any arguments to the 'add' command.");
        }

        return ElapsedTime.parse(this.commandArgs.get(0));
    }

    private double getDistance() throws NumberFormatException {
        // If a second argument wasn't given, throw an exception
        if (this.commandArgs == null || this.commandArgs.size() < 2) {
            throw new ParameterException("You did not provide a distance argument to the 'add' command.");
        }
        return Double.parseDouble(this.commandArgs.get(1));
    }

   public void run() throws ElapsedTimeFormatException, ParameterException, NumberFormatException {
       ElapsedTime elapsedTime = this.getElapsedTime();
       double distance = this.getDistance();
       System.out.println("Got this elapsed time: " + elapsedTime);
       System.out.println("Got this distance: " + distance);

       if (this.date != null) System.out.println("Got this date: " + this.date);
       if (this.time != null) System.out.println("Got this time: " + this.time);
       System.out.println("The value of noLogTime was: " + this.noLogTime);
   }
}
