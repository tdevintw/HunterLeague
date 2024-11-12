package dev.yassiraitelghari.hunterleague.service;

import dev.yassiraitelghari.hunterleague.domain.Competition;
import dev.yassiraitelghari.hunterleague.domain.Participation;
import dev.yassiraitelghari.hunterleague.domain.User;
import dev.yassiraitelghari.hunterleague.exceptions.*;
import dev.yassiraitelghari.hunterleague.repository.ParticipationRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ParticipationService {
    private final ParticipationRepository participationRepository;
    private final UserService userService;
    private final CompetitionService competitionService;

    public Participation add(UUID id, String code) {
        Optional<User> user = userService.findUserById(id);
        Optional<Competition> competition = competitionService.getByCode(code);
        if (user.isEmpty()) {
            throw new UserWithUUIDNotFound(id);
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
}
