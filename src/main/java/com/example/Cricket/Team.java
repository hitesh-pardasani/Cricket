package com.example.Cricket;

public class Team {
    private String name;
    public Integer balls = 0;
    public Integer runs = 0;
    public Integer wickets = 0;
    public Player players[]= new Player[11];
    public Team(){}
    public Team(String X)
    {
        this.name = X;
        for(int i=0;i<11;i++)players[i] = new Player();
    }

    String getName(){return name;}

    @Override public String toString() {
        String ret ;
        ret = name + "<br>";
        ret = ret + "Balls faced : " + balls.toString() + "<br>";
        ret = ret + "Runs scored : " + balls.toString() + "<br>";
        ret = ret + "Wickets lost : " + balls.toString() + "<br>";
        ret = ret + "Players Info : " + "<br>";
        for(int i=0;i<11;i++)ret = ret + players[i].toString();
        return ret;
    }
}
