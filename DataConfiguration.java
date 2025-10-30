import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;

public class DataConfiguration {
    List<DataEntry> unconfiguredData;

    public DataConfiguration(List<DataEntry> unconfiguredData) {
        this.unconfiguredData = unconfiguredData;
    }

    public List<LocalDateTime> findDates() { // Start/end dates for slider
        List<LocalDateTime> extremityDates = new ArrayList<>();
        extremityDates.add(unconfiguredData.get(0).getDateTime());
        extremityDates.add(unconfiguredData.get(unconfiguredData.size() - 1).getDateTime());
        return extremityDates;
    }

    public double timeScale(List<LocalDateTime> extremityDates) {
        LocalDateTime start = extremityDates.get(0);
        LocalDateTime end = extremityDates.get(1);
        int secondChange = (end.getSecond() - start.getSecond()) % 60;
        boolean minuteCarry = (end.getSecond() < start.getSecond()) ? true : false; // Subtract an extra minute if it has to loop
        int minuteChange = (end.getMinute() - (minuteCarry ? start.getMinute() + 1 : start.getMinute())) % 60; // Add a minute to start if minuteCarry is true
        boolean hourCarry = (end.getMinute() < (minuteCarry ? start.getMinute() + 1 : start.getMinute())) ? true : false;
        int hourChange = (end.getHour() - (hourCarry ? start.getHour() + 1 : start.getHour())) % 24;
        boolean dayCarry = (end.getHour() < (hourCarry ? start.getHour() + 1 : start.getHour())) ? true : false;
        int dayChange = (end.getDayOfYear() - (dayCarry ? start.getDayOfYear() + 1 : start.getDayOfYear())) % 365;
        return (secondChange + (60 * minuteChange) + (3600 * hourChange) + (86400 * dayChange)) / 100; // Total amount of seconds elapsed from start to end, each tick is 1/100 of total change
    }
}
