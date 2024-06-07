package com.befaruq.model.response;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

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

    /**
     * The @JsonManagedReference in the <Parent> entity and the @JsonBackReference in the relevant <Child> entity will 
     * avoid circular calls by jackson by only considering calling reference from the former for serialization.
     */
    @JsonManagedReference
    public List<Inning> getInnings() {
        return innings;
    }
}