package dev.yassiraitelghari.hunterleague.mapper;

import dev.yassiraitelghari.hunterleague.domain.Species;
import dev.yassiraitelghari.hunterleague.vm.SpeciesVm;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SpeciesMapper {
    SpeciesVm speciesToSpeciesVm(Species species) ;

    Species speciesVmToSpecies(SpeciesVm speciesVm) ;
}
