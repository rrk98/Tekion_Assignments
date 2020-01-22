package com.JavaAsn.CriGame;
import java.util.ArrayList;
import java.util.Arrays;
//import java.lang.*;
public class Match {
    public Team teamA;
    public Team teamB;
    public char BowlSeq[] = new char[300];
    public char BowlOrder[] = new char[50];

    private int[] playInning(boolean first, int prevScore) {
        Arrays.fill(BowlSeq,'\u0000');
        Arrays.fill(BowlOrder,'\u0000');
        char ch;
        int score = 0, totalScore = 0, wickets = 0;
        int j = 0;
        for (int i = 0; i < 300; i++) {
            if (i % 6 == 0) {
                BowlOrder[j] = (Integer.toString((int) (Math.random() * 11))).charAt(0);
                j++;
            }
            ch = MatchController.randFunc();
            BowlSeq[i] = ch;
            if (ch == 'W') {
                wickets++;
                totalScore += score;
                score = 0;
                if (wickets == 10) break;
            } else {
                score += ch - 48;
                if (!first && (totalScore + score) > prevScore) break;
            }
        }
        totalScore += score;
        int[] res = new int[2];
        res[0] = totalScore;
        res[1] = wickets;
        return res;
    }

    private String getResult(int score1, int score2) {
        if (teamA.getTeamScore() > teamB.getTeamScore()) {
            return "Team A defeated Team B";
        } else if (teamA.getTeamScore() < teamB.getTeamScore()) {
            return "Team B defeated Team A";
        } else {
            return "game is drawn";
        }
    }

    private void CalcStats(Team t1,Team t2)
    {   //Current Batting Side
        int nbp = 0, nB = 0, nS = 0, bmanNo = 0,scre=0;
        for (int i = 0; BowlSeq[i]!='\0'; i++)
        {
            if (!(BowlSeq[i] == 'W'))
            {
                nbp++;
                if (BowlSeq[i] == '4') {
                    nB++;
                }
                if (BowlSeq[i] == '6') {
                    nS++;
                }
                scre+=Character.getNumericValue(BowlSeq[i]);
            }

            if (BowlSeq[i] == 'W') {
                nbp+= 1;
                Player p=t1.getPlayer(bmanNo);
                p.setScore(scre);
                p.setBallsPlayed(nbp);
                p.setNBoundary(nB);
                p.setNSixes(nS);
                nbp=nB=nS=scre=0;
                bmanNo+=1;
            }
        }

        //Current Bowling Side
        int []Novrs=new int[11];
        int []rg=new int[11];
        int []wtaken=new int[11];
        int []maidenovrs=new int[11];
        for(int q=0;BowlOrder[q]!='\0';q++)
        {   char []temp;
            int sum=0;
            int q1=Character.getNumericValue(BowlOrder[q]);
            Novrs[q1]++;
            temp=Arrays.copyOfRange(BowlSeq,6*q,6*q+6);
            for(int t=0;t<6 && temp[t]!='\0';t++)
            {
              if(temp[t]=='W')
                  wtaken[q1]+=1;
              else
              {
                  sum+=Character.getNumericValue(temp[t]);
              }

            }
            if(sum==0)
                maidenovrs[q1]+=1;
            else
                rg[q1]+=sum;
        }
        for(int idx=0;idx<11;idx++)
        {
            Player pyr=t2.getPlayer(idx);
            pyr.setOversB(Novrs[idx]);
            pyr.setRunsConceded(rg[idx]);
            pyr.setWickets(wtaken[idx]);
            pyr.setOversM(maidenovrs[idx]);
        }

    }


    public cricketGame startMatch() {
        int toss = (int) (Math.random() * 2);

        String teamAR, teamBR;
        String result;
        if (toss == 0) {
            teamAR = "Opting to Bat First";
            teamBR = "Opting to Bat Second";
        } else {
            teamBR = "Opting to Bat First";
            teamAR = "Opting to Bat Second";
        }


        teamA = new Team("Team A", teamAR);
        teamB = new Team("Team B", teamBR);

        for (int i = 0; i < 11; i++) {
            teamA.addPlayer(new Player("A" + (i + 1)));
            teamB.addPlayer(new Player("B" + (i + 1)));
        }

        int[] res = playInning(true, 0);

        if (toss == 0) {
            teamA.updateTeamScore(res[0]);
            teamA.updateTeamWickets(res[1]);
            CalcStats(teamA,teamB);
        } else {
            teamB.updateTeamScore(res[0]);
            teamB.updateTeamWickets(res[1]);
            CalcStats(teamB,teamA);
        }
        /*
        //print runs scored on each ball and sequence of players bowling the overs for 1st Innings
        for(int i=0;BowlSeq[i]!='\0';i++)
        {
            System.out.print(BowlSeq[i]+" ");
        }
        System.out.println();
        for(int j=0;BowlOrder[j]!='\0';j++)
        {
            System.out.print(BowlOrder[j]+" ");
        }
        System.out.println();
        */
            int prevScore = res[0];
            res = playInning(false, prevScore);

            if (toss == 0) {
                teamB.updateTeamScore(res[0]);
                teamB.updateTeamWickets(res[1]);
                CalcStats(teamB,teamA);
            } else {
                teamA.updateTeamScore(res[0]);
                teamA.updateTeamWickets(res[1]);
                CalcStats(teamA,teamB);
            }

            /*
            //print runs scored on each ball and sequence of players bowling the overs for 2nd Innings
            for (int k = 0; BowlSeq[k]!='\0'; k++) {
                System.out.print(BowlSeq[k] + " ");
            }
            System.out.println();
            for (int j = 0; BowlOrder[j]!='\0'; j++) {
                System.out.print(BowlOrder[j] + " ");
            }
            System.out.println();

            */
            result = getResult(teamA.getTeamScore(), teamB.getTeamScore());

            /*
            for(int itr=0;itr<11;itr++)
            {   //Printing Team A Data:
                Player pr=teamA.getPlayer(itr);
                System.out.println(pr.getScore());
                System.out.println(pr.getBallsPlayed());
                System.out.println(pr.getNBoundary());
                System.out.println(pr.getNSixes());
                System.out.println();
                System.out.println(pr.getOversB());
                System.out.println(pr.getRunsConceded());
                System.out.println(pr.getWickets());
                System.out.println(pr.getOversM());
                System.out.println();

            }
            for (int itr1=0;itr1<11;itr1++)
            {
                //Printing Team B Data:
                Player pr1=teamB.getPlayer(itr1);
                System.out.println(pr1.getScore());
                System.out.println(pr1.getBallsPlayed());
                System.out.println(pr1.getNBoundary());
                System.out.println(pr1.getNSixes());
                System.out.println();
                System.out.println(pr1.getOversB());
                System.out.println(pr1.getRunsConceded());
                System.out.println(pr1.getWickets());
                System.out.println(pr1.getOversM());
            }
            */

        //return object for printing onto localhost
            return new cricketGame(teamA.getTname(), teamA.getTossResult(), teamA.getTeamScore() + "/" + teamA.getTeamWickets(), teamB.getTname(), teamB.getTossResult(), teamB.getTeamScore() + "/" + teamB.getTeamWickets(), result,teamA.getTeamPlayers(),teamB.getTeamPlayers());
        }
    }
