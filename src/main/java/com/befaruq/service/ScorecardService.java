package com.befaruq.service;

import com.befaruq.model.request.CricInfoRequest;
import com.befaruq.model.response.Match;
import com.befaruq.model.response.Meta;
import com.befaruq.model.response.mapper.Scorecard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ScorecardService {
    
    @Autowired
    private MetaService metaService;
    
    @Autowired
    private MatchService matchService;
    
    public Scorecard save(CricInfoRequest ciReq) {
        Long matchId = matchService.findMatch(LocalDate.parse(ciReq.info.dates.get(0)), ciReq.info.city, ciReq.info.teams.get(0), ciReq.info.teams.get(1));
        if (matchId != null) throw new RuntimeException("Match already exists with an id '" + matchId + "'");
        Meta meta = metaService.save(ciReq.meta);
        Match match = matchService.save(ciReq, meta.getMetaId());
        return new Scorecard("success", meta, match);
    }

    public Scorecard getScorecard(Long matchId) {
        Match match = matchService.findById(matchId).orElseThrow(() -> new RuntimeException(String.format("Match id '%s' not found", matchId)));
        Meta meta = metaService.findById(match.getMetaId()).orElseThrow(() -> new RuntimeException(String.format("Meta id '%s' not found", match.getMetaId())));
        return new Scorecard("success", meta, match);
    }
}
