package com.befaruq.model.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matchId;
    private LocalDate date;
    private String city;
    private int overs;
    private int ballsPerOver;
    private String team1;
    private String team2;
    private String tossWinner;
    private String electedTo;
    private String result;
    private Long metaId;
    
    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Inning> innings;

    @JsonManagedReference
    public List<Inning> getInnings() {
        return innings;
    }
}