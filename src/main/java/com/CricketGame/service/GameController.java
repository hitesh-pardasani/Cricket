package com.cricketgame.service;

import com.cricketgame.service.beans.CricketMatch;
import com.cricketgame.service.beans.Team;
import com.cricketgame.service.beans.MatchScorecard;

public class GameController {
    public MatchScorecard startGame(String team1, String team2, int overs) {

        Team team[] = new Team[2];
        team[0] = null;
        team[1] = null;

        for(int i = 0 ; i < team[0].allTeams.size(); i++){
            if(team[0].allTeams.get(i).getName() == team1){
                team[0] = team[0].allTeams.get(i);
                break;
            }
        }

        for(int i = 0 ; i < team[1].allTeams.size(); i++){
            if(team[1].allTeams.get(i).getName() == team1){
                team[1] = team[1].allTeams.get(i);
                break;
            }
        }

        if(team[0]==null)team[0] = new Team(team1);
        if(team[1]==null)team[1] = new Team(team2);

        CricketMatch cricketMatch = new CricketMatch(team[0],team[1],overs);
        cricketMatch.doToss();
        cricketMatch.playMatch();
        return cricketMatch.getMatchScorecard();
    }

//
//    start
}
