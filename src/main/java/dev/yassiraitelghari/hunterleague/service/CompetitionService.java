package dev.yassiraitelghari.hunterleague.service;

import dev.yassiraitelghari.hunterleague.domain.Competition;
import dev.yassiraitelghari.hunterleague.dto.CompetitionDTO;
import dev.yassiraitelghari.hunterleague.exceptions.InvalidParticipationRangeException;
import dev.yassiraitelghari.hunterleague.mapper.CompetitionMapper;
import dev.yassiraitelghari.hunterleague.repository.CompetitionRepository;
import dev.yassiraitelghari.hunterleague.vm.CompetitionVm;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompetitionService {

    private final CompetitionRepository competitionRepository;
    private final CompetitionMapper competitionMapper;

    public List<CompetitionDTO> get(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Competition> competitions = competitionRepository.findAll(pageable).toList();
        return competitions.stream().map(competitionMapper::competitionToCompetitionDTO).collect(Collectors.toList());
    }

    public CompetitionVm add(CompetitionVm competitionVm) {
        if (competitionVm.getMinParticipants() > competitionVm.getMaxParticipants()) {
            throw new InvalidParticipationRangeException("Min Participation can't be greater then max participation");
        } else if (competitionVm.getMinParticipants() < 1) {
            throw new InvalidParticipationRangeException("Min participation can't be negative or null");
        } else {
            Competition competition = competitionMapper.competitionVmToCompetition(competitionVm);
            competition.setOpenRegistration(true);
            competition.setCode(competitionVm.getLocation() + "_" + LocalDateTime.now());
            competitionRepository.save(competition);
        }
        return competitionVm;
    }

    public List<CompetitionDTO> filterByDate(String order, int page, int size) {
        if (order.equalsIgnoreCase("asc")){
            Pageable pageable = PageRequest.of(page, size);
            return competitionRepository.findAllOrderByDateAsc(pageable).stream().map(competitionMapper::competitionToCompetitionDTO).toList();
        }
    }
}
