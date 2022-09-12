package com.managerPass.payload.request;

import com.managerPass.jpa.entity.Enum.ERole;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
public class SignupRequest {

    @Size(min = 5, max = 20)
    private String username;

    @Size(min = 5, max = 50)
    @Email
    private String email;

    private Set<ERole> role;

    @Size(min = 5, max = 40)
    private String password;

}
