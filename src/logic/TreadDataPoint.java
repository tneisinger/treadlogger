package logic;

import java.time.LocalDate;

public class TreadDataPoint {

    private ElapsedTime elapsedTime;
    private double distance;
    private LocalDate date;

    public TreadDataPoint(int minutes, double distance, String dateString) {
        this.elapsedTime = new ElapsedTime(minutes);
        this.distance = distance;
        this.date = LocalDate.parse(dateString);
    }

    public ElapsedTime getElapsedTime() {
        return elapsedTime;
    }

    public double getDistance() {
        return distance;
    }

    @Override
    public String toString() {
        return "TreadDataPoint{" +
                "elapsedTime=" + elapsedTime +
                ", distance=" + distance +
                ", date=" + date +
                '}';
    }
}
