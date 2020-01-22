package com.JavaAsn.CriGame;

import java.util.ArrayList;

public class Team {
    private final String Tname;
    private int teamScore;
    private int teamWickets;
    private ArrayList<Player> teamPlayers;
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

    public void addPlayer (Player player)
    {
        teamPlayers.add(player);
    }

    public Player getPlayer(int i)
    {
        return teamPlayers.get(i);
    }

    public ArrayList<Player> getTeamPlayers() {
        return teamPlayers;
    }

}
