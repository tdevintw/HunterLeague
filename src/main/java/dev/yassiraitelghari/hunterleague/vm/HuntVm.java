package dev.yassiraitelghari.hunterleague.vm;

import dev.yassiraitelghari.hunterleague.domain.Species;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HuntVm {
    private UUID species_id ;
    private Double weight ;
}
