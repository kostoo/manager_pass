package com.managerPass.service.auth;

import com.managerPass.entity.Enum.ERole;
import com.managerPass.entity.RoleEntity;
import com.managerPass.entity.UserEntity;
import com.managerPass.entity.UserSecurity;
import com.managerPass.mail.Mail;
import com.managerPass.payload.request.LoginRequest;
import com.managerPass.payload.request.SignupRequest;
import com.managerPass.payload.response.JwtToken;
import com.managerPass.payload.response.MessageResponse;
import com.managerPass.repository.RoleRepository;
import com.managerPass.repository.UserEntityRepository;
import com.managerPass.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserEntityRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;
    private final Mail mailSender;

    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserSecurity userDetails = (UserSecurity) authentication.getPrincipal();

        if (!userDetails.isAccountActive() || !userDetails.isEnabled()){
            return ResponseEntity.badRequest().body(new MessageResponse("Account not active"));
        }

        return ResponseEntity.ok(new JwtToken(jwt));
    }

    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        UserEntity user = new UserEntity(signUpRequest.getUsername(), signUpRequest.getEmail(),
                                         encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<RoleEntity> roles = new HashSet<>();

        if (strRoles == null) {
            RoleEntity userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() ->
                    new RuntimeException("Error: Role is not found.")
            );

            roles.add(userRole);

        } else {
            strRoles.forEach(role -> {
                if ("admin".equals(role)) {
                    RoleEntity adminRole = roleRepository.findByName(ERole.ROLE_ADMIN).orElseThrow(() ->
                            new RuntimeException("Error: Role is not found.")
                    );
                    roles.add(adminRole);
                } else {
                    RoleEntity userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() ->
                            new RuntimeException("Error: Role is not found.")
                    );
                    roles.add(userRole);
                }
            });
        }

        mailSender.sendEmail(signUpRequest.getEmail(), "activate user",
                     "Please go to http://localhost:8082/api/activate/" + user.getUsername() +
                             " to activate your account");

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    public ResponseEntity<?> activateUser(String username){
       UserEntity userEntity =  userRepository.findByUsername(username).orElseThrow(()->
               new ResponseStatusException(HttpStatus.NOT_FOUND,"username not found")
       );

       if (!userEntity.getIsAccountActive()) {
           userEntity.setIsAccountActive(true);
       } else {
           return ResponseEntity.ok(new MessageResponse("user already activated"));
       }

       userRepository.save(userEntity);
       return ResponseEntity.ok(new MessageResponse("user activated"));
    }
}
