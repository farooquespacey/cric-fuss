package com.befaruq.model.request;

import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Players{
    Map<String, List<String>> players = new HashMap<>();
    
    @JsonAnySetter
    public void addTeam(String teamName, List<String> playersList) {
        players.put(teamName, playersList);
    }

    public Map<String, List<String>> getPlayers() {
        return players;
    }

    public void setPlayers(Map<String, List<String>> players) {
        this.players = players;
    }
}
