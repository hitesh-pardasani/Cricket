package com.cricketgame.service.utils;

import com.cricketgame.service.beans.Player;

import java.util.Random;

import static java.lang.StrictMath.abs;


public class MathUtils {
    public static final Integer INF = 1000000000;
    public static int getBallOutcome(Player striker, Player bowler) {
        Random random = new Random();
        double lowerLimit = 3;
        double upperLimit = lowerLimit + 13;
        double variance = 1.75, mean = lowerLimit + 2, ratio;
        ratio = striker.getBattingRating()/bowler.getBowlingRating();
        if(ratio < 1){
            ratio = 1/ratio;
            ratio = ratio - 1;
            mean = mean - ((mean-lowerLimit)/9)*ratio;
        }
        else{
            ratio = ratio - 1;
            mean = mean + ((upperLimit-mean)/9)*ratio;
        }
        double val = random.nextGaussian()*variance + mean;
        int ret = (int) Math.round(val);
        ret = abs(ret);
        if(ret < lowerLimit/2) return 7;
        ret = Math.max(ret,(int)lowerLimit);
        ret = Math.min(ret,(int)upperLimit);
        ret = (ret - (int)lowerLimit)/2;
        return ret;
    }
}
