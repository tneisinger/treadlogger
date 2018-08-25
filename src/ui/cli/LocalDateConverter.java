package ui.cli;

import com.beust.jcommander.IStringConverter;

import java.security.InvalidParameterException;
import java.time.DateTimeException;
import java.time.LocalDate;

public class LocalDateConverter implements IStringConverter<LocalDate> {

    private final int MIN_YEAR = 2018;
    private final int MAX_YEAR = 2024;

    @Override
    public LocalDate convert(String s) throws DateTimeException, InvalidParameterException {
        if (!s.matches("\\d{1,2}/\\d{1,2}/\\d{2}")) {
            throw new InvalidParameterException("The date you provided was not formatted properly (mm/dd/yy)");
        }

        String[] dateParts = s.split("/");
        int month = Integer.parseInt(dateParts[0]);
        int day = Integer.parseInt(dateParts[1]);
        int year = 2000 + Integer.parseInt(dateParts[2]);

        if (year < MIN_YEAR) {
            throw new InvalidParameterException("The date you gave was too far in the past.");
        }

        if (year > MAX_YEAR) {
            throw new InvalidParameterException("The date you gave was too far in the future.");
        }

        return LocalDate.of(year, month, day);
    }
}
