package com.cricketgame.service.beans;

import com.cricketgame.service.beans.record.PlayerRecord;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;

public class Player {

    enum role{ Batsman, All_Rounder, Bowler, Wicket_Keeper }

    //Constructors
    Player(String x, role y) {
        name = x;
        type = y;
        if(type == role.Batsman) {
            battingRating = 8.0;
            bowlingRating = 1.0;
        }
        else if(type == role.Bowler){
            battingRating = 1.0;
            bowlingRating = 8.0;
        }
        else if(type == role.Wicket_Keeper){
            battingRating = 6.0;
            bowlingRating = 1.0;
        }
        else {
            battingRating = 5.0;
            bowlingRating = 5.0;
        }
    }

    //Get the record of the game currently running
    @JsonIgnore
    public PlayerRecord getPlayerScorecard(){

        return playerRecords.get((playerRecords.size()-1));
    }

    //Getters and Setters
    public String getName() {

        return name;
    }
    public role getType() {

        return type;
    }
    public ArrayList<PlayerRecord> getPlayerRecords() {
        return playerRecords;

    }
    public double getBattingRating() {return battingRating;
    }
    public double getBowlingRating() {return bowlingRating;
    }
    public void setBattingRating(double battingRating) {this.battingRating = battingRating;
    }
    public void setBowlingRating(double bowlingRating) {this.bowlingRating = bowlingRating;
    }
    public void setPlayerRecords(ArrayList<PlayerRecord> playerRecords) {this.playerRecords = playerRecords;
    }

    private double battingRating;
    private double bowlingRating;
    final private String name;
    final private role type;
    private ArrayList<PlayerRecord> playerRecords = new ArrayList<PlayerRecord>();
}