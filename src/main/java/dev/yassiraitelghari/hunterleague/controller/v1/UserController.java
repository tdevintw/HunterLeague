package dev.yassiraitelghari.hunterleague.controller.v1;

import dev.yassiraitelghari.hunterleague.domain.User;
import dev.yassiraitelghari.hunterleague.dto.ModelToBusiness.UserDTO;
import dev.yassiraitelghari.hunterleague.mapper.UserMapper;
import dev.yassiraitelghari.hunterleague.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/findByEmail")
    public ResponseEntity<?> searchByEmail(@RequestParam String email) {

        Optional<User> user = userService.findUserByEmail(email);
        if (user.isEmpty()) {
            return ResponseEntity.status(400).body("There is no User with this email");
        } else {
            UserDTO userDTO = userMapper.userToUserDTO(user.get());
            return ResponseEntity.ok(userDTO);
        }

    }
}
