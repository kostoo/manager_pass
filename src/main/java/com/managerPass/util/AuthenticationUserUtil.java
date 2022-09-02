package com.managerPass.util;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class AuthenticationUserUtil {

    public UserDetails getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return (UserDetails) authentication.getPrincipal();
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is UnAuthorized");
        }
    }
}
