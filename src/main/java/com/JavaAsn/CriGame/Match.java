package com.JavaAsn.CriGame;
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.*;
public class Match {
    public Team teamA;
    public Team teamB;
    public char BowlSeq[] = new char[300];   //array depicting runs scored on each delivery until 10 wickets fall
    public char BowlOrder[] = new char[50];  //array showing the sequence of players who bowl each over
    public double plsrtings[]=new double[11];   //the player rating for the 11 players in the team
    private int[] playInning(boolean first, int prevScore) {
        Arrays.fill(BowlSeq,'\u0000');
        Arrays.fill(BowlOrder,'\u0000');

        //assigning player rating based off random() function in Math class
        for (int rti=0;rti<11;rti++)
        {
            plsrtings[rti]=Math.random()*10;
        }
        char ch;
        int score = 0, totalScore = 0, wickets = 0;
        int j = 0;
        int prIndex=0;

        //loop for 50 overs (if needed)
        for (int i = 0; i < 300; i++) {
            if (i % 6 == 0) // if 6 deliveries are bowled, determine the bowler to bowl the next over
            {
                BowlOrder[j] = (Integer.toString((int) (Math.random() * 11))).charAt(0);
                j++;
            }

            //pass a particular player rating to find runs hit on a ball by the player indexed by 'prIndex'
            ch = MatchController.randFunc(plsrtings[prIndex]);
            BowlSeq[i] = ch;
            if (ch == 'W')     //upon a wicket, update team wicket, and choose the next batsman
            {
                wickets++;
                totalScore += score;
                score = 0;
                prIndex+=1;
                if (wickets == 10) break;   //once 10 wickets fall, the Batting side's innings concludes
            }
            else
                {
                score += ch - 48;
                //if 2nd innings score exceeds the 1st innings score, match is over
                if (!first && (totalScore + score) > prevScore) break;
            }
        }
        totalScore += score;
        int[] res = new int[2];  //array to hold team score and wickets in a particular inning
        res[0] = totalScore;
        res[1] = wickets;
        return res;
    }

    private String getResult(int score1, int score2)  //compare the scores and determine the outcome
    {
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
        for (int i = 0; BowlSeq[i]!='\0'; i++)  //loop until end of sequence of deliveries is encountered
        {
            /*
            if delivery is not a wicket:
                check for a boundary or a six and do necessary updates
            if delivery is a wicket:
                the current player's time ends, and update fields of the player (and keep next player ready)
            */
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

        /*
        in each over bowled, determine:-
            -runs given by the bowler
            -maiden over or not
            -wickets taken
         */
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

        /*
        set the appropriate bowling fields from the arrays holding the bowling statistics
         */
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


        if (toss == 0)  //team A:bat,team B:bowl
        {
            teamAR = "Opting to Bat First";
            teamBR = "Opting to Bat Second";
        } else  //the other way around
            {
            teamBR = "Opting to Bat First";
            teamAR = "Opting to Bat Second";
        }

        //create the 2 teams
        teamA = new Team("Team A", teamAR);
        teamB = new Team("Team B", teamBR);

        //add 11 players to each team
        for (int i = 0; i < 11; i++) {
            teamA.addPlayer(new Player("A" + (i + 1)));
            teamB.addPlayer(new Player("B" + (i + 1)));
        }

        //play the 1st innings of the match
        int[] res = playInning(true, 0);

        if (toss == 0)      //if A bats first
        {   //update team A parameters
            teamA.updateTeamScore(res[0]);
            teamA.updateTeamWickets(res[1]);

            //set team A's player rating
            for(int pru=0;pru<11;pru++)
            {   //show score upto 1 decimal place
                (teamA.getPlayer(pru)).setPlayer_rating(Math.round(plsrtings[pru]*10.0)/10.0);
            }
            CalcStats(teamA,teamB);   // find batting stats of team A, bowling stats of team B
        }
        else
            {
            //update team B parameters
            teamB.updateTeamScore(res[0]);
            teamB.updateTeamWickets(res[1]);

            //set teaam B's player rating
            for(int pru=0;pru<11;pru++)
            {   //display score upto 1 decimal place
                (teamB.getPlayer(pru)).setPlayer_rating(Math.round(plsrtings[pru]*10.0)/10.0);
            }
            CalcStats(teamB,teamA);    // find batting stats of team B, bowling stats of team A
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

        //play the 2nd innings of the match
            int prevScore = res[0];
            res = playInning(false, prevScore);

            if (toss == 0)  //given A bats first, now team B's chance to bat
            {
                //update team B parameters
                teamB.updateTeamScore(res[0]);
                teamB.updateTeamWickets(res[1]);

                //set team B's player rating
                for(int pru=0;pru<11;pru++)
                {
                    (teamB.getPlayer(pru)).setPlayer_rating(Math.round(plsrtings[pru]*10.0)/10.0);
                }
                CalcStats(teamB,teamA);     //find batting stats of team B, bowling stats of team A
            } else {

                //update team B parameters
                teamA.updateTeamScore(res[0]);
                teamA.updateTeamWickets(res[1]);
                for(int pru=0;pru<11;pru++)
                {
                    (teamA.getPlayer(pru)).setPlayer_rating(Math.round(plsrtings[pru]*10.0)/10.0);
                }
                CalcStats(teamA,teamB);     //find batting stats of team A, bowling stats of team B
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
            result = getResult(teamA.getTeamScore(), teamB.getTeamScore());//determines who has won the match!

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


            return new cricketGame(teamA.getTname(), teamA.getTossResult(), teamA.getTeamScore() + "/" + teamA.getTeamWickets(), teamB.getTname(), teamB.getTossResult(), teamB.getTeamScore() + "/" + teamB.getTeamWickets(), result,teamA.getTeamPlayers(),teamB.getTeamPlayers());
        }
    }
