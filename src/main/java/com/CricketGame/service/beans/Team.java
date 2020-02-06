package com.cricketgame.service.beans;

import com.cricketgame.service.beans.record.PlayerRecord;
import com.cricketgame.service.beans.record.TeamRecord;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

@JsonIgnoreProperties({"allTeams"})
public class Team {
    private String name;
    private ArrayList<TeamRecord> teamRecords = new ArrayList<TeamRecord> ();
    private Player players[] = new Player[11];

    public static ArrayList<Team> allTeams = new ArrayList<Team>();
    public static ArrayList<Team> getAllTeams() {return allTeams;
    }

    public static void setAllTeams(ArrayList<Team> allTeams) {Team.allTeams = allTeams;
    }

    //Constructors
    public Team(){}
    public Team(String X) {
        name = X;
        for(Integer i=0;i<11;i++) {
            Player.role type;
            if(i<4)type = Player.role.Batsman;
            else if(i==4)type = Player.role.Wicket_Keeper;
            else if(i==5 || i==6)type = Player.role.All_Rounder;
            else type = Player.role.Bowler;
            players[i] = new Player(name+"_"+i.toString(),type);
        }

        //Add this new team to allTeams list
        ArrayList<Team> copy = getAllTeams();
        copy.add(this);
        setAllTeams(copy);
    }

    public TeamRecord getTeamScorecard(){
        return teamRecords.get(teamRecords.size()-1);
    }

    public void addNewRecords(String X) {
        teamRecords.add(new TeamRecord(X));
        for(int i=0;i<11;i++)
        {
            ArrayList<PlayerRecord> copy = players[i].getPlayerRecords();
            copy.add(new PlayerRecord(X));
            players[i].setPlayerRecords(copy);
        }
    }

    // Getters and Setters
    public ArrayList<TeamRecord> getTeamRecords() {


        return teamRecords;
    }
    public Player[] getPlayers() {


        return players;
    }
    public String getName(){
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setTeamRecords(ArrayList<TeamRecord> teamRecords) {this.teamRecords = teamRecords;
    }
    public void setPlayers(Player[] players) {this.players = players;
    }

    //To return HTML Objects
//    public String battingScorecard(Integer id) {
//        String ret = "";
//        for(int i=0;i<11;i++)
//        {
//            if(players[i].playerRecords.get(id).notOut)
//                ret = ret + "<tr>\n" + "  <td>" + players[i].name + "*" + "</td>\n" + players[i].playerRecords.get(id).batting();
//            else
//                ret = ret + "<tr>\n" + "  <td>" + players[i].name + "</td>\n" + players[i].playerRecords.get(id).batting();
//        }
//        return ret;
//    }
//    public String bowlingScorecard(Integer id) {
//        String ret = "";
//        for(int i=5;i<11;i++)ret = ret + "<tr>\n" + "  <td>" + players[i].name + "</td>\n" + players[i].playerRecords.get(id).bowling();
//        return ret;
//    }

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
