package dev.yassiraitelghari.hunterleague.vm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class FindParticipationByUserVm {

    private UUID user_id;
    private UUID competition_id;
}
