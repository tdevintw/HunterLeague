package dev.yassiraitelghari.hunterleague.vm;

import dev.yassiraitelghari.hunterleague.domain.Participation;
import dev.yassiraitelghari.hunterleague.domain.enums.SpeciesType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompetitionVm {



    private String location;

    private LocalDateTime date;

    private SpeciesType speciesType;

    private Integer minParticipants;

    private Integer maxParticipants;

}
