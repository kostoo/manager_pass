package com.managerPass.service.auth;

import com.managerPass.entity.Enum.ERole;
import com.managerPass.entity.RoleEntity;
import com.managerPass.entity.UserEntity;
import com.managerPass.entity.UserSecurity;
import com.managerPass.entity.ValidateTokenEntity;
import com.managerPass.mail.AppMailSender;
import com.managerPass.payload.request.LoginRequest;
import com.managerPass.payload.request.SignupRequest;
import com.managerPass.payload.response.JwtToken;
import com.managerPass.payload.response.MessageResponse;
import com.managerPass.payload.response.RegistrationResponse;
import com.managerPass.repository.RoleRepository;
import com.managerPass.repository.UserEntityRepository;
import com.managerPass.config.security.JwtUtils;
import com.managerPass.service.ValidateTokenRegisterEntityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserEntityRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;
    private final AppMailSender mailSender;
    private final ValidateTokenRegisterEntityService validateTokenRegisterEntityService;

    @Value("${app.urlToRegister}")
    private String urlToRegister;

    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserSecurity userDetails = (UserSecurity) authentication.getPrincipal();

        if (!userDetails.isAccountActive() || !userDetails.isEnabled()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Account not active"));
        }

        return ResponseEntity.ok(new JwtToken(jwt));
    }

    public ResponseEntity<?> registerUser(SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            log.warn(String.format("Username %s is already taken!", signUpRequest.getUsername()));

            return ResponseEntity.badRequest().body(
               new MessageResponse(String.format("Username %s is already taken!", signUpRequest.getUsername())
            ));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            log.warn(String.format("Email %s is already in use!", signUpRequest.getEmail()));

            return ResponseEntity.badRequest().body(new MessageResponse(
                    String.format("Email %s is already in use!", signUpRequest.getEmail())
            ));
        }

        UserEntity user = new UserEntity(
                signUpRequest.getUsername(), signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword())
        );

        Set<ERole> strRoles = signUpRequest.getRole();
        Set<RoleEntity> roles = new HashSet<>();

        if (strRoles == null) {
            RoleEntity userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() ->
                    new RuntimeException(String.format("Error: Role %s is not found", ERole.ROLE_USER))
            );

            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case ROLE_ADMIN:
                        RoleEntity adminRole = roleRepository.findByName(ERole.ROLE_ADMIN).orElseThrow(() ->
                                new RuntimeException(String.format("Error: Role %s is not found", ERole.ROLE_ADMIN))
                        );
                        roles.add(adminRole);

                        break;
                    default:
                        RoleEntity userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() ->
                                new RuntimeException(String.format("Error: Role %s is not found", ERole.ROLE_USER))
                        );
                        roles.add(userRole);

                        break;
                }
           });
       }
        user.setRoles(roles);
        user = userRepository.save(user);

        String generatedTokenRegister = UUID.randomUUID().toString();

        ValidateTokenEntity validateTokenEntity = new ValidateTokenEntity();

        Date expiryDateToken = validateTokenEntity.calculateExpiryDate();

        validateTokenEntity.setToken(generatedTokenRegister);
        validateTokenEntity.setExpiryDate(expiryDateToken);
        validateTokenEntity.setUserEntity(user);

        validateTokenEntity = validateTokenRegisterEntityService.addToken(validateTokenEntity);


        mailSender.sendEmail(signUpRequest.getEmail(), "activate user",
                  String.format("Please activate your account %s %s",  urlToRegister, validateTokenEntity.getToken())
        );

        return ResponseEntity.ok(new RegistrationResponse(validateTokenEntity.getToken()));
    }

    public ResponseEntity<MessageResponse> activateUser(String token) {
        ValidateTokenEntity validateTokenEntity = validateTokenRegisterEntityService.findByToken(token);
        UserEntity userEntity = validateTokenEntity.getUserEntity();

        if (validateTokenEntity.getExpiryDate().after(new Date())) {
            userEntity.setIsAccountActive(true);
       } else {
            return ResponseEntity.badRequest().body(new MessageResponse("token expired"));
        }

       userRepository.save(userEntity);

       return ResponseEntity.ok(new MessageResponse("user activated"));
    }
}
