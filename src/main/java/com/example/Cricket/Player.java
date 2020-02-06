package com.example.Cricket;



public class Player {
    public String name = "Dhoni";
    public Integer runs_scored = 0,balls_faced=0,wickets_taken=0,balls_bowled=0,runs_given=0;
    @Override public String toString() {
        String ret ;
        Integer ov = balls_bowled/6, ba = balls_bowled%6;
        ret = "Player Name : " + name + " | " + "Batting : " + runs_scored.toString() + "(" + balls_faced.toString() + ") " +
                "| Bowling : " + wickets_taken.toString() + "-" + runs_given.toString() + "(" + ov.toString() + "." + ba.toString() + ")<br>" ;
        return ret;
    }
}
