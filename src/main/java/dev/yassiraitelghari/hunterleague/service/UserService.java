package dev.yassiraitelghari.hunterleague.service;

import dev.yassiraitelghari.hunterleague.domain.User;
import dev.yassiraitelghari.hunterleague.domain.enums.Role;
import dev.yassiraitelghari.hunterleague.repository.UserRepository;
import dev.yassiraitelghari.hunterleague.vm.FrontToBusiness.UserVm;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            return Optional.empty();
        } else {
            return user;
        }
    }

    public User save(UserVm userVm) {

        User user = new User();
        user.setUsername(userVm.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(userVm.getPassword()));
        user.setRole(Role.MEMBER);
        user.setFirstName("DefaultFirstName");
        user.setLastName("DefaultLastName");
        user.setCin("12345678");
        user.setEmail("default@example.com");
        user.setNationality("Unknown");
        user.setJoinDate(LocalDateTime.now());
        user.setLicenseExpirationDate(LocalDateTime.now().plusYears(1));

        return userRepository.save(user);
    }

    public Optional<User> findUserByUsernameAndPassword(String username, String password) {

        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            boolean isPasswordMatch = bCryptPasswordEncoder.matches(password , user.get().getPassword());
            if (isPasswordMatch) {
                return user;
            }
        }
        return Optional.empty();
    }

    public Optional<User> findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public List<User> findByName(String name , int page , int size){
        Pageable pageable = PageRequest.of(page , size);
        return userRepository.findByFirstNameLike("%"+name+"%" , pageable);
    }
}
