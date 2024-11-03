package dev.yassiraitelghari.hunterleague.mapper;

import dev.yassiraitelghari.hunterleague.domain.User;
import dev.yassiraitelghari.hunterleague.dto.UserDTO;
import dev.yassiraitelghari.hunterleague.vm.UpdatedUserVM;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO userToUserDTO(User user);

    UpdatedUserVM userToUpdatedUserVM(User user);
}
