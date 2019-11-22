package com.login.module.controller;


import com.login.module.controller.dto.base.GenericResponseDTO;
import com.login.module.controller.dto.requestdto.RegisterRequestDTO;
import com.login.module.controller.dto.requestdto.SignInRequestDTO;
import com.login.module.controller.dto.requestdto.SignOutRequestDTO;
import com.login.module.controller.dto.responsedto.LoginResponseDTO;
import com.login.module.model.User;
import com.login.module.repository.UserRepository;
import com.login.module.service.LoginService;
import com.login.module.util.enums.MessageStatus;
import com.login.module.util.helper.Base64Helper;
import com.login.module.util.helper.MailServiceHelper;
import com.login.module.util.helper.MessageHelper;
import javassist.tools.web.BadHttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static com.login.module.util.enums.MessageStatus.*;


@RestController
@RequestMapping(value = "/")
public class LoginController {

    @Autowired
    LoginService loginService;

    @Autowired
    MessageHelper messageHelper;

    @Autowired
    MailServiceHelper mailServiceHelper;

    @Autowired
    private UserRepository userRepository;

    // TODO SESSION WILL BE HANDLED


    @PostMapping("/register")
    public ResponseEntity createUser(@Valid @RequestBody RegisterRequestDTO registerRequestDTO) throws BadHttpRequest {

        String emailToken = mailServiceHelper.createEmailToken();

        MessageStatus messageStatus = loginService.saveUserAsPassive(registerRequestDTO, emailToken);
        switch (messageStatus) {
            case AUTH_FAILED:
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new GenericResponseDTO<>(HttpStatus.FORBIDDEN,
                        messageHelper.getMessageByMessageStatus(AUTH_FAILED, null), null));
            case PASSWORD_DOES_NOT_MATCH:
                return ResponseEntity.badRequest().body(new GenericResponseDTO<>(HttpStatus.BAD_REQUEST,
                        messageHelper.getMessageByMessageStatus(PASSWORD_DOES_NOT_MATCH, null), null));
            case DUBLICATE_ENTRY:
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new GenericResponseDTO<>(HttpStatus.FORBIDDEN,
                        messageHelper.getMessageByMessageStatus(DUBLICATE_ENTRY, null), null));
            case USER_CREATED_AS_PASSIVE:

                String subject = messageHelper.getMessage("login.register.mail.subject", null);
                String message = mailServiceHelper.createRegisterMessage("login.register.mail.message",
                        registerRequestDTO.getEmail(), emailToken);
                mailServiceHelper.sendMail(registerRequestDTO.getEmail(), subject, message);

                /*userList.add(userRepository.findByEmail(registerRequestDTO.getEmail()));*/

                return ResponseEntity.ok().body(new GenericResponseDTO<>(HttpStatus.ACCEPTED,
                        messageHelper.getMessageByMessageStatus(USER_CREATED_AS_PASSIVE, null), null));

            default:
                return ResponseEntity.badRequest().body(new GenericResponseDTO<>(HttpStatus.FORBIDDEN,
                        messageHelper.getMessageByMessageStatus(MessageStatus.BAD_CREDENTIALS, null), null));
        }
    }

    @GetMapping(value = "/user/mail/verification/{mail}/{token}")
    public ResponseEntity mailVerification(@PathVariable("mail") String mailAddress,
                                           @PathVariable("token") String mailToken) {

        MessageStatus messageStatus = loginService.saveUserAsActive(Base64Helper.decoder(mailAddress), mailToken);
        switch (messageStatus) {
            case USER_CREATED:
                return ResponseEntity.ok().body(new GenericResponseDTO<>(HttpStatus.ACCEPTED,
                        messageHelper.getMessageByMessageStatus(MessageStatus.USER_CREATED, null), null));
            default:
                return ResponseEntity.badRequest().body(new GenericResponseDTO<>(HttpStatus.BAD_REQUEST,
                        messageHelper.getMessageByMessageStatus(MessageStatus.BAD_CREDENTIALS, null), null));
        }
    }

    @PostMapping(value = "/user/signin")
    public ResponseEntity signIn(@RequestBody SignInRequestDTO signInRequestDTO) {

        List<LoginResponseDTO> userLoginResponse = new ArrayList<>();
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
        MessageStatus messageStatus = loginService.userSignIn(signInRequestDTO);
        User user;

        switch (messageStatus) {
            case OK:
                user = userRepository.findByEmail(signInRequestDTO.getEmail());

                loginResponseDTO.setEmail(user.getEmail());
                loginResponseDTO.setUserId(user.getId());
                loginResponseDTO.setName(user.getName());

                userLoginResponse.add(loginResponseDTO);

                return ResponseEntity.ok().body(new GenericResponseDTO<>(HttpStatus.ACCEPTED,
                        messageHelper.getMessageByMessageStatus(MessageStatus.OK, null), userLoginResponse));
            default:
                return ResponseEntity.badRequest().body(new GenericResponseDTO<>(HttpStatus.BAD_REQUEST,
                        messageHelper.getMessageByMessageStatus(AUTH_FAILED, null), null));
        }
    }

    @DeleteMapping("/user/logout")
    public ResponseEntity<?> deleteUser(@RequestBody SignOutRequestDTO signOutRequestDTO) {

        User user = userRepository.findByEmail(signOutRequestDTO.getEmail());
        // TODO SESSION WILL BE HANDLED
        if (user != null) {
            return ResponseEntity.ok(new GenericResponseDTO<>(HttpStatus.OK, "User Logged Out", null));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new GenericResponseDTO<>(HttpStatus.BAD_REQUEST, "User Not Found", null));
        }
    }
}