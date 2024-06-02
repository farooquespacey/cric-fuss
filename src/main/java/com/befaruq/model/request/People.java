package com.befaruq.model.request;

import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.HashMap;
import java.util.Map;

public class People{
    public Map<String, String> people = new HashMap<>();
    
    @JsonAnySetter
    public void addPerson(String name, String id) {
        people.put(name, id);
    }
    
    public Map<String, String> getPeople() {
        return people;
    }

    public void setPeople(Map<String, String> people) {
        this.people = people;
    }
}
