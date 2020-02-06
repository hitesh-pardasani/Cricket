package com.cricketgame.controller;

import com.cricketgame.service.beans.Player;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import static com.cricketgame.service.Constants.FOOTER;
import static com.cricketgame.service.Constants.HEADER;
import static com.cricketgame.service.beans.Team.allTeams;

@RestController
public class PlayerAPI {

    @RequestMapping("/player-stats-form")
    public String playerStatsForm() {
        return HEADER +
                "<h3>" +
                "<form action=\"/all-player-stats\" target=\"_self\">\n" +
                "  <input type=\"submit\" value=\"All Players\">\n" +
                "</form>" +
                "<form action=\"/single-player-stats\" target=\"_self\">\n" +
                "  Team Name : \n" +
                "  <input type=\"text\" name=\"name\" >\n" +
                "  <input type=\"submit\" value=\"Get Statistics\">\n" +
                "</form>" +
                "</h3>" + FOOTER;
    }

    @GetMapping("/single-player-stats")
    @ResponseBody
    public ArrayList<Player> singlePlayerStats(@RequestParam(name = "name") String team1) {
        ArrayList<Player> ret = new ArrayList<Player>();
        for(int i=0;i<allTeams.size();i++)
        {
            if(team1.equals( allTeams.get(i).getName()) )
            {
                for(int j=0;j<11;j++)ret.add(allTeams.get(i).getPlayers()[j]);
            }
        }
        return ret;
    }

    @RequestMapping("/all-player-stats")
    public ArrayList<Player> allPlayerStats() {
        ArrayList<Player> ret = new ArrayList<Player>();
        for(int i=0;i<allTeams.size();i++)
        {
            for(int j=0;j<11;j++)ret.add(allTeams.get(i).getPlayers()[j]);
        }
        return ret;
    }


}
