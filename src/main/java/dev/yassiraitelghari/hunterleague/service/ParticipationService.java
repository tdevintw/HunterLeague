package dev.yassiraitelghari.hunterleague.service;

import dev.yassiraitelghari.hunterleague.domain.Competition;
import dev.yassiraitelghari.hunterleague.domain.Participation;
import dev.yassiraitelghari.hunterleague.domain.User;
import dev.yassiraitelghari.hunterleague.exceptions.CompetitionClosedException;
import dev.yassiraitelghari.hunterleague.exceptions.ExpiredUserException;
import dev.yassiraitelghari.hunterleague.exceptions.InvalidParamInputException;
import dev.yassiraitelghari.hunterleague.exceptions.UserWithUUIDNotFound;
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
        }
        Participation participation = new Participation();
        participation.setCompetition(competition.get());
        participation.setUser(user.get());
       return  participationRepository.save(participation);
        //gotta add :if the competition reach max players it will be closed .

    }
}
