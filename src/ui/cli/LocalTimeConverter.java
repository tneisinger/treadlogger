package ui.cli;

import com.beust.jcommander.IStringConverter;

import java.time.DateTimeException;
import java.time.LocalTime;

public class LocalTimeConverter implements IStringConverter<LocalTime> {

    @Override
    public LocalTime convert(String s) {
        IllegalArgumentException exception =
                new IllegalArgumentException("Time was not properly formatted. (Proper format example: 3:45pm)");

        if (!s.toLowerCase().matches("\\d{1,2}:\\d{2}(a|p)m")) {
            throw exception;
        }

        String[] timeParts = s.toLowerCase().split(":");
        int hour = Integer.parseInt(timeParts[0]);
        int minute = Integer.parseInt(timeParts[1].substring(0,2));

        // If the input string is 12:01pm, the 'hour' var at this point in the code will equal 12.  Since the input
        // ends in "pm", the next line of code will add 12 to the hour to change the time into military time.
        // But 12 + 12 = 24, and the valid values for the hour parameter of LocalTime.of() are 0-23.
        // To fix this, we must set the hour variable to 0 if it is currently set to 12.
        if (hour == 12) hour = 0;

        // If the string ends with "pm", add 12 hours
        if (timeParts[1].substring(2,3).equals("p")) hour += 12;

        try {
            return LocalTime.of(hour, minute);
        } catch (DateTimeException e) {
            throw exception;
        }
    }
}
