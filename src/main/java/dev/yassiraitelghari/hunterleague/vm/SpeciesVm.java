package dev.yassiraitelghari.hunterleague.vm;

import dev.yassiraitelghari.hunterleague.domain.enums.Difficulty;
import dev.yassiraitelghari.hunterleague.domain.enums.SpeciesType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class SpeciesVm {

    private String name;

    private SpeciesType category;

    private Double minimumWeight;

    private Difficulty difficulty;

    private Integer points;

}
