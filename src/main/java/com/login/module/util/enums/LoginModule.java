package com.login.module.util.enums;


public enum LoginModule {

    MANUEL(0);

    private int loginModuleCode;

    LoginModule(int loginModuleCode) {
        this.loginModuleCode = 0;
    }

    public int getLoginModuleCode() {
        return loginModuleCode;
    }
}
