import java.util.List;
import java.util.Scanner;

import javax.swing.SwingUtilities;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
    
        Scanner scanner = new Scanner(System.in);

        String file = "C:/Users/RR/OneDrive/Code/Java/Air-Quality-Sensor-Visualization/util/SampleData.csv"; // Would change for different file location
        Import importedFile = new Import(file);
        List<DataEntry> dataList = importedFile.readFile();

        for (DataEntry line : dataList) {
            System.out.println("[" + String.join(", ", line.format()) + "]");
        }

        DataConfiguration dataConfiguration = new DataConfiguration(dataList);
        List<LocalDateTime> extremityDates = dataConfiguration.findDates();

        SwingUtilities.invokeLater(() -> new Visualization(extremityDates, dataConfiguration.timeScale(extremityDates)).setVisible(true));

        scanner.close();
    }
}
