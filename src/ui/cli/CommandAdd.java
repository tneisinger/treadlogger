package ui.cli;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.beust.jcommander.Parameters;
import logic.ElapsedTime;
import logic.ElapsedTimeFormatException;

import java.util.List;

@Parameters(commandDescription = "Add a treadmill data point")
public class CommandAdd {

    @Parameter(description = "The duration of time walked followed by the distance walked in miles")
    private List<String> commandArgs;

    private ElapsedTime getElapsedTime() throws ElapsedTimeFormatException {
        // If no command arguments were given, throw an error
        if (this.commandArgs == null || this.commandArgs.isEmpty()) {
            throw new ParameterException("You did not provide any arguments to the 'add' command.");
        }

        return ElapsedTime.parse(this.commandArgs.get(0));
    }

   public void run() throws ElapsedTimeFormatException {
       ElapsedTime elapsedTime = this.getElapsedTime();
       System.out.println("Got this elapsed time: " + elapsedTime);
   }
}
