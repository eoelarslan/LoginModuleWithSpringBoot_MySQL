package com.login.module.util.enums;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;


public enum MessageStatus {

    PASSWORD_DOES_NOT_MATCH(-1), USER_SESSION_EXISTS(0), USER_SESSION_TIME_OUT(1), USER_SESSION_NOT_EXIST(
            2), CALCULATION_ERROR(3), AUTH_FAILED(4), BASE_64_NOT_SUPPORTED(
					5), BAD_CREDENTIALS(6), USER_NOT_FOUND(7), USER_CREATED(8),
            USER_CREATED_AS_PASSIVE(9), OK(10), USER_NOT_ACTIVATED(11), DUBLICATE_ENTRY(12), DATA_RETRIEVED(13), DELETED(14),
    USER_UPDATED(16), NOT_FOUND(17), GROUP_CODE_DOES_NOT_EXIST(18), GROUP_CODE_NEEDED(19);

    @Autowired
    private static MessageSource messageSource;

    int responseStatusCode;

    MessageStatus(int reponseStatusCode) {
        this.responseStatusCode = reponseStatusCode;
    }

    public int getResponseStatusCode() {
        return responseStatusCode;
    }

    public void setResponseStatusCode(int responseStatusCode) {
        this.responseStatusCode = responseStatusCode;
    }
}
