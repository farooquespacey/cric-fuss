package com.befaruq.model.response.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * Custom DTO where only partial fields (skipping children fields) from the Inning class matters for some APIs.
 * 
 * @author Farooque
 */
@Builder
@Getter
public class InningOnlyDTO {
    private Long inningId;
    private String battingTeam;
    private String bowlingTeam;
    private int totalRuns;
    private int totalWickets;
    private float overs;
    private float runRate;
}
