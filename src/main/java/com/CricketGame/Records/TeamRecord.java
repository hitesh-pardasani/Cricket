package com.CricketGame.Records;

import com.CricketGame.Match;
import com.CricketGame.Match.Result;

public class TeamRecord extends Record {
    public String name;
    public Double run_rate=0.0;
    public Integer wickets_lost=0;
    public Match.Result result;
    public Boolean toss = false;
    public PlayerRecord playerRecord[] = new PlayerRecord[11];

    public void finish() {
        this.run_rate = (this.runs_scored * 6.0) / this.balls_faced;
        overs_bowled = Integer.toString(balls_bowled / 6) + "." + Integer.toString(balls_bowled % 6);
    }

    public TeamRecord(){}

    public TeamRecord(String x, String y) {
        this.name = x;
        this.against = y;
    }
    public Double getRun_rate() {
        return run_rate;
    }

    public Integer getWickets_lost() {
        return wickets_lost;
    }

    public Result getResult() {
        return result;
    }

    public Boolean getToss() {
        return toss;
    }

    @Override
    public String toString() {
        String ret = "";
        ret = ret + against + " " + Integer.toString(runs_scored) + " " + Integer.toString(wickets_lost) + " " + result.toString();
        return ret;
    }
}
