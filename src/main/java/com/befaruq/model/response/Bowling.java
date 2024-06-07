package com.befaruq.model.response;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bowling {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bowlingId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inning_id")
    private Inning inning;
    private String player;
    @JsonIgnore
    private int balls;
    @JsonIgnore
    private int ballsPerOver;
    private float overs;
    private int maidens;
    private int runsConceded;
    private int wickets;
    private float economy;
    private int zeros;
    private int fours;
    private int sixes;
    private int noBalls;
    private int wides;

    public Bowling(String player, Inning inning, int ballsPerOver) {
        this.player = player;
        this.inning = inning;
        this.ballsPerOver = ballsPerOver;
    }

    public void incrementBalls() {
        this.balls++;
        this.overs = (this.balls / this.ballsPerOver) + (this.balls % this.ballsPerOver) / 10.0f;
    }

    public void addRunsConceded(int total) {
        this.runsConceded += total;
    }

    public void addWides(int wides) {
        this.wides += wides;
    }

    public void addNoBalls(int noballs) {
        this.noBalls += noballs;
    }

    public void addWickets(int wickets) {
        this.wickets += wickets;
    }
    
    @JsonBackReference
    public Inning getInning() {
        return inning;
    }

    public void incrementFours() {
        this.fours++;
    }

    public void incrementSixes() {
        this.sixes++;
    }

    public void incrementZeros() {
        this.zeros++;
    }

    public void incrementMaidens() {
        this.maidens++;
    }
}