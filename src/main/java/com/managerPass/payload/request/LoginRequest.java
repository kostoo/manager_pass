package com.managerPass.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
public class LoginRequest {

    @Size(min = 5, max = 20)
    private String username;

    @Size(min = 5, max = 20)
    private String password;

}
