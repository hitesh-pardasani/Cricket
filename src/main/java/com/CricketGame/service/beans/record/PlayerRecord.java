package com.cricketgame.service.beans.record;

import java.math.BigDecimal;

public class PlayerRecord extends Record {

    //Getters and Setters
    public void setNotOut(boolean notOut) {this.notOut = notOut;
    }
    public boolean isNotOut() {return notOut;
    }
    public BigDecimal getStrikeRate() {return strikeRate;
    }
    public BigDecimal getEconomy() {return economy;
    }

    //Constructors
    public PlayerRecord(){}
    public PlayerRecord(String y) {this.setPlayedAgainst(y);
    }

    //Update Sr and Economy
    public void updateStats() {
        if(getBallsFaced() > 0)strikeRate = new BigDecimal((getRunsScored()*100.0)/getBallsFaced()).setScale(2, BigDecimal.ROUND_HALF_DOWN);
        else strikeRate = new BigDecimal(0.0);
        if(ballsBowled > 0)economy = new BigDecimal((getRunsConceded()*6.0)/ballsBowled).setScale(2, BigDecimal.ROUND_HALF_DOWN);
        else economy = new BigDecimal(0.0);
        setOversBowled(Integer.toString(ballsBowled/6) + "." +Integer.toString(ballsBowled%6));
    }

    private boolean notOut;
    private BigDecimal strikeRate,economy;

    //To generate HTML Strings
//    public String batting() {
//        String ret =
//                "    <td>" + Integer.toString(runsScored) + "</td>\n" +
//                "    <td>" + Integer.toString(ballsFaced) + "</td>\n" +
//                "    <td>" + Integer.toString(fours) + "</td>\n" +
//                "    <td>" + Integer.toString(sixes) + "</td>\n" +
//                "    <td>" + String.format("%.2f",strikeRate) + "</td>\n" +
//                "  </tr>";
//        return ret;
//    }
//    public String bowling() {
//        String ret =
//                "    <td>" + oversBowled + "</td>\n" +
//                "    <td>" + Integer.toString(runsConceded) + "</td>\n" +
//                "    <td>" + Integer.toString(wicketsTaken) + "</td>\n" +
//                "    <td>" + Integer.toString(maidens) + "</td>\n" +
//                "    <td>" + String.format("%.2f",economy) + "</td>\n" +
//                "  </tr>";
//        return ret;
//    }
}