package com.cricketgame.controller;

import com.cricketgame.service.beans.Team;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import static com.cricketgame.service.Constants.FOOTER;
import static com.cricketgame.service.Constants.HEADER;
import static com.cricketgame.service.beans.Team.allTeams;
import static com.cricketgame.service.beans.Team.getAllTeams;

@RestController
public class TeamAPI {
    @RequestMapping("/team-stats-form")
    public String teamStatsForm() {
        return HEADER +
                "<h3>" +
                "<form action=\"/all-team-stats\" target=\"_self\">\n" +
                "  <input type=\"submit\" value=\"All Teams\">\n" +
                "</form>" +
                "<form action=\"/single-team-stats\" target=\"_self\">\n" +
                "  Team Name : \n" +
                "  <input type=\"text\" name=\"name\" >\n" +
                "  <input type=\"submit\" value=\"Get Statistics\">\n" +
                "</form>" +
                "</h3>" + FOOTER;
    }

    @GetMapping("/single-team-stats")
    @ResponseBody
    public Team singleTeamStats(@RequestParam(name = "name") String team1) {
        for(int i=0;i<allTeams.size();i++)
        {
            if(team1.equals( allTeams.get(i).getName()) )return allTeams.get(i);
        }
        return null;
    }

    @RequestMapping("/all-team-stats")
    public ArrayList<Team> allTeamStats() {return getAllTeams();
    }

}
