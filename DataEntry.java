import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.List;
import java.util.ArrayList;

public class DataEntry {
    public final LocalDateTime date;
    public final double qualityValue;
    public final double voltage;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public DataEntry(LocalDateTime date, double qualityValue, double voltage) {
        this.date = date;
        this.qualityValue = qualityValue;
        this.voltage = voltage;
    }

    public List<String> format() { // Returns a formatted data line as {}
        List<String> formattedData = new ArrayList<>();
        String formattedDateTime = date.format(formatter);
        formattedData.add(formattedDateTime);
        formattedData.add(String.valueOf(qualityValue));
        formattedData.add(String.valueOf(voltage));
        return formattedData;
    }

    public LocalDateTime getDateTime() {
        return date;
    }
}