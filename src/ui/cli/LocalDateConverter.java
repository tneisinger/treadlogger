package ui.cli;

import com.beust.jcommander.IStringConverter;

import java.time.LocalDate;

public class LocalDateConverter implements IStringConverter<LocalDate> {

    private final int MIN_YEAR = 2018;
    private final int MAX_YEAR = 2024;

    @Override
    public LocalDate convert(String s) {
        if (!s.matches("\\d{1,2}/\\d{1,2}/\\d{2}")) {
            throw new IllegalArgumentException("The date you provided was not formatted properly (mm/dd/yy)");
        }

        String[] dateParts = s.split("/");
        int month = Integer.parseInt(dateParts[0]);
        int day = Integer.parseInt(dateParts[1]);
        int year = 2000 + Integer.parseInt(dateParts[2]);

        if (year < MIN_YEAR) {
            throw new IllegalArgumentException("The date you gave was too far in the past.");
        }

        if (year > MAX_YEAR) {
            throw new IllegalArgumentException("The date you gave was too far in the future.");
        }

        return LocalDate.of(year, month, day);
    }
}
