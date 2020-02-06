package com.CricketGame.Records;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class Record
{
    public String name;
    public String against;
    public Integer balls_faced=0,runs_scored=0;
    String overs_bowled;
    public Integer runs_conceded=0,fours=0,sixes=0,wickets_taken=0,maidens=0;

    public String getOvers_bowled() {
        return overs_bowled;
    }

    @JsonIgnore
    public Integer balls_bowled=0;

    public Integer getBalls_faced() {
        return balls_faced;
    }

    public Integer getRuns_scored() {
        return runs_scored;
    }

    public Integer getBalls_bowled() {
        return balls_bowled;
    }

    public Integer getRuns_conceded() {
        return runs_conceded;
    }

    public Integer getFours() {
        return fours;
    }

    public Integer getSixes() {
        return sixes;
    }

    public Integer getWickets_taken() {
        return wickets_taken;
    }

    public Integer getMaidens() {
        return maidens;
    }

    public String getAgainst() {
        return against;
    }
}

