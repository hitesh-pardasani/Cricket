package com.CricketGame.TeamPackage;

import com.CricketGame.Records.PlayerRecord;

import java.util.ArrayList;

public class Player {
    final public String name;
    final public Role type;
    public ArrayList<PlayerRecord> playerRecords = new ArrayList<PlayerRecord>();

    public String getName() {
        return name;
    }

    public Role getType() {
        return type;
    }

    public ArrayList<PlayerRecord> getPlayerRecords() {
        return playerRecords;
    }

    Player(String x, Role y)
    {
        name = x;
        type = y;
    }
//    @Override public String toString() {
//        String ret ;
//        Integer ov = balls_bowled/6, ba = balls_bowled%6;
//        ret = "Player Name : " + name + " | " + "Batting : " + runs_scored.toString() + "(" + balls_faced.toString() + ") " +
//                "| Bowling : " + wickets_taken.toString() + "-" + runs_given.toString() + "(" + ov.toString() + "." + ba.toString() + ")<br>" ;
//        return ret;
//    }
}