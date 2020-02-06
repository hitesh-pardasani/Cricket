package com.cricketgame.service.beans;

import com.cricketgame.service.utils.MathUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

import static com.cricketgame.service.beans.MatchScorecard.getAllMatchScorecards;
import static com.cricketgame.service.beans.MatchScorecard.setAllMatchScorecards;
import static com.cricketgame.service.utils.MathUtils.INF;

@JsonIgnoreProperties({"result", "teams"})

public class CricketMatch {
    public enum Result {Won, Lost, Tie};

    private Team[] teams = new Team[2];
    private MatchScorecard matchScorecard;

    //Constructor
    public CricketMatch(Team team1, Team team2, Integer x) {

        //Initialization
        teams[0] = team1;
        teams[1] = team2;
        teams[0].addNewRecords(teams[1].getName());
        teams[1].addNewRecords(teams[0].getName());
        matchScorecard = new MatchScorecard(team1, team2, x);

        //Add this new Scorecard to the list of Scorecards
        ArrayList<MatchScorecard> copy = getAllMatchScorecards();
        copy.add(this.matchScorecard);
        setAllMatchScorecards(copy);
    }

    // Toss Simulation
    public void doToss() {
        //Random Toss
        int ind = (int) (Math.random() * 2);
        matchScorecard.setTossWinner(teams[ind].getName());

        //Update Toss Statistics
        matchScorecard.getTeamScoreMap().get(teams[ind].getName()).setTossWon(true);

        //Random Decision
        int dec = (int) (Math.random() * 2);
        if (ind == 0 && dec == 0) matchScorecard.setDecidedTo("bat first.");
        else if (ind == 0 && dec == 1) {
            matchScorecard.setDecidedTo("field first.");
            Team swap = teams[0];
            teams[0] = teams[1];
            teams[1] = swap;
        } else if (ind == 1 && dec == 0) {
            matchScorecard.setDecidedTo("bat first.");
            Team swap = teams[0];
            teams[0] = teams[1];
            teams[1] = swap;
        } else matchScorecard.setDecidedTo("field first.");
    }

    //Play Cricket Match
    public void playMatch() {

        Innings firstInnings = new Innings(teams[0], teams[1], matchScorecard.getOvers(), false);
        firstInnings.playInnings();
        firstInnings.updateStats();

        Innings secondInnings = new Innings(teams[1], teams[0], matchScorecard.getOvers(), true);
        secondInnings.playInnings();
        secondInnings.updateStats();

        declareWinner();
        //getHTMLString();
    }

    //Declare winner and set margin of victory
    void declareWinner() {
        if (teams[1].getTeamScorecard().getRunsScored() == teams[0].getTeamScorecard().getRunsScored()) {
            matchScorecard.setWinner("Tie");
            teams[0].getTeamScorecard().setResult(Result.Tie);
            teams[1].getTeamScorecard().setResult(Result.Tie);
        } else if (teams[1].getTeamScorecard().getRunsScored() > teams[0].getTeamScorecard().getRunsScored()) {
            StringBuilder str = new StringBuilder();
            matchScorecard.setWinner(teams[1].getName());
            str.append(Integer.toString(10 - teams[1].getTeamScorecard().getWicketsLost())).append(" wickets.");
            matchScorecard.setWonBy(str.toString());
            teams[0].getTeamScorecard().setResult(Result.Lost);
            teams[1].getTeamScorecard().setResult(Result.Won);
        } else {
            StringBuilder str = new StringBuilder();
            matchScorecard.setWinner(teams[0].getName());
            str.append(Integer.toString(teams[0].getTeamScorecard().getRunsScored() - teams[1].getTeamScorecard().getRunsScored())).append(" runs.");
            matchScorecard.setWonBy(str.toString());
            teams[1].getTeamScorecard().setResult(Result.Lost);
            teams[0].getTeamScorecard().setResult(Result.Won);
        }
    }

    //Getters
    public MatchScorecard getMatchScorecard() {return matchScorecard;
    }

    //Innings Inner Class
    private class Innings {
        Player striker, nonstriker, bowler;
        Team batting, bowling;
        int index, overs, target;
        boolean secondInnings;

        public Innings(Team team1, Team team2, int overs, boolean secondInnings) {
            this.batting = team1;
            this.bowling = team2;
            this.overs = overs;
            this.secondInnings = secondInnings;
        }

        void playInnings() {
            if (secondInnings) target = bowling.getTeamScorecard().getRunsScored() + 1;
            else target = INF;
            striker = batting.getPlayers()[index++];
            nonstriker = batting.getPlayers()[index++];
            for (int i = 0; i < overs; i++) {
                //Random bowler selection
                int rnd = 5 + (int) (Math.random() * 6);
                bowler = bowling.getPlayers()[rnd];

                int overRuns = 0, balls = 0;
                while (balls < 6) {
                    balls++;
                    int runs = simulateBall();
                    overRuns += runs;

                    //Strike Rotation
                    if (runs % 2 == 1) {
                        Player swap = striker;
                        striker = nonstriker;
                        nonstriker = swap;
                    }

                    //Maiden Over
                    if (overRuns == 0 && balls == 6)
                        bowler.getPlayerScorecard().setMaidens(bowler.getPlayerScorecard().getMaidens() + 1);

                    // Match ending conditions and updating notOut details
                    if (batting.getTeamScorecard().getRunsScored() >= target) {
                        striker.getPlayerScorecard().setNotOut(true);
                        nonstriker.getPlayerScorecard().setNotOut(true);
                        return;
                    }
                    if (batting.getTeamScorecard().getWicketsLost() == 10) {
                        nonstriker.getPlayerScorecard().setNotOut(true);
                        return;
                    }
                }

                //Strike Rotation
                Player swap = striker;
                striker = nonstriker;
                nonstriker = swap;
            }
            striker.getPlayerScorecard().setNotOut(true);
            nonstriker.getPlayerScorecard().setNotOut(true);
        }

        int simulateBall() {
            batting.getTeamScorecard().setBallsFaced(batting.getTeamScorecard().getBallsFaced() + 1);
            bowling.getTeamScorecard().setBallsBowled(bowling.getTeamScorecard().getBallsBowled() + 1);
            striker.getPlayerScorecard().setBallsFaced(striker.getPlayerScorecard().getBallsFaced() + 1);
            bowler.getPlayerScorecard().setBallsBowled(bowler.getPlayerScorecard().getBallsBowled() + 1);

            int runs = MathUtils.getBallOutcome(striker, bowler);
            if (runs == 7)       //Wicket
            {
                runs = 0;
                batting.getTeamScorecard().setWicketsLost(batting.getTeamScorecard().getWicketsLost() + 1);
                ;
                bowler.getPlayerScorecard().setWicketsTaken(bowler.getPlayerScorecard().getWicketsTaken() + 1);
                ;
                bowling.getTeamScorecard().setWicketsTaken(bowling.getTeamScorecard().getWicketsTaken() + 1);
                ;
                if (index < 11) striker = batting.getPlayers()[index++];
                else striker = null;
            } else                //Runs
            {
                batting.getTeamScorecard().incrementRunsScored(runs);
                bowling.getTeamScorecard().incrementRunsConceded(runs);
                striker.getPlayerScorecard().incrementRunsScored(runs);
                bowler.getPlayerScorecard().incrementRunsConceded(runs);
                if (runs == 6)      //Six
                {
                    striker.getPlayerScorecard().setSixes(striker.getPlayerScorecard().getSixes() + 1);
                    batting.getTeamScorecard().setSixes(batting.getTeamScorecard().getSixes() + 1);
                }
                if (runs == 4)      //Four
                {
                    striker.getPlayerScorecard().setFours(striker.getPlayerScorecard().getFours() + 1);
                    batting.getTeamScorecard().setFours(batting.getTeamScorecard().getFours() + 1);
                }
            }
            return runs;
        }

        void updateStats() {
            batting.getTeamScorecard().updateStats();
            bowling.getTeamScorecard().updateStats();
            for (int i = 0; i < 11; i++) batting.getPlayers()[i].getPlayerScorecard().updateStats();
            for (int i = 0; i < 11; i++) bowling.getPlayers()[i].getPlayerScorecard().updateStats();
        }
    }

    //    void getHTMLString() {
//        //Header
//        result = "<h2> " + teams[0].getName() + " vs " + teams[1].getName() + "</h2>";
//
//        //Result
//        result = result + toss + " won the toss and decined to " + decision + "<br> <br> ";
//        result = result + teams[0].getName() + " : " +
//                Integer.toString(teams[0].fetchLastRecord().runsScored) +
//                "/" + Integer.toString(teams[0].fetchLastRecord().wicketsLost) +
//                " (" + Integer.toString(teams[0].fetchLastRecord().ballsFaced/6) +
//                "." + Integer.toString(teams[0].fetchLastRecord().ballsFaced%6) + " overs)<br>";
//        result = result + teams[1].getName() + " : " +
//                Integer.toString(teams[1].fetchLastRecord().runsScored) +
//                "/" + Integer.toString(teams[1].fetchLastRecord().wicketsLost) +
//                " (" + Integer.toString(teams[1].fetchLastRecord().ballsFaced/6) +
//                "." + Integer.toString(teams[1].fetchLastRecord().ballsFaced%6) + " overs)<br>";
//        if(winner=="Tie")result = result + "Match ended in a tie. <br>";
//        else result = result + winner + " won by " + wonBy + "<br>";
//
//        //Scorecard
//        result = result + "<style>\n" +
//                "th, td {\n" +
//                "  border: 1px solin grey;\n" +
//                "}\n" +
//                "table {\n" +
//                "  border: 1px solin black;\n" +
//                "}\n" +
//                "</style>";
//        result = result + "<div style = 'winth: 50%'>";
//        result = result + "<h3>" + teams[0].getName() + "'s ins" + "</h3>";
//        result = result + "<table style='text-align : center; winth : 100%;padding: 8px;'>\n" +
//                "  <tr>\n" +
//                "    <th>Batsman</th>\n" +
//                "    <th>Runs</th>\n" +
//                "    <th>Balls</th>\n" +
//                "    <th>4s</th>\n" +
//                "    <th>6s</th>\n" +
//                "    <th>Strike Rate</th>\n" +
//                "  </tr>\n";
//        result = result + teams[0].battingScorecard(teams[0].teamRecords.size()-1);
//        result = result + "</table>\n" ;
//        result = result + "<table style='text-align : center; winth : 100%;padding: 8px;'>\n" +
//                "  <tr>\n" +
//                "    <th>Bowler</th>\n" +
//                "    <th>Overs</th>\n" +
//                "    <th>Runs</th>\n" +
//                "    <th>Wickets</th>\n" +
//                "    <th>Mainen</th>\n" +
//                "    <th>Economy</th>\n" +
//                "  </tr>\n";
//        result = result + teams[1].bowlingScorecard(teams[1].teamRecords.size()-1);
//        result = result + "</table>\n" ;
//        result = result + "<br> <br>";
//        result = result + "<h3>" + teams[1].getName() + "'s ins" + "</h3>";
//        result = result + "<table style='text-align : center; winth : 100%;padding: 8px;'>\n" +
//                "  <tr>\n" +
//                "    <th>Batsman</th>\n" +
//                "    <th>Runs</th>\n" +
//                "    <th>Balls</th>\n" +
//                "    <th>4s</th>\n" +
//                "    <th>6s</th>\n" +
//                "    <th>Strike Rate</th>\n" +
//                "  </tr>\n";
//        result = result + teams[1].battingScorecard(teams[1].teamRecords.size()-1);
//        result = result + "</table>\n" ;
//        result = result + "<table style='text-align : center; winth : 100%;padding: 8px;'>\n" +
//                "  <tr>\n" +
//                "    <th>Bowler</th>\n" +
//                "    <th>Overs</th>\n" +
//                "    <th>Runs</th>\n" +
//                "    <th>Wickets</th>\n" +
//                "    <th>Mainen</th>\n" +
//                "    <th>Economy</th>\n" +
//                "  </tr>\n";
//        result = result + teams[0].bowlingScorecard(teams[0].teamRecords.size()-1);
//        result = result + "</table>\n" ;
//        result = result + "</div>\n" ;
//        result = result + "<br> <br>";
//    }

    //Getters
}
