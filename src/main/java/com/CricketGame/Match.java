package com.CricketGame;

import com.CricketGame.Records.MatchRecord;
import com.CricketGame.TeamPackage.Player;
import com.CricketGame.TeamPackage.Team;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"striker","nonstriker","bowler","result","index","team_last","player_last"})

public class Match
{
    public enum Result{Won, Lost, Tie};
    public Team[] teams = new Team[2];
    public String toss,decide,winner,wonby;
    public Integer overs;
    public MatchRecord matchRecord = new MatchRecord();

    //Helper Variables
    Player striker,nonstriker,bowler;
    public String result;
    public Integer index=0;
    public Integer team_last[] = new Integer[2];
    public Integer player_last[][] = new Integer[2][11];


    //Constructor
    public Match(Team A, Team B, Integer x)
    {
        teams[0] = A;
        teams[1] = B;
        overs = x;
        teams[0].addNewRecords(teams[1].name);
        teams[1].addNewRecords(teams[0].name);
    }

    public void Toss()
    {
        int ind = (int) (Math.random() * 2);            //Random Toss
        toss = teams[ind].getName();

        teams[ind].teamRecords.get(teams[ind].teamRecords.size()-1).toss = true;  //Update Toss Statistics

        int dec = (int) (Math.random() * 2);            //Random Decision
        if (ind == 0 && dec == 0) decide = "bat first.";
        else if (ind == 0 && dec == 1) {
            decide = "field first.";
            Team swap = teams[0];teams[0] = teams[1];teams[1] = swap;
        }
        else if (ind == 1 && dec == 0) {
            decide = "bat first.";
            Team swap = teams[0];teams[0] = teams[1];teams[1] = swap;
        }
        else decide = "field first.";

        updateLast(); //Helper Variables Update
    }

    public Integer simulateBall(int id)
    {
        Integer runs = 0;
        teams[id].teamRecords.get(team_last[id]).balls_faced++;
        teams[1-id].teamRecords.get(team_last[1-id]).balls_bowled++;
        striker.playerRecords.get(striker.playerRecords.size()-1).balls_faced++;
        bowler.playerRecords.get(bowler.playerRecords.size()-1).balls_bowled++;
        int rnd = (int) (Math.random() * 8);
        if (rnd == 7)       //Wicket
        {
            teams[id].teamRecords.get(team_last[id]).wickets_lost++;
            teams[1-id].teamRecords.get(team_last[1-id]).wickets_taken++;
            bowler.playerRecords.get(bowler.playerRecords.size()-1).wickets_taken++;
            if(index<11)striker = teams[id].players[index++];
            else striker = null;
        }
        else                //Runs
        {
            runs = rnd;
            teams[id].teamRecords.get(team_last[id]).runs_scored+=rnd;
            teams[1-id].teamRecords.get(team_last[1-id]).runs_conceded+=rnd;
            bowler.playerRecords.get(bowler.playerRecords.size()-1).runs_conceded+=rnd;
            striker.playerRecords.get(striker.playerRecords.size()-1).runs_scored+=rnd;
            if(rnd==4)      //Four
            {
                striker.playerRecords.get(striker.playerRecords.size()-1).sixes++;
                teams[id].teamRecords.get(team_last[id]).sixes++;
            }
            if(rnd==6)      //Six
            {
                striker.playerRecords.get(striker.playerRecords.size()-1).fours++;
                teams[id].teamRecords.get(team_last[id]).fours++;
            }
            if(rnd%2 == 1)  //Strike Change
            {
                Player swap = striker;
                striker = nonstriker;
                nonstriker = swap;
            }
        }
        return runs;
    }

    public void Innings(int id)
    {
        Integer target = teams[1-id].teamRecords.get(team_last[1-id]).runs_scored + 1;
        index = 0;          //Batting Order Index
        striker = teams[id].players[index++];
        nonstriker = teams[id].players[index++];
        for(int i=0;i<overs;i++)
        {
            int rnd = 5 + (int) (Math.random()*6);
            int over_runs = 0, j=0;
            bowler = teams[1-id].players[rnd];
            for(j=0;j<6;j++)
            {
                over_runs += simulateBall(id);
                if(id==1) if(teams[id].teamRecords.get(team_last[id]).runs_scored >= target)break;
                if (teams[id].teamRecords.get(team_last[id]).wickets_lost == 10) break;
            }
            if(over_runs == 0  && j==6) bowler.playerRecords.get(bowler.playerRecords.size()-1).maidens++;
            if (teams[id].teamRecords.get(team_last[id]).wickets_lost == 10) break;
            Player swap = striker;
            striker = nonstriker;
            nonstriker = swap;
        }
        for(int i=0;i<11;i++)       //Update NotOut Details
        {
            if(teams[id].players[i] == striker)teams[id].players[i].playerRecords.get(player_last[id][i]).notout = true;
            if(teams[id].players[i] == nonstriker)teams[id].players[i].playerRecords.get(player_last[id][i]).notout = true;
        }
    }

    public void StartGame()
    {
        //Play Match
        Innings(0);
        Innings(1);

        //Sr and E update
        for(int i=0;i<11;i++)teams[1].players[i].playerRecords.get(player_last[1][i]).finish();
        for(int i=0;i<11;i++)teams[0].players[i].playerRecords.get(player_last[0][i]).finish();

        //RR Update
        teams[0].teamRecords.get(team_last[0]).finish();
        teams[1].teamRecords.get(team_last[1]).finish();

        //Insert Player Records in Team Records
        for(int i=0;i<11;i++) {
            teams[0].teamRecords.get(team_last[0]).playerRecord[i] = teams[0].players[i].playerRecords.get(player_last[0][i]);
            teams[1].teamRecords.get(team_last[1]).playerRecord[i] = teams[1].players[i].playerRecords.get(player_last[1][i]);
        }

        //Setup Match Records
        matchRecord.teamRecord[0] = teams[0].teamRecords.get(team_last[0]);
        matchRecord.teamRecord[1] = teams[1].teamRecords.get(team_last[1]);

        setupResult();
        getHTMLString();
    }


    //Helper Functions
    void updateLast()
    {
        team_last[0] = teams[0].teamRecords.size() - 1;
        team_last[1] = teams[1].teamRecords.size() - 1;
        for(int i=0;i<11;i++)
        {
            player_last[0][i] = teams[0].players[i].playerRecords.size()-1;
            player_last[1][i] = teams[1].players[i].playerRecords.size()-1;
        }
        teams[0].teamRecords.get(team_last[0]).against = teams[1].getName();
        teams[1].teamRecords.get(team_last[1]).against = teams[0].getName();
    }

    void setupResult()
    {
        if(teams[1].teamRecords.get(team_last[1]).runs_scored == teams[0].teamRecords.get(team_last[0]).runs_scored)
        {
            winner = "Tie";
            teams[0].teamRecords.get(team_last[0]).result = Result.Tie;
            teams[1].teamRecords.get(team_last[1]).result = Result.Tie;
        }
        else if(teams[1].teamRecords.get(team_last[1]).runs_scored > teams[0].teamRecords.get(team_last[0]).runs_scored)
        {
            winner = teams[1].getName();
            wonby = Integer.toString(10 - teams[1].teamRecords.get(team_last[1]).wickets_lost) + " wickets.";
            teams[0].teamRecords.get(team_last[0]).result = Result.Lost;
            teams[1].teamRecords.get(team_last[1]).result = Result.Won;
        }
        else
        {
            winner = teams[0].getName();
            wonby = Integer.toString(teams[0].teamRecords.get(team_last[0]).runs_scored -
                    teams[1].teamRecords.get(team_last[1]).runs_scored) + " runs.";
            teams[1].teamRecords.get(team_last[1]).result = Result.Lost;
            teams[0].teamRecords.get(team_last[0]).result = Result.Won;
        }
    }

    void getHTMLString()
    {

        //Header
        result = "<h2> " + teams[0].getName() + " vs " + teams[1].getName() + "</h2>";

        //Result
        result = result + toss + " won the toss and decided to " + decide + "<br> <br> ";
        result = result + teams[0].getName() + " : " +
                Integer.toString(teams[0].teamRecords.get(team_last[0]).runs_scored) +
                "/" + Integer.toString(teams[0].teamRecords.get(team_last[0]).wickets_lost) +
                " (" + Integer.toString(teams[0].teamRecords.get(team_last[0]).balls_faced/6) +
                "." + Integer.toString(teams[0].teamRecords.get(team_last[0]).balls_faced%6) + " overs)<br>";
        result = result + teams[1].getName() + " : " +
                Integer.toString(teams[1].teamRecords.get(team_last[1]).runs_scored) +
                "/" + Integer.toString(teams[1].teamRecords.get(team_last[1]).wickets_lost) +
                " (" + Integer.toString(teams[1].teamRecords.get(team_last[1]).balls_faced/6) +
                "." + Integer.toString(teams[1].teamRecords.get(team_last[1]).balls_faced%6) + " overs)<br>";
        if(winner=="Tie")result = result + "Match ended in a tie. <br>";
        else result = result + winner + " won by " + wonby + "<br>";

        //Scorecard
        result = result + "<style>\n" +
                "th, td {\n" +
                "  border: 1px solid grey;\n" +
                "}\n" +
                "table {\n" +
                "  border: 1px solid black;\n" +
                "}\n" +
                "</style>";
        result = result + "<div style = 'width: 50%'>";
        result = result + "<h3>" + teams[0].getName() + "'s Innings" + "</h3>";
        result = result + "<table style='text-align : center; width : 100%;padding: 8px;'>\n" +
                "  <tr>\n" +
                "    <th>Batsman</th>\n" +
                "    <th>Runs</th>\n" +
                "    <th>Balls</th>\n" +
                "    <th>4s</th>\n" +
                "    <th>6s</th>\n" +
                "    <th>Strike Rate</th>\n" +
                "  </tr>\n";
        result = result + teams[0].batting_scorecard(team_last[0]);
        result = result + "</table>\n" ;
        result = result + "<table style='text-align : center; width : 100%;padding: 8px;'>\n" +
                "  <tr>\n" +
                "    <th>Bowler</th>\n" +
                "    <th>Overs</th>\n" +
                "    <th>Runs</th>\n" +
                "    <th>Wickets</th>\n" +
                "    <th>Maiden</th>\n" +
                "    <th>Economy</th>\n" +
                "  </tr>\n";
        result = result + teams[1].bowling_scorecard(team_last[1]);
        result = result + "</table>\n" ;
        result = result + "<br> <br>";
        result = result + "<h3>" + teams[1].getName() + "'s Innings" + "</h3>";
        result = result + "<table style='text-align : center; width : 100%;padding: 8px;'>\n" +
                "  <tr>\n" +
                "    <th>Batsman</th>\n" +
                "    <th>Runs</th>\n" +
                "    <th>Balls</th>\n" +
                "    <th>4s</th>\n" +
                "    <th>6s</th>\n" +
                "    <th>Strike Rate</th>\n" +
                "  </tr>\n";
        result = result + teams[1].batting_scorecard(team_last[1]);
        result = result + "</table>\n" ;
        result = result + "<table style='text-align : center; width : 100%;padding: 8px;'>\n" +
                "  <tr>\n" +
                "    <th>Bowler</th>\n" +
                "    <th>Overs</th>\n" +
                "    <th>Runs</th>\n" +
                "    <th>Wickets</th>\n" +
                "    <th>Maiden</th>\n" +
                "    <th>Economy</th>\n" +
                "  </tr>\n";
        result = result + teams[0].bowling_scorecard(team_last[0]);
        result = result + "</table>\n" ;
        result = result + "</div>\n" ;
        result = result + "<br> <br>";
    }

    //Getters
    public MatchRecord getmatchRecord() {
        return matchRecord;
    }

    public Team[] getTeams() {
        return teams;
    }

    public String getToss() {
        return toss;
    }

    public String getDecide() {
        return decide;
    }

    public String getWinner() {
        return winner;
    }

    public String getWonby() {
        return wonby;
    }

    public Integer getOvers() {
        return overs;
    }

    public Match(){}
}
