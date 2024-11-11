package dev.yassiraitelghari.hunterleague.service;

import dev.yassiraitelghari.hunterleague.domain.Species;
import dev.yassiraitelghari.hunterleague.mapper.SpeciesMapper;
import dev.yassiraitelghari.hunterleague.repository.SpeciesRepository;
import dev.yassiraitelghari.hunterleague.vm.SpeciesVm;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpeciesService {

    private final SpeciesRepository speciesRepository;
    private final SpeciesMapper speciesMapper;

    public List<Species> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return speciesRepository.findAll(pageable).stream().collect(Collectors.toList());
    }

    public Optional<Species> add(SpeciesVm speciesVm) {
        Optional<Species> speciesOptional = speciesRepository.findByName(speciesVm.getName());
        if (speciesOptional.isEmpty()) {
            Species species = speciesMapper.speciesVmToSpecies(speciesVm);
            speciesRepository.save(species);
        }
        return speciesOptional;
    }

    public Optional<Species> edit(UUID uuid, SpeciesVm speciesVm) {
        Optional<Species> speciesOptional = speciesRepository.findById(uuid);
        if (speciesOptional.isPresent()) {
            Species species = speciesMapper.speciesVmToSpecies(speciesVm);
            species.setId(uuid);
            speciesRepository.save(species);
        }
        return speciesOptional;
    }

    public void delete(UUID uuid) {
        try {
            speciesRepository.delete(uuid);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

}
