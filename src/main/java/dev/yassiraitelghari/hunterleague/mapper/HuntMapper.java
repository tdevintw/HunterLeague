package dev.yassiraitelghari.hunterleague.mapper;

import dev.yassiraitelghari.hunterleague.domain.Hunt;
import dev.yassiraitelghari.hunterleague.dto.HuntDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HuntMapper {
    HuntDTO HuntToHuntDTO(Hunt hunt);
}
