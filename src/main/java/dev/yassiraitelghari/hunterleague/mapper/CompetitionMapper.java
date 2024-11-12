package dev.yassiraitelghari.hunterleague.mapper;

import dev.yassiraitelghari.hunterleague.domain.Competition;
import dev.yassiraitelghari.hunterleague.dto.CompetitionDTO;
import dev.yassiraitelghari.hunterleague.vm.CompetitionVm;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface CompetitionMapper {
    CompetitionVm competitionToCompetitionVm(Competition competition);

    Competition competitionVmToCompetition(CompetitionVm competitionVm);

    CompetitionDTO competitionToCompetitionDTO(Competition competition);
}
