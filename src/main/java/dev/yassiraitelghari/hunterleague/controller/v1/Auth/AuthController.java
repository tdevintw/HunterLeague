package dev.yassiraitelghari.hunterleague.controller.v1.Auth;

import dev.yassiraitelghari.hunterleague.service.UserService;
import dev.yassiraitelghari.hunterleague.vm.FrontToBusiness.UserVm;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserVm userVm) {
        if (userService.findUserByUsername(userVm.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("The username already exist");
        } else {
            userService.save(userVm);
        }
        return ResponseEntity.status(200).body("User registered");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserVm userVm) {
        if (userService.findUserByUsernameAndPassword(userVm.getUsername(), userVm.getPassword()).isPresent()) {
            return ResponseEntity.status(200).body("Welcome Back " + userVm.getUsername());
        } else {
            return ResponseEntity.status(404).body("Username or password are incorrect");
        }
    }
}
