package dev.yassiraitelghari.hunterleague.dto;

import dev.yassiraitelghari.hunterleague.domain.enums.SpeciesType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CompetitionDTO {
    private String code;

    private String location;

    private LocalDateTime date;

    private SpeciesType speciesType;

    private Integer minParticipants;

    private Integer maxParticipants;

    private Boolean openRegistration;
}
