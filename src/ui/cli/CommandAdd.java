package ui.cli;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import logic.ElapsedTime;
import logic.ElapsedTimeParseException;
import logic.db.SqliteDB;

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

    @Parameter(names = {"--includeTime", "-i"}, description = "include a log time when adding this data", arity = 1)
    private boolean includeTime = true;

    private SqliteDB db;

    public CommandAdd(SqliteDB db) {
        this.db = db;
    }

    private ElapsedTime parseElapsedTime() {
        // If no command arguments were given, throw an error
        if (this.commandArgs == null || this.commandArgs.isEmpty()) {
            throw new IllegalArgumentException("You did not provide any arguments to the 'add' command.");
        }

        try {
            return ElapsedTime.parse(this.commandArgs.get(0));
        } catch (ElapsedTimeParseException e) {
            throw new IllegalArgumentException("The first argument of 'add' is the time walked.  It must be an " +
                    "integer (minutes), or of the form 'hours:minutes' (2:31)");
        }
    }

    private double parseDistance() {
        // If a second argument wasn't given, throw an exception
        if (this.commandArgs == null || this.commandArgs.size() < 2) {
            throw new IllegalArgumentException("You did not provide a distance argument to the 'add' command.");
        }

        try {
            return Double.parseDouble(this.commandArgs.get(1));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("The second argument to the 'add' command must be a number " +
                    "representing the distance walked.");
        }
    }

   public void run() {
       // If the user gave a time but also set includeTime to false, throw an exception
       if (this.time != null && !this.includeTime) {
           throw new IllegalArgumentException("A time was given but includeTime was set to false. "
                   + "Your intentions are unclear. :(");
       }

       ElapsedTime elapsedTime = this.parseElapsedTime();
       double distance = this.parseDistance();
       System.out.println("Got this elapsed time: " + elapsedTime);
       System.out.println("Got this distance: " + distance);

       if (this.date != null) System.out.println("Got this date: " + this.date);
       if (this.time != null) System.out.println("Got this time: " + this.time);
       System.out.println("The value of includeTime: " + this.includeTime);

       if (this.date == null && this.time == null) {
           // If no date or time was given, insert the data using today's date.
           // If this.includeTime is set to true, insert the data using the current time.
           // If this.includeTime is set to false, insert the data without a time.
           this.db.insertData(elapsedTime, distance, this.includeTime);
       } else if (this.date != null && this.time == null) {
           // If a date was given, include it in the data.
           this.db.insertData(elapsedTime, distance, this.date, this.includeTime);
       } else if (this.date == null && this.time != null) {
           // If no date is given, but a time is given, insert the data using today's date
           this.db.insertData(elapsedTime, distance, LocalDate.now(), this.time);
       } else {
           // If a date and a time were given, insert the data with the given date and time
           this.db.insertData(elapsedTime, distance, this.date, this.time);
       }
   }
}
