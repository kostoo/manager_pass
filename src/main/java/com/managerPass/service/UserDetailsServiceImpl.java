package com.managerPass.service;

import com.managerPass.jpa.entity.UserEntity;
import com.managerPass.jpa.entity.UserSecurity;
import com.managerPass.jpa.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserEntityRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format("User Not Found with username: %s", username))
        );

        return UserSecurity.buildUserSecurity(user);
    }
}
