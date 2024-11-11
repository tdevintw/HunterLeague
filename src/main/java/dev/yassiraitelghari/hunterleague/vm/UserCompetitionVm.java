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
public class UserCompetitionVm {
    private UUID user_id ;
    private String competition_code ;

}
