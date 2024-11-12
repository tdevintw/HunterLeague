package dev.yassiraitelghari.hunterleague.service;

import dev.yassiraitelghari.hunterleague.domain.Competition;
import dev.yassiraitelghari.hunterleague.domain.Hunt;
import dev.yassiraitelghari.hunterleague.domain.Participation;
import dev.yassiraitelghari.hunterleague.domain.User;
import dev.yassiraitelghari.hunterleague.exceptions.*;
import dev.yassiraitelghari.hunterleague.repository.ParticipationRepository;
import dev.yassiraitelghari.hunterleague.vm.ParticipationVm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ParticipationService {
    private final ParticipationRepository participationRepository;
    private final UserService userService;
    private final CompetitionService competitionService;
    private final HuntService huntService;

    public Participation add(UUID id, String code) {
        Optional<User> user = userService.findUserById(id);
        Optional<Competition> competition = competitionService.getByCode(code);
        if (user.isEmpty()) {
            throw new UserWithUUIDNotFoundException(id);
        } else if (competition.isEmpty()) {
            throw new InvalidParamInputException("There is no competition with this code");
        } else if (userService.isUserLicenseExpired(user.get())) {
            throw new ExpiredUserException("the user license was expired");
        } else if (!competition.get().getOpenRegistration()) {
            throw new CompetitionClosedException("This competition is no longer accept participation");
        } else if (competition.get().getMaxParticipants() == this.countParticipationOfACompetition(competition.get())) {
            competition.get().setOpenRegistration(false);
            competitionService.save(competition.get());
            throw new CompetitionMaxLimitException("Competition reached max participation");
        }
        Participation participation = new Participation();
        participation.setCompetition(competition.get());
        participation.setUser(user.get());
        return participationRepository.save(participation);

    }

    public int countParticipationOfACompetition(Competition competition) {
        return participationRepository.countParticipationByCompetition(competition);
    }

    public Participation saveScore(ParticipationVm participationVm) {
        List<Hunt> hunts;
        Participation participation;
        try {
            hunts = huntService.getHunts(participationVm.getHunts());
            participation = this.findById(participationVm.getParticipation_id()) ;
        } catch (SpeciesWithUUIDNotFoundException e) {
            throw new SpeciesWithUUIDNotFoundException();
        }
        participation.setHunts(hunts);
        participation.setScore(huntService.getTotalScore(hunts));
        return participationRepository.save(participation);
    }

    public Participation findById(UUID id) {
      return  participationRepository.findById(id).orElseThrow(ParticipationWithUUIDNotFoundException::new);
    }
}
