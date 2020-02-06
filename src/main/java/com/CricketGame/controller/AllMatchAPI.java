package com.cricketgame.controller;

import com.cricketgame.service.beans.MatchScorecard;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import static com.cricketgame.service.beans.MatchScorecard.getAllMatchScorecards;

@RestController
public class AllMatchAPI {
    @RequestMapping("/all-match-stats")
    public ArrayList<MatchScorecard> allMatchStats() {return getAllMatchScorecards();
    }

}