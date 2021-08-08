package com.uqam.model;

public interface Authenticable {

    public boolean login(String username, String password) throws AppException;
}
