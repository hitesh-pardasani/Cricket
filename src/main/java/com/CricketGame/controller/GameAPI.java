package com.cricketgame.controller;

import com.cricketgame.service.GameController;
import com.cricketgame.service.beans.MatchScorecard;
import com.cricketgame.service.beans.Player;
import com.cricketgame.service.beans.Team;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import static com.cricketgame.service.Constants.FOOTER;
import static com.cricketgame.service.Constants.HEADER;
import static com.cricketgame.service.beans.MatchScorecard.getAllMatchScorecards;
import static com.cricketgame.service.beans.Team.allTeams;
import static com.cricketgame.service.beans.Team.getAllTeams;

@RestController
public class GameAPI {
    GameController gameController = new GameController();

    //UI Code
    @RequestMapping("/")
    public String home() {
        return HEADER +
                "<h3>" +
                "<form action=\"/playgame\" target=\"_self\">\n" +
                "  <input type=\"submit\" value=\"Start Game\">\n" +
                "</form>" +
                "<form action=\"/team-stats-form\" target=\"_self\">\n" +
                "  <input type=\"submit\" value=\"Team Statistics\">\n" +
                "</form>" +
                "<form action=\"/player-stats-form\" target=\"_self\">\n" +
                "  <input type=\"submit\" value=\"Player Statistics\">\n" +
                "</form>" +
                "<form action=\"/all-match-stats\" target=\"_self\">\n" +
                "  <input type=\"submit\" value=\"All Match Scorecards\">\n" +
                "</form>" +
                "</h3>" + FOOTER;
    }

    @RequestMapping("/playgame")
    public String gameForm() {
        return HEADER +  "<h3>" +
                "<form action=\"/result\" target=\"_self\">\n" +
                "  Team 1 : \n" +
                "  <input type=\"text\" name=\"team1\" value=\"India\">\n" +
                "  <br> <br> \n" +
                "  Team 2 : \n" +
                "  <input type=\"text\" name=\"team2\" value =\"Australia\">\n" +
                "  <br> <br>\n" +
                "  Number of Overs : \n" +
                "  <input type=\"text\" name=\"overs\" value = \"10\">\n" +
                "  <br><br>\n" +
                "  <input type=\"submit\" >\n" +
                "</form>" +
                "</h3>" + FOOTER;
    }

    @GetMapping("/result")
    @ResponseBody
    public MatchScorecard gameResult(@RequestParam(name = "team1") String team1 , @RequestParam(name = "team2") String team2 , @RequestParam(name = "overs") String overs){
        return gameController.startGame(team1,team2,Integer.parseInt(overs));
    }

}