package com.uqam.dao;

import com.uqam.model.User;
import org.junit.jupiter.api.*;

public class DataSourceTest {

    private DataSource source;

    @BeforeEach
    public void setUp() {
        source = new DataSource();
    }

    @AfterEach
    public void tearDown() {
        source = null;
    }

        @Test
    public void findByUsernameAndPasswordTestSuccess(){
        String username = "docteur01";
        String password = "111111";
        String expected = "User{username='docteur01', password='111111', " +
                "doctor=Doctor{firstname='Marsilius', lastname='Dupuis', " +
                "permit='13698', specialty='médecine de famille', " +
                "establishment=Establishment{identification='123456', designation='CHUM de montréal'}}}";
        User user = source.findByUsernameAndPassword(username, password);
        Assertions.assertEquals(expected, user.toString());
    }

    @Test
    public void findByUsernameAndPasswordTestFailed(){
        String username = "docteur01";
        String password = "123451335";
        User user = source.findByUsernameAndPassword(username, password);
        Assertions.assertNull(user);
    }

}
