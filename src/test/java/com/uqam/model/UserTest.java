package com.uqam.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserTest {

    String username = "Mohamed";

    @Test
    void testValidPassword() throws AppException {
        String password = "Al8s3F66";
        User user = new User(username,password,null);
        Assertions.assertTrue(user.validPassword(user.getPassword()));
    }

    @Test
    void testInvalidPassword2(){
        User user = new User(username,"AlQQ6s",null);
        Assertions.assertThrows(AppException.class, () ->{
            user.validPassword(user.getPassword());
        });
    }

    @Test
    void validValidPassword3(){
        String password = "AAAAAAAAA2!1";
        User user = new User(username,password,null);
        Assertions.assertThrows(AppException.class, () ->{
            user.validPassword(user.getPassword());
        });    }

    @Test
    void testToString(){
        User user = new User("Mohamed","Rehouma",null);
        Assertions.assertEquals("User{username='Mohamed', password='Rehouma', doctor=null}",user.toString());
    }


}
