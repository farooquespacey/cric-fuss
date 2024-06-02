package com.befaruq.model.request;

import java.util.ArrayList;

public class Delivery{
    public String batter;
    public String bowler;
    public String non_striker;
    public Runs runs;
    public Review review;
    public ArrayList<Wicket> wickets;
    public Extras extras;
    
    public Delivery() {
        this.wickets = new ArrayList<>();
    }
}
