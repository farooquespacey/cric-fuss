package com.befaruq.service;

import com.befaruq.model.request.CricInfoRequest;
import com.befaruq.model.request.Outcome;
import com.befaruq.model.response.Inning;
import com.befaruq.model.response.Match;
import com.befaruq.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class MatchService {
    @Autowired
    private MatchRepository matchRepository;
    
    public Optional<Match> findById(Long id) {
        return matchRepository.findById(id);
    }

    public Long findMatch(LocalDate date, String city, String team1, String team2) {
        List<Match> m = matchRepository.findMatch(date, city, team1, team2);
        return (m.isEmpty()) ? null: m.get(0).getMatchId();
    }
    
    public Match save(CricInfoRequest ciReq, Long metaId) {
        Match match = buildMatchData(ciReq, metaId);
        Inning inning = buildInningData(match, ciReq, 0, 1);
        Inning inning2 = buildInningData(match, ciReq, 1, 0);
        match.setInnings(Arrays.asList(inning, inning2));
        return matchRepository.save(match);
    }

    private Inning buildInningData(Match match, CricInfoRequest ciReq, int battingIdx, int bowlingIdx) {
        Inning inning = Inning.builder().match(match)
                .battingTeam(ciReq.innings.get(battingIdx).team)
                .bowlingTeam(ciReq.innings.get(bowlingIdx).team)
                .build();
        inning.generateBattingScorecard(ciReq.innings.get(battingIdx).overs);
        inning.generateBowlingScorecard(ciReq.innings.get(battingIdx).overs);
        return inning;
    }

    private Match buildMatchData(CricInfoRequest ciReq, Long metaId) {
        Match match = Match.builder().metaId(metaId)
                .date(LocalDate.parse(ciReq.info.dates.get(0)))
                .city(ciReq.info.city)
                .team1(ciReq.info.teams.get(0))
                .team2(ciReq.info.teams.get(1))
                .overs(ciReq.info.overs)
                .ballsPerOver(ciReq.info.balls_per_over)
                .tossWinner(ciReq.info.toss.winner)
                .electedTo(ciReq.info.toss.decision)
                .result(evaluateResult(ciReq.info.outcome))
                .build();
        return match;
    }

    private String evaluateResult(Outcome outcome) {
        return outcome.winner + " won by " +
                ((outcome.by.runs != 0) ? outcome.by.runs + " runs" : outcome.by.wickets + " wickets");
    }

    // Bad design to grab everything from DB and performing filters, instead ask DB to do so.
//    public List<Batting> getTopNScorer(Long inningId, int topNum) {
//        Match m = matchRepository.findById(matchId).orElseThrow(() -> new RuntimeException("matchId not found"));
//        Inning mi = m.getInnings().stream().filter(i -> Objects.equals(i.getInningId(), inningId)).findFirst().orElseThrow(() -> new RuntimeException("inningId not found"));
//        return mi.getBattingList().stream().sorted(Comparator.comparingInt(Batting::getRuns).reversed()).toList();
//    }

}
