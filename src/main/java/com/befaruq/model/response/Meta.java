package com.befaruq.model.response;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Meta{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long metaId;
    private String dataVersion;
    private String created;
    private int revision;
}