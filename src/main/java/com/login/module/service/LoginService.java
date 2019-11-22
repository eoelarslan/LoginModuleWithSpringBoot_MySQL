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


@Service
public class LoginService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMappper;

    public MessageStatus saveUserAsPassive(RegisterRequestDTO registerRequestDTO, String emailToken)
            throws BadHttpRequest {
        String password = registerRequestDTO.getPassword().trim();
        String rePassword = registerRequestDTO.getRePassword().trim();
        String eMail = registerRequestDTO.getEmail();

        User user = userRepository.findByEmail(eMail);

        String baseAuthTokenSTR = BasicAuthenticationHelper.createBasicAuthToken(eMail, password);

        if(user != null){ //user is created before.
            return MessageStatus.DUBLICATE_ENTRY;
        }else {
            if (eMail != null && eMail.trim().length() > 0 && password.trim().length() > 0) {
                if (password.equals(rePassword)) {
                    user = new User();
                    modelMappper.map(registerRequestDTO, user);
                    user.setUserStatusCode(UserStatus.PASSIVE.getUserStatusCode());
                    user.setLoginModuleCode(LoginModule.MANUEL.getLoginModuleCode());

                    if (baseAuthTokenSTR.equals(MessageStatus.BASE_64_NOT_SUPPORTED.toString())) {
                        return MessageStatus.BASE_64_NOT_SUPPORTED;
                    } else {
                        user.setAuthenticationToken(baseAuthTokenSTR);
                        user.setUserStatusCode(UserStatus.PASSIVE.getUserStatusCode());
                        user.setAuthenticationToken(emailToken);
                        userRepository.save(user);
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

        User user = userRepository.findByEmail(email);
        if (user != null) {
            if (user.getAuthenticationToken().equals(emailToken)) {
                user.setUserStatusCode(UserStatus.ACTIVE.getUserStatusCode());
                userRepository.save(user);
                return MessageStatus.USER_CREATED;
            } else {
                return MessageStatus.AUTH_FAILED;
            }
        } else {
            return MessageStatus.USER_NOT_FOUND;
        }
    }

    public MessageStatus userSignIn(SignInRequestDTO signInRequestDTO) {

        User user = userRepository.findByEmail(signInRequestDTO.getEmail());

        if (user != null) {
            if (user.getPassword().equals(signInRequestDTO.getPassword())){ // if passwords matched.
                if (user.getUserStatusCode() == UserStatus.ACTIVE.getUserStatusCode()){ // if user is activated.
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
