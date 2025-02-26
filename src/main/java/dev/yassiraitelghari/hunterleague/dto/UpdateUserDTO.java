package dev.yassiraitelghari.hunterleague.dto;

import dev.yassiraitelghari.hunterleague.domain.enums.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@Builder
public class UpdateUserDTO {
    private String username;

    private String password;

    private Role role;

    private String firstName;

    private String lastName;

    private String cin;

    private String email;

    private String nationality;

    private LocalDateTime joinDate;

    private LocalDateTime licenseExpirationDate;

}
