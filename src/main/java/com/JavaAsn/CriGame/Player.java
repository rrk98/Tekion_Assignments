package com.JavaAsn.CriGame;
public class Player {
    private String pName;

    //Bat Stats
    private int score;
    private int BallsPlayed=0;
    private int NBoundary=0;
    private int NSixes=0;

    //Bowl Stats
    private int OversB=0;
    private int RunsConceded=0;
    private int Wickets=0;
    private int OversM=0;

    /*
    Player Rating (on a scale of 10)
    In practice a player's rating can never be a perfect score
    */
    private double player_rating=0.0;

    public Player(String Name)
    {
        pName=Name;
        score=0;
    }

    //getter and setter methods for the various fields
    public String getpName() {
        return pName;
    }

    public void updateScore(int sc)
    {
        score=sc;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getBallsPlayed() {
        return BallsPlayed;
    }

    public void setBallsPlayed(int bp) {
        BallsPlayed = bp;
    }

    public int getNBoundary() {
        return NBoundary;
    }

    public void setNBoundary(int nb) {
        NBoundary = nb;
    }

    public int getNSixes() {
        return NSixes;
    }

    public void setNSixes(int NSixes) {
        this.NSixes = NSixes;
    }

    public int getOversB() {
        return OversB;
    }

    public void setOversB(int oversB) {
        OversB = oversB;
    }

    public int getRunsConceded() {
        return RunsConceded;
    }

    public void setRunsConceded(int runsConceded) {
        RunsConceded = runsConceded;
    }

    public int getWickets() {
        return Wickets;
    }

    public void setWickets(int wickets) {
        Wickets = wickets;
    }

    public int getOversM() {
        return OversM;
    }

    public void setOversM(int oversM) {
        OversM = oversM;
    }

    public double getPlayer_rating() {
        return player_rating;
    }

    public void setPlayer_rating(double player_rating) {
        this.player_rating = player_rating;
    }
}
