package dev.yassiraitelghari.hunterleague.controller.v1;

import dev.yassiraitelghari.hunterleague.domain.enums.SpeciesType;
import dev.yassiraitelghari.hunterleague.dto.CompetitionDTO;
import dev.yassiraitelghari.hunterleague.exceptions.InvalidParticipationRangeException;
import dev.yassiraitelghari.hunterleague.mapper.CompetitionMapper;
import dev.yassiraitelghari.hunterleague.service.CompetitionService;
import dev.yassiraitelghari.hunterleague.vm.CompetitionVm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/competitions/")
@RequiredArgsConstructor
public class CompetitionController {
    private final CompetitionService competitionService;
    private final CompetitionMapper competitionMapper;

    @GetMapping("/")
    public ResponseEntity<?> get(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {

        List<CompetitionDTO> competitionDTO = competitionService.get(--page, size);
        return ResponseEntity.ok().body(competitionDTO);
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody CompetitionVm competitionVm) {
        try {
            competitionService.add(competitionVm);
        } catch (InvalidParticipationRangeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().body("Competition was added");
    }

    @GetMapping("/filterBy/date")
    public ResponseEntity<?> filterByDate(@RequestParam(defaultValue = "desc") String order, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        List<CompetitionDTO> competitionsDTO = null;
        try {
            competitionsDTO = competitionService.filterByDate(order, page, size);
        } catch (InvalidParticipationRangeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().body(competitionsDTO);
    }

    @GetMapping("/filterBy/species")
    public ResponseEntity<?> filterBySpecie(@RequestParam(defaultValue = "BIRD") SpeciesType speciesType, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        List<CompetitionDTO> competitionsDTO = null;
        try {
            competitionsDTO = competitionService.filterBySpecie(speciesType ,page  , size);
        } catch (InvalidParticipationRangeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().body(competitionsDTO);
    }

    @GetMapping("/filterBy/code")
    public ResponseEntity<?> filterByCode(@RequestParam(defaultValue = "") String code) {
        CompetitionDTO competitionDTO = null;
        try {
            competitionDTO = competitionService.filterByCode(code);
        } catch (InvalidParticipationRangeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().body(competitionDTO);
    }

    @GetMapping("filterBy/status")
    private ResponseEntity<?> filterByStatus(@RequestParam(defaultValue = "true") boolean open, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        List<CompetitionDTO> competitionsDTO = null;
        try {
            competitionsDTO = competitionService.filterByStatus(open ,page  , size);
        } catch (InvalidParticipationRangeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().body(competitionsDTO);

    }
}
