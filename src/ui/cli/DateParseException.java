package ui.cli;

public class DateParseException extends Exception {
    public DateParseException() { super(); }
    public DateParseException(String message) { super(message); }
    public DateParseException(String message, Throwable cause) { super(message, cause); }
    public DateParseException(Throwable cause) { super(cause); }
}
