package ui.cli;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import java.util.List;

@Parameters(commandDescription = "Add a treadmill data point")
public class CommandAdd {

    @Parameter(description = "The duration of time walked followed by the distance walked in miles")
    private List<String> timeAndDistance;

    public List<String> getTimeAndDistance() {
        return this.timeAndDistance;
    }
}
