import java.io.FileReader;
import java.io.IOException;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.util.*;

public class IPL_Data_Analysis {
    public static void main(String[] args) throws IOException, CsvValidationException {

        String matchesCSV = "matches.csv";
        String ballsCSV = "deliveries.csv";

        List<String[]> matches = loadCsvData(matchesCSV);
        List<String[]> balls = loadCsvData(ballsCSV);

        // Task 1: Number of matches played per year
        Map<String, Integer> matchesPerYear = getMatchesPerYear(matches);

        // Task 2: Number of matches won by all teams
        Map<String, Integer> matchesWonByTeam = getMatchesWonByTeam(matches);

        // Task 3: Extra runs conceded per team in 2016
        Map<String, Integer> extraRunsConcededIn2016 = getExtraRunsConcededInYear(balls, matches, "2016");

        System.out.println("Task 1: Number of matches played per year");
        matchesPerYear.forEach((year, count) -> System.out.println(year + ": " + count));

        System.out.println("\nTask 2: Number of matches won by each team");
        matchesWonByTeam.forEach((team, count) -> System.out.println(team + ": " + count));

        System.out.println("\nTask 3: Extra runs conceded per team in 2016");
        extraRunsConcededIn2016.forEach((team, runs) -> System.out.println(team + ": " + runs));

    }

    private static List<String[]> loadCsvData(String csvFile) throws IOException, CsvValidationException {
        List<String[]> data = new ArrayList<>();

        CSVReader reader = new CSVReader(new FileReader(csvFile));
        String[] line;

        while ((line = reader.readNext()) != null) {
            data.add(line);
        }

        reader.close();
        return data;
    }

    // Task 1: Number of matches played per year
    private static Map<String, Integer> getMatchesPerYear(List<String[]> matchesData) {
        Map<String, Integer> matchesPerYear = new HashMap<>();
        for (String[] match : matchesData) {
            String year = match[1]; // Assuming year is in the second column
            matchesPerYear.put(year, matchesPerYear.getOrDefault(year, 0) + 1);
        }
        return matchesPerYear;
    }

    // Task 2: Number of matches won by all teams
    private static Map<String, Integer> getMatchesWonByTeam(List<String[]> matchesData) {
        Map<String, Integer> matchesWonByTeam = new HashMap<>();
        for (String[] match : matchesData) {
            String winner = match[10]; // Assuming winner team name is in the 11th column
            matchesWonByTeam.put(winner, matchesWonByTeam.getOrDefault(winner, 0) + 1);
        }
        return matchesWonByTeam;
    }

    // Task 3: Extra runs conceded per team in a given year
    private static Map<String, Integer> getExtraRunsConcededInYear(List<String[]> deliveriesData, List<String[]> matchesData, String year) {
        Map<String, Integer> extraRunsConceded = new HashMap<>();
        Map<String, String> id_to_year = new HashMap<>();

        for(String[] match : matchesData) {
            String match_id = match[0];
            id_to_year.put(match_id, match[1]);
        }

        for (String[] delivery : deliveriesData) {
            String matchYear = id_to_year.get(delivery[0]);
            // System.out.println(delivery[0] + "\t" + matchYear);
            if (year.equals(matchYear)) {
                String bowlingTeam = delivery[3]; // Bowling team name is in the fourth column
                // System.out.println(delivery[16]);
                int extraRuns = Integer.parseInt(delivery[16]); // Extra runs column
                extraRunsConceded.put(bowlingTeam,
                extraRunsConceded.getOrDefault(bowlingTeam, 0) + extraRuns);
            }
        }
        return extraRunsConceded;
    }

    

}