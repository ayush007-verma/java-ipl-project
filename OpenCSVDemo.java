
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;

public class OpenCSVDemo {

    public static void main(String[] args) {
        // Define the path to the CSV file
        String csvFile = "deliveries.csv";

        try {
            // Create a CSVReader to read the CSV file
            CSVReader reader = new CSVReader(new FileReader(csvFile));

            // Read and print each row of the CSV file
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                // Each row is represented as an array of strings
                for (String cell : nextLine) {
                    System.out.print(cell + "\t"); // Print each cell separated by a tab
                }
                System.out.println(); // Move to the next line for the next row
            }

            // Close the CSVReader
            reader.close();
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }
}
