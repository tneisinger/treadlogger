package ui.cli;

import com.beust.jcommander.Parameter;

import java.util.ArrayList;
import java.util.List;

public class ArgsObject {

    @Parameter
    private List<String> parameters = new ArrayList<>();

    public List<String> getParameters() {
        return this.parameters;
    }
}
