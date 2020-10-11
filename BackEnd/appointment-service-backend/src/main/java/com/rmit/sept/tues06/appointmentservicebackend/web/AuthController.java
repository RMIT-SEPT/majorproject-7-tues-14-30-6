package com.rmit.sept.tues06.appointmentservicebackend.web;

import com.rmit.sept.tues06.appointmentservicebackend.exception.UserNotFoundException;
import com.rmit.sept.tues06.appointmentservicebackend.model.Customer;
import com.rmit.sept.tues06.appointmentservicebackend.model.ERole;
import com.rmit.sept.tues06.appointmentservicebackend.model.Role;
import com.rmit.sept.tues06.appointmentservicebackend.model.User;
import com.rmit.sept.tues06.appointmentservicebackend.payload.request.LoginRequest;
import com.rmit.sept.tues06.appointmentservicebackend.payload.request.SignupRequest;
import com.rmit.sept.tues06.appointmentservicebackend.payload.response.JwtResponse;
import com.rmit.sept.tues06.appointmentservicebackend.payload.response.MessageResponse;
import com.rmit.sept.tues06.appointmentservicebackend.repository.RoleRepository;
import com.rmit.sept.tues06.appointmentservicebackend.security.jwt.JwtUtils;
import com.rmit.sept.tues06.appointmentservicebackend.security.service.UserDetailsImpl;
import com.rmit.sept.tues06.appointmentservicebackend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Tag(name = "auth", description = "the user authentication API")

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    public static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = LoginRequest.class)),
                    @Content(mediaType = "application/xml", schema = @Schema(implementation = JwtResponse.class))}),
    })
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @Operation(summary = "Register user", description = "New users are customers by default", tags = {"auth"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = User.class)),
                    @Content(mediaType = "application/xml", schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "400", description = "Username or email is already taken", content = @Content),
    })
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        User usernameMatch = null;
        try {
            usernameMatch = userService.findByUsername(signUpRequest.getUsername());
        } catch (UserNotFoundException userNotFoundException) {
            logger.info(userNotFoundException.getMessage());
        }

        if (usernameMatch != null) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        User emailMatch = null;
        try {
            emailMatch = userService.findByEmail(signUpRequest.getEmail());
        } catch (UserNotFoundException userNotFoundException) {
            logger.info(userNotFoundException.getMessage());
        }

        if (emailMatch != null) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create a new customer account
        User user = new Customer(signUpRequest.getUsername(), signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()),
                signUpRequest.getName(), signUpRequest.getAddress(), signUpRequest.getPhoneNumber());

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findTopByName(ERole.ROLE_CUSTOMER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findTopByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "worker":
                        Role modRole = roleRepository.findTopByName(ERole.ROLE_WORKER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findTopByName(ERole.ROLE_CUSTOMER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userService.createUser(user);

        return ResponseEntity.ok(new MessageResponse("Customer successfully registered."));
    }
}