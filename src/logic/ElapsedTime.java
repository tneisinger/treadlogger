package logic;

public class ElapsedTime {

    private int minutes;

    public ElapsedTime(int hours, int minutes) {
        this.minutes = hours*60 + minutes;
    }

    public ElapsedTime(int minutes) {
        this.minutes = minutes;
    }

    public int getMinutes() {
        return this.minutes;
    }

    public ElapsedTime plus(ElapsedTime otherElapsedTime) {
        return new ElapsedTime(this.minutes + otherElapsedTime.minutes);
    }

    @Override
    public String toString() {
        int days = this.minutes / (60*24);
        int hours = (this.minutes % (60*24)) / 60;
        int minutes = this.minutes % 60;

        String result = "";
        if (days > 0) {
            result += days + ":" + String.format("%02d", hours) + ":";
        } else {
            result += hours + ":";
        }
        result += String.format("%02d", minutes);

        return result;
    }

    public String toVerboseString() {
        int days = this.minutes / (60*24);
        int hours = (this.minutes % (60*24)) / 60;
        int minutes = this.minutes % 60;

        return String.format("%d days, %d hours, and %d minutes", days, hours, minutes);
    }

    public static ElapsedTime parse(String s) throws ElapsedTimeParseException {
        if (!(s.matches("\\d{1,2}:[0-5]\\d") || s.matches("\\d{1,2}"))) {
            throw new ElapsedTimeParseException("Cannot parse '" + s+ "' to an ElapsedTime.");
        }

        if (s.contains(":")) {
            String[] hoursAndMinutes = s.split(":");
            int hours = Integer.parseInt(hoursAndMinutes[0]);
            int minutes = Integer.parseInt(hoursAndMinutes[1]);
            return new ElapsedTime(hours, minutes);
        }

        return new ElapsedTime(Integer.parseInt(s));
    }
}
