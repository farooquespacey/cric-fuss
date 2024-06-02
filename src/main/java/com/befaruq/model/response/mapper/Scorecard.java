package com.befaruq.model.response.mapper;

import com.befaruq.model.response.Match;
import com.befaruq.model.response.Meta;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Scorecard {
    private String status;
    private Meta meta;
    private Match match;

    public Scorecard(String status, Meta meta, Match match) {
        this.status = status;
        this.meta = meta;
        this.match = match;
    }

    // Constructor, Getters, and Setters
}