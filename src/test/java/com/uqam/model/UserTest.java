package com.uqam.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserTest {

    String username = "Mohamed";

    @Test
    void validValidPassword(){
        String password = "Al8s3F6";
        User user = new User(username,password);
        Assertions.assertFalse(user.validPassword(user.getPassword()));
    }

    @Test
    void validValidPassword2(){
        String password = "Al8s3F6s";
        User user = new User(username,password);
        Assertions.assertTrue(user.validPassword(user.getPassword()));
    }

    @Test
    void validValidPassword3(){
        String password = "AAAAAAAAA";
        User user = new User(username,password);
        Assertions.assertTrue(user.validPassword(user.getPassword()));
    }

}
