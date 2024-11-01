package dev.yassiraitelghari.hunterleague.vm.FrontToBusiness;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserVm {
    @NotBlank(message = "username cant be empty")
    @NotNull(message = "username cant be null")
    @Size(min = 3 , max = 10 , message = "Username Must Between 3 to 10 Characters")
    private String username ;

    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*]).{8,}$",
            message = "Password must be at least 8 characters long and include at least one digit, one uppercase letter, one lowercase letter, and one special character."
    )
    private String password ;
}
