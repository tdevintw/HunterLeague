package dev.yassiraitelghari.hunterleague.service;

import dev.yassiraitelghari.hunterleague.domain.Hunt;
import dev.yassiraitelghari.hunterleague.domain.Participation;
import dev.yassiraitelghari.hunterleague.domain.Species;
import dev.yassiraitelghari.hunterleague.exceptions.SpecieWeightNotReachedException;
import dev.yassiraitelghari.hunterleague.exceptions.SpeciesWithUUIDNotFoundException;
import dev.yassiraitelghari.hunterleague.vm.HuntVm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HuntService {

    private final SpeciesService speciesService;

    public List<Hunt> getHunts(List<HuntVm> hunts , Participation participation) {

        List<Hunt> userHunts = new ArrayList<>();
        hunts.stream().forEach(huntVm -> {
            Species species = speciesService.find(huntVm.getSpecies_id()).orElseThrow(() -> new SpeciesWithUUIDNotFoundException());
            if (species.getMinimumWeight() > huntVm.getWeight()) {
                System.out.println("Hunt doesnt reach min weight");
//                throw new SpecieWeightNotReachedException();
            } else {
                Hunt hunt = new Hunt();
                hunt.setSpecies(species);
                hunt.setWeight(huntVm.getWeight());
                hunt.setParticipation(participation);
                userHunts.add(hunt);
            }

        });

        return userHunts;
    }

    public double getHuntScore(Hunt hunt) {
        Species species = hunt.getSpecies();

        return (species.getPoints()) + (species.getCategory().getValue()) + (species.getDifficulty().getValue());
    }

    public double getTotalScore(List<Hunt> hunts) {
        return hunts.stream().mapToDouble(hunt -> this.getHuntScore(hunt)).sum();
    }


}
