package com.io.mountblue;

public class Delivery {
    private String id; // 0
    private String bowlingTeam; // 3
    private int extraRuns; // 16
    private String bowler; // 8
    private int runsConceded; // 17
    private String batsman;// 7
    private int runsScoredByBatsman;// 15

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBowlingTeam() {
        return bowlingTeam;
    }

    public void setBowlingTeam(String bowlingTeam) {
        this.bowlingTeam = bowlingTeam;
    }

    public int getExtraRuns() {
        return extraRuns;
    }

    public void setExtraRuns(int extraRuns) {
        this.extraRuns = extraRuns;
    }

    public String getBowler() {
        return bowler;
    }

    public void setBowler(String bowler) {
        this.bowler = bowler;
    }

    public int getRunsConceded() {
        return runsConceded;
    }

    public void setRunsConceded(int runsConceded) {
        this.runsConceded = runsConceded;
    }

    public String getBatsman() {
        return batsman;
    }

    public void setBatsman(String batsman) {
        this.batsman = batsman;
    }

    public int getRunsScoredByBatsman() {
        return runsScoredByBatsman;
    }

    public void setRunsScoredByBatsman(int runsScoredByBatsman) {
        this.runsScoredByBatsman = runsScoredByBatsman;
    }

}
