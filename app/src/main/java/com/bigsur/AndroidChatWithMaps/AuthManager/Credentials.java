package com.bigsur.AndroidChatWithMaps.AuthManager;


public class Credentials {
    private String login;
    private String password;


    public Credentials(String loginString, String passString) {
        this.login = loginString;
        this.password = passString;
    }

    public Credentials() {

    }


    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

}
