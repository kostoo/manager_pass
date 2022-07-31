package com.managerPass.payload.request;

import com.managerPass.entity.Enum.ERole;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import java.util.Set;

@Getter
@Setter
public class SignupRequest {

    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    private Set<ERole> role;

    @NotBlank
    @Size(max = 40)
    private String password;

}
