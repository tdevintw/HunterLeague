package dev.yassiraitelghari.hunterleague.controller.v1;

import dev.yassiraitelghari.hunterleague.domain.Species;
import dev.yassiraitelghari.hunterleague.mapper.SpeciesMapper;
import dev.yassiraitelghari.hunterleague.service.SpeciesService;
import dev.yassiraitelghari.hunterleague.vm.SpeciesVm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/species")
@RequiredArgsConstructor
public class SpeciesController {

    private final SpeciesService speciesService;
    private final SpeciesMapper speciesMapper;


    @GetMapping("/")
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "20") int size) {
        if (page < 1 || size < 1) {
            return ResponseEntity.badRequest().body("Size or Page cant be less then 1");
        }
        List<Species> species = speciesService.getAll(page, size);
        List<SpeciesVm> speciesFormatted = species.stream().map(speciesMapper::speciesToSpeciesVm).toList();
        return ResponseEntity.ok(speciesFormatted);

    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody SpeciesVm speciesVm) {
        Optional<Species> species = speciesService.add(speciesVm) ;
        if(species.isEmpty()){
            return  ResponseEntity.ok().body(speciesVm);
        }else{
            return ResponseEntity.badRequest().body("The Specie name already exist");
        }
    }

    @PutMapping("/edit/{uuid}")
    public ResponseEntity<?> edit(@PathVariable UUID uuid , @RequestBody SpeciesVm speciesVm){
        Optional<Species> speciesOptional =  speciesService.edit(uuid , speciesVm) ;
        if(speciesOptional.isEmpty()){
            return ResponseEntity.badRequest().body("Specie not found") ;
        }else{
            return ResponseEntity.ok().body(speciesVm) ;
        }
    }

    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity<?> delete(@PathVariable UUID uuid){

        speciesService.delete(uuid) ;
        return  ResponseEntity.ok().body("Specie was deleted") ;
    }
}
