package com.example.Cricket;
import java.util.Random;

public class Match {
    public Team[] teams = new Team[2];
    public String toss,decide,winner,wonby,result;
    public Integer overs;
    public Match(){}
    public Match(Team A, Team B, Integer x)
    {
        teams[0] = A;
        teams[1] = B;
        overs = x;
    }

    public void Toss() {
        int ind = (int) (Math.random() * 2);
        toss = teams[ind].getName();
        int dec = (int) (Math.random() * 2);
        if (ind == 0 && dec == 0) decide = "bat first.";
        else if (ind == 0 && dec == 1) {
            decide = "field first.";
            Team ex = teams[0];
            teams[0] = teams[1];
            teams[1] = ex;
        } else if (ind == 1 && dec == 0) {
            decide = "bat first.";
            Team ex = teams[0];
            teams[0] = teams[1];
            teams[1] = ex;
        } else decide = "field first.";
    }

    public void StartGame(){

        //First Innings
        for(int i=0;i<overs;i++) {
            for(int j=0;j<6;j++) {
                teams[0].balls++;
                int rnd = (int) (Math.random() * 8);
                if (rnd == 7) teams[0].wickets++;
                else teams[0].runs += rnd;
                if (teams[0].wickets == 10) break;
            }
            if (teams[0].wickets == 10) break;
        }

        //Second Innings
        for(int i=0;i<overs;i++) {
            for (int j=0;j<6;j++) {
                teams[1].balls++;
                int rnd = (int) (Math.random() * 8);
                if (rnd == 7) teams[1].wickets++;
                else teams[1].runs += rnd;
                if (teams[1].wickets == 10) break;
                if (teams[1].runs > teams[0].runs) break;
            }
            if (teams[1].wickets == 10) break;
            if (teams[1].runs > teams[0].runs) break;
        }

        if(teams[1].runs == teams[0].runs){
            winner = "Tie";
        }
        else if(teams[1].runs > teams[0].runs) {
            winner = teams[1].getName();
            wonby = Integer.toString(10 - teams[1].wickets) + " wickets.";
        }
        else {
            winner = teams[0].getName();
            wonby = Integer.toString(teams[0].runs - teams[1].runs) + " runs.";
        }
        result = toss + " won the toss and decided to " + decide + "<br>";
        result = result + teams[0].getName() + " : " + Integer.toString(teams[0].runs) + "/" + Integer.toString(teams[0].wickets) + " (" + Integer.toString(teams[0].balls/6) + "." + Integer.toString(teams[0].balls%6) + " overs)<br>";
        result = result + teams[1].getName() + " : " + Integer.toString(teams[1].runs) + "/" + Integer.toString(teams[1].wickets) + " (" + Integer.toString(teams[1].balls/6) + "." + Integer.toString(teams[1].balls%6) + " overs)<br>";
        if(winner=="Tie")result = result + "Match ended in a tie. <br>";
        else result = result + winner + " won by " + wonby + "<br>";
        result = result + "<br>";
        result = result + teams[0].toString();
        result = result + "<br>";
        result = result + teams[1].toString();
    }
}
