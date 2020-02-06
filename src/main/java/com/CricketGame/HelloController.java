package com.CricketGame;

import com.CricketGame.TeamPackage.Player;
import com.CricketGame.TeamPackage.Team;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;

@RestController
public class HelloController {
    ArrayList<Match> all_matches = new ArrayList<Match>();
    ArrayList<Team> all_teams = new ArrayList<Team>();
    String Header = "<center> <h1> Cricket Game Simulator </h1>";
    String Footer = "<form action=\"/\" target=\"_self\">\n" +
            "<input type=\"submit\" value=\"Home\">" +
            "</form>"  + "</center>";
    @RequestMapping("/")
    public String home() {
        return Header +
                "<h3>" +
                "<form action=\"/playgame\" target=\"_self\">\n" +
                "  <input type=\"submit\" value=\"Start Game\">\n" +
                "</form>" +
                "<form action=\"/team-stats\" target=\"_self\">\n" +
                "  <input type=\"submit\" value=\"Team Statistics\">\n" +
                "</form>" +
                "<form action=\"/player-stats\" target=\"_self\">\n" +
                "  <input type=\"submit\" value=\"Player Statistics\">\n" +
                "</form>" +
                "</h3>" + Footer;
    }

    @RequestMapping("/player-stats")
    public String formfunc1() {
        return Header +
                "<h3>" +
                "<form action=\"/all-player-stats\" target=\"_self\">\n" +
                "  <input type=\"submit\" value=\"All Players\">\n" +
                "</form>" +
                "<form action=\"/single-player-stats\" target=\"_self\">\n" +
                "  Team Name : \n" +
                "  <input type=\"text\" name=\"name\" >\n" +
                "  <input type=\"submit\" value=\"Get Statistics\">\n" +
                "</form>" +
                "</h3>" + Footer;
    }

    @GetMapping("/single-player-stats")
    @ResponseBody
    public ArrayList<Player> scorecard2(@RequestParam(name = "name") String team1)
    {
        ArrayList<Player> ret = new ArrayList<Player>();
        for(int i=0;i<all_teams.size();i++)
        {
            if(team1.equals( all_teams.get(i).name) )
            {
                for(int j=0;j<11;j++)ret.add(all_teams.get(i).players[j]);
            }
        }
        return ret;
    }

    @RequestMapping("/all-player-stats")
    public ArrayList<Player> func1() {
        ArrayList<Player> ret = new ArrayList<Player>();
        for(int i=0;i<all_teams.size();i++)
        {
            for(int j=0;j<11;j++)ret.add(all_teams.get(i).players[j]);
        }
        return ret;
    }

    @RequestMapping("/team-stats")
    public String formfunc() {
        return Header +
                "<h3>" +
                "<form action=\"/all-team-stats\" target=\"_self\">\n" +
                "  <input type=\"submit\" value=\"All Teams\">\n" +
                "</form>" +
                "<form action=\"/single-team-stats\" target=\"_self\">\n" +
                "  Team Name : \n" +
                "  <input type=\"text\" name=\"name\" >\n" +
                "  <input type=\"submit\" value=\"Get Statistics\">\n" +
                "</form>" +
                "</h3>" + Footer;
    }


    @GetMapping("/single-team-stats")
    @ResponseBody
    public Team scorecard1(@RequestParam(name = "name") String team1)
    {
        for(int i=0;i<all_teams.size();i++)
        {
            if(team1.equals( all_teams.get(i).name) )return all_teams.get(i);
        }
        return null;
    }

    @RequestMapping("/all-team-stats")
    public ArrayList<Team> func() {
        return all_teams;
    }


    @RequestMapping("/playgame")
    public String game() {
        return Header +  "<h3>" +
                "<form action=\"/result\" target=\"_self\">\n" +
                "  Team 1 : \n" +
                "  <input type=\"text\" name=\"team1\" >\n" +
                "  <br> <br> \n" +
                "  Team 2 : \n" +
                "  <input type=\"text\" name=\"team2\" >\n" +
                "  <br> <br>\n" +
                "  Number of Overs : \n" +
                "  <input type=\"text\" name=\"overs\" >\n" +
                "  <br><br>\n" +
                "  <input type=\"submit\" >\n" +
                "</form>" +
                "</h3>" + Footer;
    }

    @GetMapping("/result")
    @ResponseBody
    public ArrayList<Match> scorecard(@RequestParam(name = "team1") String team1 , @RequestParam(name = "team2") String team2 , @RequestParam(name = "overs") String overs){
        Team A = null,B = null;
        for(int i=0;i<all_teams.size();i++)
        {
            if(all_teams.get(i).getName().equals(team1)){A = all_teams.get(i);break;}
        }
        for(int i=0;i<all_teams.size();i++)
        {
            if(all_teams.get(i).getName().equals(team2)){B = all_teams.get(i);break;}
        }
        if(A == null){A = new Team(team1);all_teams.add(A);}
        if(B == null){B = new Team(team2);all_teams.add(B);}
        Match game = new Match(A,B,Integer.parseInt(overs));
        game.Toss();
        game.StartGame();
        String ret = game.result;
        ret = Header + ret +Footer;
        all_matches.add(game);
        return all_matches;
    }

}