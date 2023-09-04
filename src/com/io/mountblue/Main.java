package com.io.mountblue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

    private static final String MATCHES_FILE_PATH = "C:/Users/GOD/Documents/mountblue/ipl-project/data/matches.csv";
    private static final String DELIVERIES_FILE_PATH = "C:/Users/GOD/Documents/mountblue/ipl-project/data/deliveries.csv";
    private static final String EXTRA_RUNS_QUERY_YEAR = "2016";
    private static final String ECONOMY_QUERY_YEAR = "2015";
    private static final String MOST_SIXES_QUERY_YEAR = "2016";
    private static final String MOST_STRIKE_QUERY_YEAR = "2012";

    public static void main(String[] args)  {
        List<Match> matches = addMatchesData();
        List<Delivery> deliveries = addDeliveriesData();
        Map<String, String> yearCorrespondingGivenId = new HashMap<>();

        for(Match match : matches) {
            yearCorrespondingGivenId.put(match.getId(), match.getSeason());
        }

        findNumberOfMatchesPlayedPerYear(matches);

        findNumberOfMatchesWonByEachTeam(matches);

        findExtraRunsConcededPerTeam(matches, deliveries, yearCorrespondingGivenId);

        findMostEconomicalBowlerGivenYear(matches, deliveries, yearCorrespondingGivenId);

        findMostStrikeRateBatsman(matches, deliveries, yearCorrespondingGivenId);
    }

    private static void findMostStrikeRateBatsman(List<Match> matches, List<Delivery> deliveries, Map<String, String> yearCorrespondingGivenId) {
        Map<String, Integer> totalRunsScoredGivenBatsman= new HashMap<>();
        Map<String, Integer> totalDeliveriesPlayed = new HashMap<>();
        List<Map.Entry<String, Double>> strikeRatePerBatsman = new ArrayList<>();

        for(Delivery delivery : deliveries) {
            String matchId = delivery.getId();
            String year = yearCorrespondingGivenId.get(matchId);
            if (year.equals(MOST_STRIKE_QUERY_YEAR)) {
                totalRunsScoredGivenBatsman.put(delivery.getBatsman(), totalRunsScoredGivenBatsman.getOrDefault(delivery.getBatsman(), 0) + delivery.getRunsScoredByBatsman());
                totalDeliveriesPlayed.put(delivery.getBatsman(), totalDeliveriesPlayed.getOrDefault(delivery.getBatsman(), 0) + 1);
            }
        }

        for(String batsman : totalRunsScoredGivenBatsman.keySet()) {
            int runsScored = totalRunsScoredGivenBatsman.get(batsman);
            int deliveriesPlayed = totalDeliveriesPlayed.get(batsman);
            double strikeRate = (double) runsScored / (double)deliveriesPlayed;
            strikeRate *= 100;
            strikeRatePerBatsman.add(Map.entry(batsman, strikeRate));
        }

        Collections.sort(strikeRatePerBatsman, Comparator.comparingDouble(Map.Entry :: getValue));

        System.out.println("\n Most Strike Rate By Batsman in"+ MOST_STRIKE_QUERY_YEAR + " : ");

        for(Map.Entry<String, Double> economyData : strikeRatePerBatsman) {
            System.out.println(economyData.getKey() + " : " + economyData.getValue());
        }
    }



    private static void findMostEconomicalBowlerGivenYear(List<Match> matches, List<Delivery> deliveries, Map<String, String> yearCorrespondingGivenId) {
        Map<String, Integer> totalRunsConcededGivenBowler = new HashMap<>();
        Map<String, Integer> totalDeliveriesBowled = new HashMap<>();
        List<Map.Entry<String, Double>> economyConcededGivenBowler = new ArrayList<>();

        for(Delivery delivery : deliveries) {
            String matchId = delivery.getId();
            String year = yearCorrespondingGivenId.get(matchId);
            if (year.equals(ECONOMY_QUERY_YEAR)) {
                totalRunsConcededGivenBowler.put(delivery.getBowler(), totalRunsConcededGivenBowler.getOrDefault(delivery.getBowler(), 0) + delivery.getRunsConceded());
                totalDeliveriesBowled.put(delivery.getBowler(), totalDeliveriesBowled.getOrDefault(delivery.getBowler(), 0) + 1);
            }
        }

        for(String bowler : totalRunsConcededGivenBowler.keySet()) {
            int runsConceded = totalRunsConcededGivenBowler.get(bowler);
            int deliveriesBowled = totalDeliveriesBowled.get(bowler);
            double oversBowled = deliveriesBowled / 6;
            double economy = (double) runsConceded / oversBowled;
            economyConcededGivenBowler.add(Map.entry(bowler, economy));
        }

        Collections.sort(economyConcededGivenBowler, Comparator.comparingDouble(Map.Entry :: getValue));

        System.out.println("\n Top Economical Bowlers of "+ ECONOMY_QUERY_YEAR + " : ");

        for(Map.Entry<String, Double> economyData : economyConcededGivenBowler) {
            System.out.println(economyData.getKey() + " : " + economyData.getValue());
        }
    }

    private static void findExtraRunsConcededPerTeam(List<Match> matches, List<Delivery> deliveries, Map<String, String> yearCorrespondingGivenId) {
        Map<String, Integer> extraRunsConcededPerTeam = new HashMap<>();


        for(Delivery delivery : deliveries) {
            String matchId = delivery.getId();
            String year = yearCorrespondingGivenId.get(matchId);

            if (year.equals(EXTRA_RUNS_QUERY_YEAR)) {
                extraRunsConcededPerTeam.put(delivery.getBowlingTeam(), extraRunsConcededPerTeam.getOrDefault(delivery.getBowlingTeam(), 0) + delivery.getExtraRuns());
            }
        }

        System.out.println("\nExtra Runs Conceded by Each Team in " + EXTRA_RUNS_QUERY_YEAR + " :");

        extraRunsConcededPerTeam.forEach((team, extraRuns) -> System.out.println(team + " : " + extraRuns));
    }

    private static void findNumberOfMatchesWonByEachTeam(List<Match> matches) {
        Map<String, Integer> matchesWonPerTeam = new HashMap<>();

        for(Match match : matches) {
            matchesWonPerTeam.put(match.getWinner(), matchesWonPerTeam.getOrDefault(match.getWinner(), 0) + 1);
        }

        System.out.println("\nNumber of matches won by each team : ");

        matchesWonPerTeam.forEach((team, count) -> System.out.println(team + " : " + count));
    }

    private static void findNumberOfMatchesPlayedPerYear(List<Match> matches) {
        Map<String, Integer> matchesPlayedPerYear = new HashMap<>();

        for(Match match : matches) {
            matchesPlayedPerYear.put(match.getSeason(), matchesPlayedPerYear.getOrDefault(match.getSeason(), 0) + 1);
        }

        System.out.println("\nNumber of matches played by each team : ");

        matchesPlayedPerYear.forEach((year, count) -> System.out.println(year + " : " + count));
    }

    private static List<Match> addMatchesData()  {
        List<Match> matches = new ArrayList<>();

        try{
            BufferedReader matchesData = new BufferedReader(new FileReader(MATCHES_FILE_PATH));
            String line;
            Boolean flag = false;

            while((line = matchesData.readLine()) != null ) {
                if(!flag) {
                    flag = true;
                    continue;
                }
                String[] data = line.split(",");

                Match match = new Match();
                match.setId(data[0]);
                match.setSeason(data[1]);
                match.setWinner(data[10]);
                match.setVenue(data[14]);

                matches.add(match);
            }
        } catch(IOException e) {
            e.printStackTrace();
        }

        return matches;
    }

    private static List<Delivery> addDeliveriesData() {
        List<Delivery> deliveries = new ArrayList<>();

        try{
            BufferedReader matchesData = new BufferedReader(new FileReader(DELIVERIES_FILE_PATH));
            String line;
            Boolean flag = false;

            while((line = matchesData.readLine()) != null ) {
                if(!flag) {
                    flag = true;
                    continue;
                }
                String[] data = line.split(",");

                Delivery delivery = new Delivery();
                delivery.setId(data[0]);
                delivery.setBowlingTeam(data[3]);
                int extraRuns = Integer.parseInt(data[16]);
                delivery.setExtraRuns(extraRuns);
                delivery.setBowler(data[8]);
                int runsConceded = Integer.parseInt(data[17]);
                delivery.setRunsConceded(runsConceded);
                delivery.setBatsman(data[7]);
                int runsScoredByBatsman = Integer.parseInt((data[15]));
                delivery.setRunsScoredByBatsman(runsScoredByBatsman);

                deliveries.add(delivery);
            }
        } catch(IOException e) {
            e.printStackTrace();
        }

        return deliveries;
    }
}
