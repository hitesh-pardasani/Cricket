package com.cricketgame.service.beans;

import com.cricketgame.service.beans.record.PlayerRecord;
import com.cricketgame.service.beans.record.TeamRecord;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@JsonIgnoreProperties({"allMatchScorecards"})
public class MatchScorecard{

    private Integer overs;
    private String tossWinner,decidedTo,winner,wonBy;
    private Map<String, TeamRecord> teamScoreMap = new HashMap<>();
    private Map<String, Map<String,PlayerRecord>> playerScoreMap = new HashMap<>();

    public static ArrayList<MatchScorecard> allMatchScorecards = new ArrayList<MatchScorecard>();

    //Constructor
    public MatchScorecard(Team team1, Team team2, Integer overs) {
        this.overs = overs;
        teamScoreMap.put(team1.getName(),team1.getTeamScorecard());
        teamScoreMap.put(team2.getName(),team2.getTeamScorecard());

        TreeMap<String , PlayerRecord> playerRecordMap1 = new TreeMap<String, PlayerRecord>();
        for(int i=0;i<11;i++) {
            playerRecordMap1.put(team1.getPlayers()[i].getName(),team1.getPlayers()[i].getPlayerScorecard());
        }
        playerScoreMap.put(team1.getName(),playerRecordMap1);

        TreeMap<String , PlayerRecord> playerRecordMap2 = new TreeMap<String, PlayerRecord>();
        for(int i=0;i<11;i++) {
            playerRecordMap2.put(team2.getPlayers()[i].getName(),team2.getPlayers()[i].getPlayerScorecard());
        }
        playerScoreMap.put(team2.getName(),playerRecordMap2);
    }
    public MatchScorecard(){}

    //Getters and Setters
    public static ArrayList<MatchScorecard> getAllMatchScorecards() {return allMatchScorecards;
    }
    public static void setAllMatchScorecards(ArrayList<MatchScorecard> allMatchScorecards) {MatchScorecard.allMatchScorecards = allMatchScorecards;
    }
    public Map<String, TeamRecord> getTeamScoreMap() {return teamScoreMap;
    }
    public Map<String, Map<String,PlayerRecord>> getPlayerScoreMap() {return playerScoreMap;
    }
    public void setOvers(Integer overs) {this.overs = overs;
    }
    public void setTossWinner(String tossWinner) {this.tossWinner = tossWinner;
    }
    public void setDecidedTo(String decidedTo) {this.decidedTo = decidedTo;
    }
    public void setWinner(String winner) {this.winner = winner;
    }
    public void setWonBy(String wonBy) {this.wonBy = wonBy;
    }
    public void setTeamScoreMap(Map<String, TeamRecord> teamScoreMap) {this.teamScoreMap = teamScoreMap;
    }
    public void setPlayerScoreMap(Map<String, Map<String, PlayerRecord>> playerScoreMap) {
        this.playerScoreMap = playerScoreMap;
    }
    public Integer getOvers() {return overs;
    }
    public String getTossWinner() {return tossWinner;
    }
    public String getDecidedTo() {return decidedTo;
    }
    public String getWinner() {return winner;
    }
    public String getWonBy() {return wonBy;
    }
}
