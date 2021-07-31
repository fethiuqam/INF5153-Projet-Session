package com.uqam.model;

public class User {

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean validPassword(String password){
        if (password.length()<8){
            return false;
        }
        return password.matches("[A-Za-z0-9]+");
    }
}
