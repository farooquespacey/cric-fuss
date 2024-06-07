package com.befaruq.service;

import com.befaruq.model.response.Batting;
import com.befaruq.model.response.Bowling;
import com.befaruq.model.response.Inning;
import com.befaruq.model.response.dto.InningOnlyDTO;
import com.befaruq.repository.BattingRepository;
import com.befaruq.repository.BowlingRepository;
import com.befaruq.repository.InningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Farooque
 */
@Service
public class StatsService {
    
    @Autowired
    InningRepository inningRepository;
    @Autowired
    BattingRepository battingRepository;
    @Autowired
    BowlingRepository bowlingRepository;
    
    public List<Batting> getTopNScorer(Long inningId, int topNum) {
        return battingRepository.findTopNScorer(inningId, PageRequest.of(0, topNum));
    }

    public List<Bowling> getTopNBowler(Long inningId, int topNum) {
        return bowlingRepository.findTopNBowler(inningId, PageRequest.of(0, topNum));
    }
    
    public InningOnlyDTO getInningData(Long inningId) {
        Inning inning = inningRepository.findById(inningId).orElseThrow(() -> new RuntimeException("The inningId doesn't exist!"));
        return InningOnlyDTO.builder().inningId(inning.getInningId())
                .battingTeam(inning.getBattingTeam()).bowlingTeam(inning.getBowlingTeam())
                .totalRuns(inning.getTotalRuns()).totalWickets(inning.getTotalWickets())
                .overs(inning.getOvers()).runRate(inning.getRunRate())
                .build();
    }
    
    public Inning getInningDataWithChild(Long inningId) {
        return inningRepository.findById(inningId).orElseThrow(() -> new RuntimeException("The inningId doesn't exist!"));
    }
}
