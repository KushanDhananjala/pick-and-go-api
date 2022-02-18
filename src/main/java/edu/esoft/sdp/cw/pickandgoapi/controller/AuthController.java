package edu.esoft.sdp.cw.pickandgoapi.controller;

import edu.esoft.sdp.cw.pickandgoapi.dto.Mail;
import edu.esoft.sdp.cw.pickandgoapi.payload.request.LoginRequest;
import edu.esoft.sdp.cw.pickandgoapi.payload.request.SignupRequest;
import edu.esoft.sdp.cw.pickandgoapi.payload.response.JwtResponse;
import edu.esoft.sdp.cw.pickandgoapi.security.jwt.JwtUtils;
import edu.esoft.sdp.cw.pickandgoapi.security.service.UserDetailsImpl;
import edu.esoft.sdp.cw.pickandgoapi.service.EmailService;
import edu.esoft.sdp.cw.pickandgoapi.service.UserRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserRegisterService userRegisterService;
    private final EmailService emailService;

    @PostMapping("/auth/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(
                jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles
        ));
    }

//    @PreAuthorize("hasRole('ROLE_HEAD_OFFICE_MANAGER')")
    @PostMapping(value = "/auth/signup", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest) throws Exception {
        return userRegisterService.createUser(signupRequest);
    }

    @GetMapping(value = "/auth/get-all")
    public ResponseEntity<?> getAllUsers() {
        return userRegisterService.getAllUsers();
    }

    @GetMapping(value = "/auth/service-check")
    public String serviceCheck() {

        // Email configs
        Mail mail = new Mail();
        mail.setMailTo("thisaruudam@gmail.com");
        mail.setSubject("New User Creation for account - " + "UserName");
        Map<String, Object> model = new HashMap<>();
        model.put("tempPw", "12345asd");
        mail.setProps(model);
        try {
            emailService.sendEmail(mail, "password-reset");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return "Service is Up and Running!!!";
    }


}
