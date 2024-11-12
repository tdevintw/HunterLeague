package dev.yassiraitelghari.hunterleague.controller.v1;

import dev.yassiraitelghari.hunterleague.domain.Participation;
import dev.yassiraitelghari.hunterleague.dto.HuntDTO;
import dev.yassiraitelghari.hunterleague.dto.UserDTO;
import dev.yassiraitelghari.hunterleague.mapper.HuntMapper;
import dev.yassiraitelghari.hunterleague.mapper.UserMapper;
import dev.yassiraitelghari.hunterleague.service.ParticipationService;
import dev.yassiraitelghari.hunterleague.vm.FindParticipationByUserVm;
import dev.yassiraitelghari.hunterleague.vm.ParticipationResultVm;
import dev.yassiraitelghari.hunterleague.vm.ParticipationVm;
import dev.yassiraitelghari.hunterleague.vm.UserCompetitionVm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/participation")
@RequiredArgsConstructor
public class ParticipationController {

    private final ParticipationService participationService;
    private final UserMapper userMapper;
    private final HuntMapper huntMapper;

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

    @PatchMapping("/saveScore")
    public ResponseEntity<?> saveScore(@RequestBody ParticipationVm participationVm) {
        Participation participation;
        try {
            participation = participationService.saveScore(participationVm);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        UserDTO user = userMapper.userToUserDTO(participation.getUser());
        List<HuntDTO> huntsDTO = participation.getHunts().stream().map(huntMapper::HuntToHuntDTO).toList();
        ParticipationResultVm participationResultVm = new ParticipationResultVm(user, participation.getCompetition().getCode(), huntsDTO, participation.getScore());
        return ResponseEntity.ok().body(participationResultVm);
    }

    @GetMapping("/findByUser")
    public ResponseEntity<?> findByUser(@RequestBody FindParticipationByUserVm findParticipationByUserVm) {
        ParticipationResultVm participationResultVm;
        try {
            participationResultVm = participationService.findByUser(findParticipationByUserVm.getUser_id(), findParticipationByUserVm.getCompetition_id());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().body(participationResultVm);
    }
}
