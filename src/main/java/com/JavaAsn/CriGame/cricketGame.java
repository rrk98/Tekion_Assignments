package com.JavaAsn.CriGame;
import java.util.*;

public class cricketGame {
    private final String Team_Name_1;
    private final String teamA_Toss;
    private final String teamA_Score;
    private final String Team_Name_2;
    private final String teamB_Toss;
    private final String teamB_Score;
    private final String result;
    public List <Player> TEAM_1P=new ArrayList<>(Collections.nCopies(11,null));
    public List <Player> TEAM_2P=new ArrayList<>(Collections.nCopies(11,null));


    public cricketGame(String tAName, String tARes, String tAScore, String tBName, String tBRes, String tBScore, String res,ArrayList<Player> tAP,ArrayList<Player> tBP)
    {
        Team_Name_1 = tAName;
        teamA_Toss = tARes;
        teamA_Score = tAScore;
        Collections.copy(TEAM_1P,tAP);

        Team_Name_2 = tBName;
        teamB_Toss = tBRes;
        teamB_Score = tBScore;
        result = res;
        Collections.copy(TEAM_2P,tBP);
    }

    public String getTeam_Name_1() {
        return Team_Name_1;
    }

    public String getTeamA_Toss() {
        return teamA_Toss;
    }

    public String getTeamA_Score() {
        return teamA_Score;
    }

    public String getTeam_Name_2() {
        return Team_Name_2;
    }

    public String getTeamB_Toss() {
        return teamB_Toss;
    }

    public String getTeamB_Score() {
        return teamB_Score;
    }

    public String getResult() {
        return result;
    }

}
