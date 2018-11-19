package com.bigsur.AndroidChatWithMaps.AuthManager;


public class Credentials {

    private String login;
    private String password;


    public Credentials(String loginString, String passString) {
        this.login = loginString;
        this.password = passString;
    }


    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }



    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
