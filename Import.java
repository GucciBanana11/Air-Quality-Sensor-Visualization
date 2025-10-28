import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDateTime;

import java.io.File;
import java.io.FileNotFoundException;

public class Import {

    File file;
    List<DataEntry> dataList;

    public Import(String fileName) {
        file = new File(fileName);
        dataList = new ArrayList<>();
    }

    public List<DataEntry> readFile() {
        System.out.println("File exists: " + file.exists());
        System.out.println("Can read: " + file.canRead());
        System.out.println("File length: " + file.length());
        try {
            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNextLine()) { // While there is more data
                String line = fileScanner.nextLine();
                String[] lineData = line.split(","); // Split the csv file, format for lineData is {yyyy-mm-dd hh:mm:ss, [analogValue], [voltage]} 
                String[] dateString = lineData[0].split(" ")[0].split("-"); 
                int[] date = Arrays.stream(dateString).map(s -> s.replaceAll("\"", "").trim()).mapToInt(Integer::parseInt).toArray();
                String[] timeString = lineData[0].split(" ")[1].split(":");
                int[] time = Arrays.stream(timeString).map(s -> s.replaceAll("\"", "").trim()).mapToInt(Integer::parseInt).toArray();
                LocalDateTime dateTime = LocalDateTime.of(date[0], date[1], date[2], time[0], time[1]); // Make a new LocalDateTime object with the date from data
                double analogValue = Double.parseDouble(lineData[1].replace("\"", ""));
                double voltage = Double.parseDouble(lineData[2].replace("\"", ""));
                DataEntry dataEntry = new DataEntry(dateTime, analogValue, voltage);
                dataList.add(dataEntry); // Add the entry to the data list
            }

            fileScanner.close();

        } catch (FileNotFoundException fileNotFound) {
            fileNotFound.printStackTrace();
        }

        return dataList;

    }
}
