package com.login.module.service;



import com.login.module.controller.dto.requestdto.RegisterRequestDTO;
import com.login.module.controller.dto.requestdto.SignInRequestDTO;
import com.login.module.model.User;
import com.login.module.repository.UserRepository;
import com.login.module.util.enums.LoginModule;
import com.login.module.util.enums.MessageStatus;
import com.login.module.util.enums.UserStatus;
import com.login.module.util.helper.BasicAuthenticationHelper;
import javassist.tools.web.BadHttpRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public MessageStatus saveUserAsPassive(RegisterRequestDTO registerRequestDTO, String emailToken)
            throws BadHttpRequest {
        String password = registerRequestDTO.getPassword().trim();
        String rePassword = registerRequestDTO.getRePassword().trim();
        String eMail = registerRequestDTO.getEmail();

        Optional<User> user = userRepository.findByEmail(eMail);

        String baseAuthTokenSTR = BasicAuthenticationHelper.createBasicAuthToken(eMail, password);

        if(user.isPresent()){ // User is created before.
            return MessageStatus.DUBLICATE_ENTRY;
        }else {
            if (eMail != null && eMail.trim().length() > 0 && password.trim().length() > 0) {
                if (password.equals(rePassword)) {
                    user = Optional.of(new User());
                    modelMapper.map(registerRequestDTO, user.get());
                    user.get().setUserStatusCode(UserStatus.PASSIVE.getUserStatusCode());
                    user.get().setLoginModuleCode(LoginModule.MANUEL.getLoginModuleCode());
                    user.get().setCreatedTime(LocalDateTime.now());


                    if (baseAuthTokenSTR.equals(MessageStatus.BASE_64_NOT_SUPPORTED.toString())) {
                        return MessageStatus.BASE_64_NOT_SUPPORTED;
                    } else {
                        user.get().setAuthenticationToken(baseAuthTokenSTR);
                        user.get().setUserStatusCode(UserStatus.PASSIVE.getUserStatusCode());
                        user.get().setAuthenticationToken(emailToken);
                        userRepository.save(user.get());
                        return MessageStatus.USER_CREATED_AS_PASSIVE;
                    }

                } else {
                    return MessageStatus.PASSWORD_DOES_NOT_MATCH;
                }
            } else {
                throw new BadHttpRequest();
            }
        }
    }

    public MessageStatus saveUserAsActive(String email, String emailToken) {

        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            if (user.get().getAuthenticationToken().equals(emailToken)) {
                user.get().setUserStatusCode(UserStatus.ACTIVE.getUserStatusCode());
                userRepository.save(user.get());
                return MessageStatus.USER_CREATED;
            } else {
                return MessageStatus.AUTH_FAILED;
            }
        } else {
            return MessageStatus.USER_NOT_FOUND;
        }
    }

    public MessageStatus userSignIn(SignInRequestDTO signInRequestDTO) {

        Optional<User> user = userRepository.findByEmail(signInRequestDTO.getEmail());

        if (user.isPresent()) {
            if (user.get().getPassword().equals(signInRequestDTO.getPassword())){ // if passwords matched.
                if (user.get().getUserStatusCode() == UserStatus.ACTIVE.getUserStatusCode()){ // if user is activated.
                    return MessageStatus.OK;
                } else {
                    return MessageStatus.USER_NOT_ACTIVATED;
                }
            } else {
                return MessageStatus.PASSWORD_DOES_NOT_MATCH;
            }
        } else {
            return MessageStatus.USER_NOT_FOUND;
        }
    }
}
