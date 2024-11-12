package dev.yassiraitelghari.hunterleague.service;

import dev.yassiraitelghari.hunterleague.domain.Competition;
import dev.yassiraitelghari.hunterleague.domain.enums.SpeciesType;
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
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompetitionService {

    private final CompetitionRepository competitionRepository;
    private final CompetitionMapper competitionMapper;

    public Optional<Competition> findById(UUID id){
        return competitionRepository.findById(id);
    }

    public List<CompetitionDTO> get(int page, int size) {
        Pageable pageable = PageRequest.of(--page, size);
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
        if (!order.equalsIgnoreCase("asc") && !order.equalsIgnoreCase("desc")) {
            throw new InvalidParticipationRangeException("Order can be either asc or desc");
        } else if (page <= 0 || size < 1) {
            throw new InvalidParticipationRangeException("page must be greater and size must be greater then 0");
        }

        Pageable pageable = PageRequest.of(--page, size);
        if (order.equalsIgnoreCase("asc")) {
            return competitionRepository.findAllOrderByDateAsc(pageable).stream().map(competitionMapper::competitionToCompetitionDTO).toList();
        } else {
            return competitionRepository.findAllOrderByDateDesc(pageable).stream().map(competitionMapper::competitionToCompetitionDTO).toList();
        }
    }

    public List<CompetitionDTO> filterBySpecie(SpeciesType speciesType, int page, int size) {
        if (page <= 0 || size < 1) {
            throw new InvalidParticipationRangeException("page must be greater or equal 0  and size must be greater then 0");
        }else if(!speciesType.equals(SpeciesType.BIRD) && !speciesType.equals(SpeciesType.SEA) && !speciesType.equals(SpeciesType.BIG_GAME)){
            throw new InvalidParticipationRangeException("Species Type are [SEA,BIRD,BIG_GAME]");
        }

        Pageable pageable = PageRequest.of(--page, size);
        return competitionRepository.findAllBySpeciesType(speciesType, pageable).stream().map(competitionMapper::competitionToCompetitionDTO).toList();
    }

    public CompetitionDTO filterByCode(String code) {
        if(code.isEmpty()){
            throw new InvalidParticipationRangeException("the code of the competition is required");

        }else
        if(!code.contains("-")){
            throw new InvalidParticipationRangeException("code format must be like : location_date");
        }
        Optional<Competition> competitionOptional = competitionRepository.findByCode(code);
        return competitionOptional.map(competitionMapper::competitionToCompetitionDTO).orElse(null);
    }

    public List<CompetitionDTO> filterByStatus(boolean status, int page, int size) {
        if(page<=0 || size <1){
            throw new InvalidParticipationRangeException("page must be greater or equal 0  and size msu be greater then 0");
        }

        Pageable pageable = PageRequest.of(--page, size);
        return competitionRepository.findAllByOpenRegistration(status, pageable).stream().map(competitionMapper::competitionToCompetitionDTO).toList();
    }

    public Optional<Competition> getByCode(String code){
        return competitionRepository.findByCode(code);
    }

    public Competition save(Competition competition){
        return competitionRepository.save(competition);
    }
}
