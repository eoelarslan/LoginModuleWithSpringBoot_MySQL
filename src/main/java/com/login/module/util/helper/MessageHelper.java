package com.login.module.util.helper;


import com.login.module.util.enums.MessageStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;


@Component
public class MessageHelper {

    @Autowired
    public MessageSource messageSource;

    public String getMessageByMessageStatus(MessageStatus messageStatus, Object[] args) {
        switch (messageStatus) {
            case PASSWORD_DOES_NOT_MATCH:
                return messageSource.getMessage("login.password.doesnot.match", args, LocaleContextHolder.getLocale());
            case USER_SESSION_EXISTS:
                return messageSource.getMessage("session.user.session.exists", args, LocaleContextHolder.getLocale());
            case USER_SESSION_TIME_OUT:
                return messageSource.getMessage("session.user.session.time.out", args, LocaleContextHolder.getLocale());
            case USER_SESSION_NOT_EXIST:
                return messageSource.getMessage("session.user.session.not.exists", args, LocaleContextHolder.getLocale());
            case CALCULATION_ERROR:
                return messageSource.getMessage("general.calculation.error", args, LocaleContextHolder.getLocale());
            case AUTH_FAILED:
                return messageSource.getMessage("login.auth.failed", args, LocaleContextHolder.getLocale());
            case USER_CREATED:
                return messageSource.getMessage("login.user.created", args, LocaleContextHolder.getLocale());
            case USER_CREATED_AS_PASSIVE:
                return messageSource.getMessage("login.user.created_as_passive", args, LocaleContextHolder.getLocale());
            case OK:
                return messageSource.getMessage("login.user.signin", args, LocaleContextHolder.getLocale());
            case DUBLICATE_ENTRY:
                return messageSource.getMessage("register.user.dublicate", args, LocaleContextHolder.getLocale());
            case DATA_RETRIEVED:
                return messageSource.getMessage("retrieve.football.data", args, LocaleContextHolder.getLocale());
            case DELETED:
                return messageSource.getMessage("delete.data", args, LocaleContextHolder.getLocale());
            case USER_UPDATED:
                return messageSource.getMessage("user.team.date", args, LocaleContextHolder.getLocale());
            case USER_NOT_FOUND:
                return messageSource.getMessage("unable.find.user", args, LocaleContextHolder.getLocale());
            case NOT_FOUND:
                return messageSource.getMessage("data.not.found", args, LocaleContextHolder.getLocale());
            case GROUP_CODE_DOES_NOT_EXIST:
                return messageSource.getMessage("group.code.doest.not.exist", args, LocaleContextHolder.getLocale());
            case GROUP_CODE_NEEDED:
                return messageSource.getMessage("group.code.needed", args, LocaleContextHolder.getLocale());
            default:
                return messageSource.getMessage("general.fail", args, LocaleContextHolder.getLocale());
        }
    }

    public String getMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }

}