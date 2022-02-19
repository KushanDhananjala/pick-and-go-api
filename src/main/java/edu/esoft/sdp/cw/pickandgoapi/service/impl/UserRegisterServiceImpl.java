package edu.esoft.sdp.cw.pickandgoapi.service.impl;

import edu.esoft.sdp.cw.pickandgoapi.dto.CustomerDTO;
import edu.esoft.sdp.cw.pickandgoapi.dto.Mail;
import edu.esoft.sdp.cw.pickandgoapi.dto.UserDTO;
import edu.esoft.sdp.cw.pickandgoapi.entity.Center;
import edu.esoft.sdp.cw.pickandgoapi.entity.CenterUser;
import edu.esoft.sdp.cw.pickandgoapi.entity.Role;
import edu.esoft.sdp.cw.pickandgoapi.entity.User;
import edu.esoft.sdp.cw.pickandgoapi.enums.ERole;
import edu.esoft.sdp.cw.pickandgoapi.enums.UserStatus;
import edu.esoft.sdp.cw.pickandgoapi.payload.request.ResetPasswordRequest;
import edu.esoft.sdp.cw.pickandgoapi.payload.request.SignupRequest;
import edu.esoft.sdp.cw.pickandgoapi.payload.response.MessageResponse;
import edu.esoft.sdp.cw.pickandgoapi.repository.CenterRepository;
import edu.esoft.sdp.cw.pickandgoapi.repository.CenterUserRepository;
import edu.esoft.sdp.cw.pickandgoapi.repository.RoleRepository;
import edu.esoft.sdp.cw.pickandgoapi.repository.UserRepository;
import edu.esoft.sdp.cw.pickandgoapi.service.CustomerService;
import edu.esoft.sdp.cw.pickandgoapi.service.EmailService;
import edu.esoft.sdp.cw.pickandgoapi.service.OTPService;
import edu.esoft.sdp.cw.pickandgoapi.service.UserRegisterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRED)
@Slf4j
public class UserRegisterServiceImpl implements UserRegisterService {

    final UserRepository userRepository;
    final RoleRepository roleRepository;
    final PasswordEncoder encoder;
    final EmailService emailService;
    final CenterRepository centerRepository;
    final CenterUserRepository centerUserRepository;
    final CustomerService customerService;
    final OTPService otpService;

    @Override
    public ResponseEntity<?> createUser(SignupRequest signupRequest) throws Exception {

        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        User userEntity = new User();
        userEntity.setUsername(signupRequest.getUsername());
        userEntity.setEmail(signupRequest.getEmail());
        userEntity.setPassword(encoder.encode(signupRequest.getPassword()));

        Set<String> strRoles = signupRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "head-office":
                        Role headOffice = roleRepository.findByName(ERole.ROLE_HEAD_OFFICE_MANAGER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(headOffice);
                        userEntity.setType(String.valueOf(ERole.ROLE_HEAD_OFFICE_MANAGER));
                        userEntity.setStatus(String.valueOf(UserStatus.A));
                        break;
                    case "paying-officer":
                        Role payingOfficer = roleRepository.findByName(ERole.ROLE_PAYING_OFFICER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(payingOfficer);
                        userEntity.setType(String.valueOf(ERole.ROLE_PAYING_OFFICER));
                        userEntity.setStatus(String.valueOf(UserStatus.A));
                        break;
                    case "center-manager":
                        Role centerManager = roleRepository.findByName(ERole.ROLE_CENTER_MANAGER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(centerManager);
                        userEntity.setType(String.valueOf(ERole.ROLE_CENTER_MANAGER));
                        userEntity.setStatus(String.valueOf(UserStatus.A));
                        break;
                    case "rider":
                        Role rider = roleRepository.findByName(ERole.ROLE_RIDER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(rider);
                        userEntity.setType(String.valueOf(ERole.ROLE_RIDER));
                        userEntity.setStatus(String.valueOf(UserStatus.P));
                        break;
                    case "mobile":
                        Role mobile = roleRepository.findByName(ERole.ROLE_MOBILE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(mobile);
                        userEntity.setType(String.valueOf(ERole.ROLE_MOBILE_USER));
                        userEntity.setStatus(String.valueOf(UserStatus.A));
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        userEntity.setRoles(roles);
        userRepository.save(userEntity);

        if(String.valueOf(ERole.ROLE_MOBILE_USER).equals(userEntity.getType())) {

            CustomerDTO customerDTO = new CustomerDTO();

            customerDTO.setCustomerName(signupRequest.getUsername().split("@")[0]);
            customerDTO.setUserName(signupRequest.getUsername().split("@")[0]);
            customerDTO.setEmail(signupRequest.getEmail());
            customerDTO.setMobileNo(signupRequest.getMobileNumber());

            customerService.save(customerDTO);
            otpService.generateOTP(signupRequest.getMobileNumber());
        } else if(String.valueOf(ERole.ROLE_RIDER).equals(userEntity.getType())) {
            otpService.generateOTP(signupRequest.getMobileNumber());
        }

        if (String.valueOf(ERole.ROLE_CENTER_MANAGER).equals(userEntity.getType())) {

            Center center = centerRepository.getById(signupRequest.getRegistrationId());

            CenterUser centerUser = new CenterUser();
            centerUser.setUser(userEntity);
            centerUser.setPwReset(false);
            centerUser.setCenter(center);
            centerUser.setTempPw(generateTempPassword());

            centerUserRepository.save(centerUser);

            // Email configs
            Mail mail = new Mail();
            mail.setMailTo(signupRequest.getEmail());
            mail.setSubject("New User Creation for account - " + signupRequest.getUsername());
            Map<String, Object> model = new HashMap<>();
            model.put("title", "New User Password Change");
            model.put("tempPw", centerUser.getTempPw());
            model.put("resetUrl", "http://localhost:4200/reset-password");
            mail.setProps(model);

            try {
                emailService.sendEmail(mail, "password-reset");
            } catch (MessagingException e) {
                e.printStackTrace();
                log.error(e.getMessage());
            }

        }

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));

    }

    @Override
    public ResponseEntity<?> centerUserPasswordChange(ResetPasswordRequest resetPasswordRequest) {

        User existingUser = userRepository.findByEmail(resetPasswordRequest.getEmail());
        CenterUser centerUser = centerUserRepository.findCenterUserByUserId(existingUser.getId());

        if (!centerUser.getTempPw().equals(resetPasswordRequest.getTempPassword())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Entered temporary password is invalid !"));
        }

        existingUser.setPassword(encoder.encode(resetPasswordRequest.getPassword()));
        centerUser.setPwReset(true);
        centerUser.setTempPw("");

        userRepository.save(existingUser);
        centerUserRepository.save(centerUser);

        return ResponseEntity.ok(new MessageResponse("Password changed successfully!"));
    }

    @Override
    public ResponseEntity<?> forgotPassword(String email) {

        User existingUser = userRepository.findByEmail(email);

        if (null == existingUser) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("No User found for this email!"));
        }

        existingUser.setResetRequested(true);
        existingUser.setTempPw(generateTempPassword());

        userRepository.save(existingUser);

        // Email configs
        Mail mail = new Mail();
        mail.setMailTo(existingUser.getEmail());
        mail.setSubject("Reset Password for account - " + existingUser.getEmail());
        Map<String, Object> model = new HashMap<>();
        model.put("title", "Forgot Password?");
        model.put("tempPw", existingUser.getTempPw());
        model.put("resetUrl", "http://localhost:4200/reset-password");
        mail.setProps(model);

        try {
            emailService.sendEmail(mail, "password-reset");
        } catch (MessagingException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }

        return ResponseEntity.ok(new MessageResponse("Please check your email and reset your password!"));
    }

    @Override
    public ResponseEntity<?> resetPassword(ResetPasswordRequest resetPasswordRequest) {

        User existingUser = userRepository.findByEmail(resetPasswordRequest.getEmail());

        if (null == existingUser) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("No User found for this email!"));
        }

        if (!existingUser.getTempPw().equals(resetPasswordRequest.getTempPassword())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Entered temporary password is invalid !"));
        }

        existingUser.setResetRequested(false);
        existingUser.setTempPw("");
        existingUser.setPassword(encoder.encode(resetPasswordRequest.getPassword()));

        userRepository.save(existingUser);

        return ResponseEntity.ok(new MessageResponse("Password reset successfully!"));
    }

    @Override
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users.stream().map(user -> {
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(user, userDTO);
            return userDTO;
        }).collect(Collectors.toList()));
    }

    private String generateTempPassword() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 6) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();
    }

    @Override
    public UserDTO convertUserToUserDTO(final User user) {
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);

        return userDTO;
    }

}
