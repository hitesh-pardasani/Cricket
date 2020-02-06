package com.cricketgame.service.beans.record;

import com.fasterxml.jackson.annotation.JsonIgnore;


public abstract class Record {

    //Increment Methods
    public void incrementRunsScored(int runs){this.setRunsScored(this.getRunsScored()+runs);}
    public void incrementRunsConceded(int runs){this.setRunsConceded(this.getRunsConceded()+runs);}

    //Getters and Setters
    public int getBallsFaced() { return ballsFaced;
    }
    public int getRunsScored() { return runsScored;
    }
    public int getRunsConceded() { return runsConceded;
    }
    public int getFours() { return fours;
    }
    public int getSixes() { return sixes;
    }
    public int getWicketsTaken() { return wicketsTaken;
    }
    public int getMaidens() { return maidens;
    }
    public int getBallsBowled() {return ballsBowled;
    }
    public void setPlayedAgainst(String playedAgainst) {this.playedAgainst = playedAgainst;
    }
    public void setBallsFaced(int ballsFaced) {this.ballsFaced = ballsFaced;
    }
    public void setRunsScored(int runsScored) {this.runsScored = runsScored;
    }
    public void setOversBowled(String oversBowled) {this.oversBowled = oversBowled;
    }
    public void setRunsConceded(int runsConceded) {this.runsConceded = runsConceded;
    }
    public void setFours(int fours) {this.fours = fours;
    }
    public void setSixes(int sixes) {this.sixes = sixes;
    }
    public void setWicketsTaken(int wicketsTaken) {this.wicketsTaken = wicketsTaken;
    }
    public void setMaidens(int maidens) {this.maidens = maidens;
    }
    public void setBallsBowled(int ballsBowled) {this.ballsBowled = ballsBowled;
    }
    public String getPlayedAgainst() {return playedAgainst;
    }
    public String getOversBowled() {return oversBowled;
    }


    private String playedAgainst;
    private int ballsFaced=0,runsScored=0;
    private String oversBowled;
    private int runsConceded=0,fours=0,sixes=0,wicketsTaken=0,maidens=0;

    @JsonIgnore
    public int ballsBowled=0;
}