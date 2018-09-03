package ui.cli;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import logic.ElapsedTime;
import logic.TreadDataPoint;
import logic.db.SqliteDB;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Parameters(commandDescription = "Print treadmill stats")
public class CommandPrintStats {

    @Parameter(names = {"--startDate", "-s"}, converter = LocalDateConverter.class)
    private LocalDate startDate;

    @Parameter(names = {"--endDate", "-e"}, converter = LocalDateConverter.class)
    private LocalDate endDate;

    private SqliteDB db;

    public CommandPrintStats(SqliteDB db) {
        this.db = db;
    }

    public String run() throws SQLException {
        if (this.startDate == null) this.startDate = LocalDate.of(2018, 1, 1);
        if (this.endDate == null) this.endDate = LocalDate.now();

        List<TreadDataPoint> dataPoints = this.db.getDataBetweenDates(this.startDate, this.endDate);

        ElapsedTime totalTime = new ElapsedTime(0);
        double totalDistance = 0;
        for (TreadDataPoint dataPoint : dataPoints) {
            totalTime = totalTime.plus(dataPoint.getElapsedTime());
            totalDistance += dataPoint.getDistance();
        }

        String message = String.format("Total time: %s\n", totalTime.toVerboseString());
        message += String.format("Total distance: %.2f miles", totalDistance);
        return message;
    }
}
