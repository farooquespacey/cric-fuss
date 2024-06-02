package com.befaruq.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class State{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stateId;
    public String stateName;
    public String outType;
    public String bowledBy;
    public String runOutBy;
    public String caughtBy;
}