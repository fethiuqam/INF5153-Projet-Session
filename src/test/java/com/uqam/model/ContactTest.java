package com.uqam.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ContactTest {

    Contact contact = new Contact("5825henribourassa","5144922317","testemail@gmail.com");

    @Test
    void testValidAddress() throws AppException {
        Assertions.assertTrue(contact.validAddress(contact.getAddress()));
    }

    @Test
    void testValidPhone() throws AppException {
        Assertions.assertTrue(contact.validPhone(contact.getPhone()));
    }
    @Test
    void testValidEmail() throws AppException {
        Assertions.assertTrue(contact.validEmail(contact.getEmail()));
    }

    @Test
    void testInvalidAddress() throws AppException{
        Assertions.assertThrows(AppException.class, () -> {
            Contact contact = new Contact("5825henribourassa//","5144922317","testemail@gmail.com");
            contact.validAddress(contact.getAddress());
        });
    }

    @Test
    void testInvalidPhone() {
        Assertions.assertThrows(AppException.class, () -> {
            Contact contact = new Contact("5825henribourassa","514978774","testemail@gmail.com");
            contact.validPhone(contact.getPhone());
        });
    }

    @Test
    void testInvalidPhone2() {
        Assertions.assertThrows(AppException.class, () -> {
            Contact contact = new Contact("5825henribourassa","514999562A","testemail@gmail.com");
            contact.validPhone(contact.getPhone());
        });
    }

    @Test
    void testInvalidEmail() {
        Assertions.assertThrows(AppException.class, () -> {
            Contact contact = new Contact("5825henribourassa","5149995624","testemailgmail.com");
            contact.validPhone(contact.getEmail());
        });
    }

    @Test
    void validClone(){
        Contact contact2 = contact.clone();
        Assertions.assertEquals(contact2.getAddress(),contact.getAddress());
        Assertions.assertEquals(contact2.getEmail(),contact.getEmail());
        Assertions.assertEquals(contact2.getPhone(),contact.getPhone());
    }

    @Test
    void validToString(){
        Assertions.assertEquals("Contact{address='5825henribourassa', phone='5144922317', email='testemail@gmail.com'}",contact.toString());
    }


}

