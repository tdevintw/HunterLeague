package dev.yassiraitelghari.hunterleague.mapper;

import dev.yassiraitelghari.hunterleague.domain.User;
import dev.yassiraitelghari.hunterleague.dto.ModelToBusiness.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO userToUserDTO(User user);
}
