package dev.yassiraitelghari.hunterleague.controller.v1;

import dev.yassiraitelghari.hunterleague.domain.User;
import dev.yassiraitelghari.hunterleague.dto.UpdateUserDTO;
import dev.yassiraitelghari.hunterleague.dto.UserDTO;
import dev.yassiraitelghari.hunterleague.mapper.UserMapper;
import dev.yassiraitelghari.hunterleague.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    @GetMapping("/findByName")
    public ResponseEntity<?> searchByName(@RequestParam String name, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        if (page < 1 || size < 1) {
            return ResponseEntity.badRequest().body("Size and page values are incorrect");
        }
        List<User> users = userService.findByName(name, page - 1, size);
        List<UserDTO> mappedUsers = users.stream().map(userMapper::userToUserDTO).toList();
        if (users.isEmpty()) {
            return ResponseEntity.badRequest().body("There is no users with this name");
        } else {
            return ResponseEntity.ok(mappedUsers);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteByUUID(@PathVariable UUID id) {
        try {
            System.out.println("hello");
            userService.deleteUserById(id);
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok("User was Deleted");

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestParam UpdateUserDTO userDTO) {
        try {
            userService.updateUser(id, userDTO);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("User was updated");
    }

}
