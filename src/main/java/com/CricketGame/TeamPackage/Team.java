package com.CricketGame.TeamPackage;

import com.CricketGame.Records.PlayerRecord;
import com.CricketGame.Records.TeamRecord;

import java.util.ArrayList;

enum Role{ Batsman, All_Rounder, Bowler, Wicket_Keeper }

public class Team {

    public String name;
    public ArrayList<TeamRecord> teamRecords = new ArrayList<TeamRecord> ();
    public Player players[] = new Player[11];
    public Team(){}
    public Team(String X)
    {
        this.name = X;
        players[0] = new Player("Rohit",Role.Batsman);
        players[1] = new Player("Dhawan",Role.Batsman);
        players[2] = new Player("Kohli",Role.Batsman);
        players[3] = new Player("Iyer",Role.Batsman);
        players[4] = new Player("Dhoni",Role.Wicket_Keeper);
        players[5] = new Player("Pandya",Role.All_Rounder);
        players[6] = new Player("Jadeja",Role.All_Rounder);
        players[7] = new Player("Kuldeep",Role.Bowler);
        players[8] = new Player("Shami",Role.Bowler);
        players[9] = new Player("Saini",Role.Bowler);
        players[10] = new Player("Bumrah",Role.Bowler);
    }

    public void addNewRecords(String X)
    {
        teamRecords.add(new TeamRecord(name,X));
        for(int i=0;i<11;i++)players[i].playerRecords.add(new PlayerRecord(players[i].name,X));
    }

    public ArrayList<TeamRecord> getTeamRecords() {
        return teamRecords;
    }

    public Player[] getPlayers() {
        return players;
    }

    public String getName(){
        return name;
    }

    public String batting_scorecard(Integer id)
    {
        String ret = "";
        for(int i=0;i<11;i++)
        {
            if(players[i].playerRecords.get(players[i].playerRecords.size()-1).notout)
                ret = ret + "<tr>\n" + "  <td>" + players[i].name + "*" + "</td>\n" + players[i].playerRecords.get(id).batting();
            else
                ret = ret + "<tr>\n" + "  <td>" + players[i].name + "</td>\n" + players[i].playerRecords.get(id).batting();
        }
        return ret;
    }

    public String bowling_scorecard(Integer id)
    {
        String ret = "";
        for(int i=5;i<11;i++)ret = ret + "<tr>\n" + "  <td>" + players[i].name + "</td>\n" + players[i].playerRecords.get(id).bowling();
        return ret;
    }

    @Override public String toString() {
        String ret ;
        ret = name + "<br>";
        for(int i=0;i<teamRecords.size();i++)
        {
            ret = ret + "  <tr>\n" + teamRecords.get(i).toString() + "<br>";
        }
        return ret;
    }
}
