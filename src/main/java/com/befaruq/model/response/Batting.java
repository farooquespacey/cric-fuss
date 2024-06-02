package com.befaruq.model.response;

import com.befaruq.model.request.Wicket;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Batting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long battingId;
    @ManyToOne
    @JoinColumn(name = "inning_id")
    private Inning inning;
    private String player;
    private int runs;
    private int balls;
    private int fours;
    private int sixes;
    private float strikeRate;
    @OneToOne(cascade = CascadeType.ALL)
    private State state;
    
    public Batting(String player, Inning inning) {
        this.player = player;
        this.inning = inning;
        this.state = new State();
    }
    
    public void addRuns(int runToAdd) {
        this.runs += runToAdd;
    }
    
    public void incrementBalls() {
        this.balls++;
    }

    public void incrementFours() {
        this.fours++;
    }
    
    public void incrementSixes() {
        this.sixes++;
    }

    public void updateWicket(String bowler, Wicket wicket) {
        this.state.bowledBy = bowler;
        this.state.stateName = wicket.kind; // lbw, caught, bowled
        if (wicket.fielders != null && !wicket.fielders.isEmpty()) {
            if ("caught".equals(wicket.kind))
                this.state.caughtBy = wicket.fielders.get(0).name; // TODO: assumption of array of 1 val
            else if ("runout".equals(wicket.kind)) // TODO: assumption of runout which is not found in Schema file
                this.state.runOutBy = wicket.fielders.get(0).name;
        }
    }

    @JsonBackReference
    public Inning getInning() {
        return inning;
    }
}