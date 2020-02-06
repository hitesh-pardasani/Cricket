package com.example.Cricket;

import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {
    @RequestMapping("/")
    public String home() {
        return "<center>"+
                "<h1>" + "Cricket Game Simulator"+ "</h1> <br>"+
                "<h3>" +
                "<form action=\"/result\" target=\"_self\">\n" +
                "  Team 1 : \n" +
                "  <input type=\"text\" name=\"team1\" >\n" +
                "  <br>\n" +
                "  Team 2 : \n" +
                "  <input type=\"text\" name=\"team2\" >\n" +
                "  <br>\n" +
                "  Number of Overs : \n" +
                "  <input type=\"text\" name=\"overs\" >\n" +
                "  <br><br>\n" +
                "  <input type=\"submit\" >\n" +
                "</form>" +
                "</h3>" +
                "</center>";
    }

    @GetMapping("/result")
    @ResponseBody
    public String index1(@RequestParam(name = "team1") String team1 , @RequestParam(name = "team2") String team2 , @RequestParam(name = "overs") String overs){
        Team A = new Team(team1);
        Team B = new Team(team2);
        Match game = new Match(A,B,Integer.parseInt(overs));
        game.Toss();
        game.StartGame();
        return game.result;
    }

}