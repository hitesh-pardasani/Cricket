package com.cricketgame.service.beans.record;

import com.cricketgame.service.beans.CricketMatch;

import java.math.BigDecimal;

public class TeamRecord extends Record {
    // Constructors
    public TeamRecord(){}
    public TeamRecord(String y) {this.setPlayedAgainst(y);}

    // Update Run Rate and Overs Bowled
    public void updateStats() {
        if(getBallsFaced() > 0)runRate = new BigDecimal((getRunsScored() * 6.0) / getBallsFaced()).setScale(2, BigDecimal.ROUND_HALF_UP);
        setOversBowled(Integer.toString(ballsBowled / 6) + "." + Integer.toString(ballsBowled % 6));
    }

    //Getters and Setters
    public BigDecimal getRunRate() { return runRate;
    }
    public Integer getWicketsLost() { return wicketsLost;
    }
    public CricketMatch.Result getResult() {return result;
    }
    public Boolean getTossWon() {return tossWon;
    }
    public void setRunRate(BigDecimal runRate) {this.runRate = runRate;
    }
    public void setWicketsLost(Integer wicketsLost) {this.wicketsLost = wicketsLost;
    }
    public void setResult(CricketMatch.Result result) {this.result = result;
    }
    public void setTossWon(Boolean tossWon) {this.tossWon = tossWon;
    }

    private BigDecimal runRate;
    private Integer wicketsLost=0;
    private CricketMatch.Result result;
    private Boolean tossWon = false;
}
