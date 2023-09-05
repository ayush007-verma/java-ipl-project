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
    private static final int MATCH_ID = 0;
    private static final int SEASON = 1;
    private static final int WINNER = 10;
    private static final int VENUE = 14;
    private static final int BOWLING_TEAM = 3;
    private static final int EXTRA_RUNS =  16;
    private static final int BOWLER = 8;
    private static final int RUNS_CONCEDED = 17;
    private static final int BATSMAN = 7;
    private static final int RUNS_SCORED_BY_BATSMAN  = 15;
    private static final int COUNT_TO_PRINT = 10;


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

        findMostSixesByBatsmanPerVenue(matches, deliveries, yearCorrespondingGivenId);

    }


    private static void findMostSixesByBatsmanPerVenue(List<Match> matches, List<Delivery> deliveries, Map<String, String> yearCorrespondingGivenId) {
        Map<String, String> venueCorrespondingId = new HashMap<>();

        Map<String, List<Map.Entry<String, Integer>>> batsmanSixesListCorrespondingVenue = new HashMap<>();

        for(Match match : matches) {
            venueCorrespondingId.put(match.getId(), match.getVenue());
        }

        for(Delivery delivery : deliveries) {
            String matchId = delivery.getId();
            String year = yearCorrespondingGivenId.get(matchId);
            if(year.equals(MOST_SIXES_QUERY_YEAR)) {
                // now we have to see these deliveries which a batsman and if it goes for six;
                int runsScoredByBatsman = delivery.getRunsScoredByBatsman();
                if(runsScoredByBatsman == 6) {
                    // store the venue accoridinly go with batsman-sixes map
                    String venue = venueCorrespondingId.get(matchId);
                    String batsman = delivery.getBatsman();


//                    batsmanSixesListCorrespondingVenue.put(venue, batsmanSixesListCorrespondingVenue.getOrDefault(venue, new ArrayList<>()).add(Map.entry(batsman, )));

                }
            }
        }

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
        Collections.reverse(strikeRatePerBatsman);

        System.out.println("\n Most Strike Rate By Batsman in"+ MOST_STRIKE_QUERY_YEAR + " : ");
        int countToPrint = COUNT_TO_PRINT;
        for(Map.Entry<String, Double> economyData : strikeRatePerBatsman) {
            if (countToPrint-- > 0) {
                System.out.println(economyData.getKey() + " : " + economyData.getValue());
            }
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
        int countToPrint = COUNT_TO_PRINT;

        for(Map.Entry<String, Double> economyData : economyConcededGivenBowler) {
            if (countToPrint-- > 0) {
                System.out.println(economyData.getKey() + " : " + economyData.getValue());
            }
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
                match.setId(data[MATCH_ID]);
                match.setSeason(data[SEASON]);
                match.setWinner(data[WINNER]);
                match.setVenue(data[VENUE]);

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
                delivery.setId(data[MATCH_ID]);
                delivery.setBowlingTeam(data[BOWLING_TEAM]);
                int extraRuns = Integer.parseInt(data[EXTRA_RUNS]);
                delivery.setExtraRuns(extraRuns);
                delivery.setBowler(data[BOWLER]);
                int runsConceded = Integer.parseInt(data[RUNS_CONCEDED]);
                delivery.setRunsConceded(runsConceded);
                delivery.setBatsman(data[BATSMAN]);
                int runsScoredByBatsman = Integer.parseInt((data[RUNS_SCORED_BY_BATSMAN]));
                delivery.setRunsScoredByBatsman(runsScoredByBatsman);

                deliveries.add(delivery);
            }
        } catch(IOException e) {
            e.printStackTrace();
        }

        return deliveries;
    }
}
