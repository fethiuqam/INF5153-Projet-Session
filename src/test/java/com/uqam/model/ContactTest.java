package com.uqam.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ContactTest {

    Contact contact = new Contact("5825henribourassa","5144922317","testemail@gmail.com");

    @Test
    void testValidAddress(){
        Assertions.assertTrue(contact.validAddress(contact.getAddress()));
    }

    @Test
    void testValidPhone(){
        Assertions.assertTrue(contact.validPhone(contact.getPhone()));
    }
    @Test
    void testValidEmail(){
        Assertions.assertTrue(contact.validEmail(contact.getEmail()));
    }

    Contact contact2 = contact.clone();
}

