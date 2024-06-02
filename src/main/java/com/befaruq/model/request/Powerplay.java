package com.befaruq.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Powerplay{
    public double from;
    @JsonProperty("to") 
    public double myto;
    public String type;
}
