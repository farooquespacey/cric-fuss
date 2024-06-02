package com.befaruq.model.response;

import com.befaruq.model.request.Delivery;
import com.befaruq.model.request.Over;
import com.befaruq.model.request.Wicket;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class Inning {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inningId;

    @ManyToOne
    @JoinColumn(name = "match_id")
    private Match match;
    
    @OneToMany(mappedBy = "inning", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Batting> battingList;
    @OneToMany(mappedBy = "inning", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Bowling> bowlingList;

    private String battingTeam;
    private String bowlingTeam;

    private int totalRuns;
    private int totalWickets;
    private float overs;
    private float runRate;
    
    public void generateBattingScorecard(ArrayList<Over> overs) {
        Map<String, Batting> battingScorecard = new LinkedHashMap<>();
        
        for (Over over : overs) {
            for (Delivery delivery : over.deliveries) {
                String batterName = delivery.batter;

                // Update batter stats
                battingScorecard.putIfAbsent(batterName, new Batting(batterName, this));
                if (delivery.non_striker != null && !delivery.non_striker.isEmpty()) // this ensures that ordering of batting is not tampered
                    battingScorecard.putIfAbsent(delivery.non_striker, new Batting(delivery.non_striker, this));
                Batting batting = battingScorecard.get(batterName);
                
                batting.addRuns(delivery.runs.batter);

                if (delivery.extras == null || (delivery.extras.wides == 0)) 
                    batting.incrementBalls();

                if (delivery.runs.batter == 4) {
                    batting.incrementFours();
                } else if (delivery.runs.batter == 6) {
                    batting.incrementSixes();
                }

                // Update wicket if batter is out
                for (Wicket wicket : delivery.wickets) {
                    if (wicket.player_out.equals(batterName)) {
                        batting.updateWicket(delivery.bowler, wicket);
                        this.totalWickets++;
                    }
                }
                
                // Update inning stat
                this.totalRuns += delivery.runs.total;
            }
        }
        updateStrikeRates(battingScorecard);
        this.battingList = new ArrayList<>(battingScorecard.values());
    }

    void updateStrikeRates(Map<String, Batting> battingMap) {
        for (Batting batting : battingMap.values()) {
            int runs = batting.getRuns();
            int ballsFaced = batting.getBalls();
            if (ballsFaced > 0) {
                float strikeRate = (runs / (float) ballsFaced) * 100;
                batting.setStrikeRate(limitDecPointsWithNoRounding(strikeRate)); // restrict to just 2 decimal places without rounding
            } else {
                batting.setStrikeRate(0.00f); // Handle case where ballsFaced is zero
            }
        }
    }

    public void generateBowlingScorecard(ArrayList<Over> overs) {
        Map<String, Bowling> bowlingScorecard = new LinkedHashMap<>();

        int ballsInLastOver = 0;
        for (Over over : overs) {
            int maiden = match.getBallsPerOver(); // using int instead of bool to ignore accounting for cases like bowler having bowled less than 6 balls with all dots
            for (Delivery delivery : over.deliveries) {
                String bowlerName = delivery.bowler;

                // Update bowler stats
                bowlingScorecard.putIfAbsent(bowlerName, new Bowling(bowlerName, this, match.getBallsPerOver()));
                Bowling bowling = bowlingScorecard.get(bowlerName);

                if (delivery.extras == null || (delivery.extras.wides == 0 && delivery.extras.noballs == 0)) // TODO: no_ball not found in schema
                    bowling.incrementBalls(); // takes into account the valid balls only (i.e., legbyes included)
                
                if ((delivery.extras == null || delivery.extras.legbyes == 0) && delivery.runs.total > 0)
                    bowling.addRunsConceded(delivery.runs.total); // excluding legbyes, all other runs gets added into bowler's account
                    
                if (delivery.extras != null) {
                    bowling.addWides(delivery.extras.wides);
                    bowling.addNoBalls(delivery.extras.noballs);
                }

                if (delivery.runs.batter == 4) {
                    bowling.incrementFours();
                } else if (delivery.runs.batter == 6) {
                    bowling.incrementSixes();
                } else if (delivery.runs.total == 0 || (delivery.extras != null && delivery.extras.legbyes > 0)) {
                    bowling.incrementZeros(); // it is still considered 0s when legbyes are taken
                    maiden--;
                }

                // Update wickets
                bowling.addWickets(delivery.wickets.size());
                
                ballsInLastOver = bowling.getBalls() % match.getBallsPerOver();
            }
            if (maiden == 0) bowlingScorecard.get(over.deliveries.get(0).bowler).incrementMaidens();
        }
        
        updateEconomy(bowlingScorecard);
        this.bowlingList = new ArrayList<>(bowlingScorecard.values());
        this.overs = ballsInLastOver != 0 ? (overs.size()-1) + (ballsInLastOver / 10.0f) : overs.size();
        updateRunRate();
    }

    private void updateEconomy(Map<String, Bowling> bowlingScorecard) {
        bowlingScorecard.values().
                forEach(bowling -> 
                        bowling.setEconomy(
                                limitDecPointsWithNoRounding(
                                        ((float) bowling.getRunsConceded() * match.getBallsPerOver()) / bowling.getBalls())));
    }

    private void updateRunRate() {
        float rr = (this.totalRuns == 0) ? 0f : (((float) this.totalRuns) / oversToBalls(this.overs)) * match.getBallsPerOver(); // handle edge case of 0 run by a team
        this.runRate = limitDecPointsWithNoRounding(rr); // to restrict to 2 decimal places without rounding
    }
    
    private float limitDecPointsWithNoRounding(float f) {
        return (float) (Math.floor(f * 100) / 100);
    }
    
    private int oversToBalls(float overs) {
        String num = String.valueOf(overs);
        int dotIndex = num.indexOf(".");
        return (Integer.parseInt(num.substring(0, dotIndex)) * match.getBallsPerOver()) + Integer.parseInt(num.substring(dotIndex+1));
    }

    @JsonBackReference
    public Match getMatch() {
        return match;
    }

    @JsonManagedReference
    public List<Batting> getBattingList() {
        return battingList;
    }

    @JsonManagedReference
    public List<Bowling> getBowlingList() {
        return bowlingList;
    }
}