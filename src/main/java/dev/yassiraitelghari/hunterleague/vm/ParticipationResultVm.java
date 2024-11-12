package dev.yassiraitelghari.hunterleague.vm;

import dev.yassiraitelghari.hunterleague.domain.Hunt;
import dev.yassiraitelghari.hunterleague.domain.User;
import dev.yassiraitelghari.hunterleague.dto.HuntDTO;
import dev.yassiraitelghari.hunterleague.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ParticipationResultVm {
    private UserDTO user;
    private String competition_code;
    private List<HuntDTO> hunts;
    private double score;
}
