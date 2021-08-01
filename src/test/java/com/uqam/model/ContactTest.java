package com.uqam.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ContactTest {

    Contact contact = new Contact("5825henribourassa","5146492231","testemail@gmail.com");

    @Test
    void testValidAddress(){
        Assertions.assertTrue(contact.validAddress(contact.getAddress()));
    }

    @Test
    void testValidPhone(){

    }
}
