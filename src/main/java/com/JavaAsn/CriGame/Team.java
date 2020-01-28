package com.JavaAsn.CriGame;

import java.util.ArrayList;

public class Team {
    private final String Tname;
    private int teamScore;
    private int teamWickets;
    private ArrayList<Player> teamPlayers;//list of the 11 players in the cricket team
    private final String tossResult;


    public Team(String tname, String tR) {
        Tname = tname;
        teamScore=0;
        teamWickets=0;
        tossResult = tR;
        teamPlayers= new ArrayList<>();
    }

    public String getTname() {
        return Tname;
    }

    public int getTeamScore() {
        return teamScore;
    }

    public int getTeamWickets() {
        return teamWickets;
    }

    public String getTossResult() {
        return tossResult;
    }

    public void updateTeamScore(int ts)
    {
        teamScore=ts;
    }

    public void updateTeamWickets(int tw)
    {
        teamWickets=tw;
    }

    //add a player to the end of the arraylist of players
    public void addPlayer (Player player)
    {
        teamPlayers.add(player);
    }

    //get the player at the ith index
    public Player getPlayer(int i)
    {
        return teamPlayers.get(i);
    }

    //return the arraylist of player objects
    public ArrayList<Player> getTeamPlayers() {
        return teamPlayers;
    }

}
