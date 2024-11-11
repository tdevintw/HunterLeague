package dev.yassiraitelghari.hunterleague.controller.v1;

import dev.yassiraitelghari.hunterleague.domain.Participation;
import dev.yassiraitelghari.hunterleague.service.ParticipationService;
import dev.yassiraitelghari.hunterleague.vm.UserCompetitionVm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/participation")
@RequiredArgsConstructor
public class ParticipationController {

    private final ParticipationService participationService;

    @PostMapping("/add")
    public ResponseEntity<?> addParticipation(@RequestBody UserCompetitionVm userCompetitionVm) {
        Participation participation = null;
        try {
            participation = participationService.add(userCompetitionVm.getUser_id(), userCompetitionVm.getCompetition_code());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().body("You have been registered to the competition UUID : " + participation.getId());
    }

}
