package dev.yassiraitelghari.hunterleague.service;

import dev.yassiraitelghari.hunterleague.domain.User;
import dev.yassiraitelghari.hunterleague.domain.enums.Role;
import dev.yassiraitelghari.hunterleague.dto.UpdateUserDTO;
import dev.yassiraitelghari.hunterleague.dto.UserDTO;
import dev.yassiraitelghari.hunterleague.exceptions.UserWithUUIDNotFound;
import dev.yassiraitelghari.hunterleague.repository.UserRepository;
import dev.yassiraitelghari.hunterleague.vm.UpdatedUserVM;
import dev.yassiraitelghari.hunterleague.vm.UserVm;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
            boolean isPasswordMatch = bCryptPasswordEncoder.matches(password, user.get().getPassword());
            if (isPasswordMatch) {
                return user;
            }
        }
        return Optional.empty();
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> findByName(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findByFirstNameLike("%" + name + "%", pageable);
    }

    public void deleteUserById(UUID id) {
        if (userRepository.findById(id).isEmpty()) {
            throw new EmptyResultDataAccessException("There is no user with this UUID .", 1);
        }
        userRepository.deleteById(id);
    }

    public User updateUser(UUID id, UpdateUserDTO userDTO) {
        Optional<User> UserToUpdate = userRepository.findById(id);
        if (UserToUpdate.isEmpty()) {
             throw new UserWithUUIDNotFound(id);
        } else {
            User user = UserToUpdate.get();
            user.setUsername(userDTO.getUsername());
            user.setPassword(userDTO.getPassword());
            user.setRole(userDTO.getRole());
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setCin(userDTO.getCin());
            user.setEmail(userDTO.getEmail());
            user.setNationality(userDTO.getNationality());
            user.setJoinDate(userDTO.getJoinDate());
            user.setLicenseExpirationDate(userDTO.getLicenseExpirationDate());
            return userRepository.save(user);
        }
    }

    public UpdatedUserVM updateUserPartially(UUID id , UpdateUserDTO updateUserDTO){
        Optional<User> UserToUpdate = userRepository.findById(id);
        if (UserToUpdate.isEmpty()) {
            throw new UserWithUUIDNotFound(id);
        } else {
            User userToUpdate = UserToUpdate.get();
            Optional.ofNullable(updateUserDTO.getUsername()).ifPresent(userToUpdate::setUsername);
            Optional.ofNullable(updateUserDTO.getPassword()).ifPresent(userToUpdate::setPassword);
            Optional.ofNullable(updateUserDTO.getRole()).ifPresent(userToUpdate::setRole);
            Optional.ofNullable(updateUserDTO.getFirstName()).ifPresent(userToUpdate::setFirstName);
            Optional.ofNullable(updateUserDTO.getLastName()).ifPresent(userToUpdate::setLastName);
            Optional.ofNullable(updateUserDTO.getCin()).ifPresent(userToUpdate::setCin);
            Optional.ofNullable(updateUserDTO.getEmail()).ifPresent(userToUpdate::setEmail);
            Optional.ofNullable(updateUserDTO.getNationality()).ifPresent(userToUpdate::setNationality);
            Optional.ofNullable(updateUserDTO.getJoinDate()).ifPresent(userToUpdate::setJoinDate);
            Optional.ofNullable(updateUserDTO.getLicenseExpirationDate()).ifPresent(userToUpdate::setLicenseExpirationDate);
       UpdatedUserVM updatedUserVM =  userRepository.save(userToUpdate) ;
        }

    }
}
