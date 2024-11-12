package dev.yassiraitelghari.hunterleague.dto;

import dev.yassiraitelghari.hunterleague.domain.Species;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class HuntDTO {

    private Species species;

    private Double weight;

}
