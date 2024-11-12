package dev.yassiraitelghari.hunterleague.vm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParticipationVm {
    private UUID participation_id ;
    private List<HuntVm> hunts ;
}
