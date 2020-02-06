package com.CricketGame.Records;

public class PlayerRecord extends Record {
    public Boolean notout = false;
    public Double strike_rate,economy;

    public PlayerRecord(){}
    public PlayerRecord(String x, String y)
    {
        this.name = x;
        this.against = y;
    }

    public Double getStrike_rate() {
        return strike_rate;
    }

    public Double getEconomy() {
        return economy;
    }

    public void finish()
    {
        if(balls_faced > 0)strike_rate = (runs_scored*100.0)/balls_faced;
        else strike_rate = 0.0;
        if(balls_bowled > 0)economy = (runs_conceded*6.0)/balls_bowled;
        else economy = 0.0;
        overs_bowled = Integer.toString(balls_bowled/6) + "." +Integer.toString(balls_bowled%6);
    }

    public String batting() {
        String ret =
                "    <td>" + Integer.toString(runs_scored) + "</td>\n" +
                "    <td>" + Integer.toString(balls_faced) + "</td>\n" +
                "    <td>" + Integer.toString(fours) + "</td>\n" +
                "    <td>" + Integer.toString(sixes) + "</td>\n" +
                "    <td>" + String.format("%.2f",strike_rate) + "</td>\n" +
                "  </tr>";
        return ret;
    }

    public String bowling() {
        String ret =
                "    <td>" + overs_bowled + "</td>\n" +
                "    <td>" + Integer.toString(runs_conceded) + "</td>\n" +
                "    <td>" + Integer.toString(wickets_taken) + "</td>\n" +
                "    <td>" + Integer.toString(maidens) + "</td>\n" +
                "    <td>" + String.format("%.2f",economy) + "</td>\n" +
                "  </tr>";
        return ret;
    }
}