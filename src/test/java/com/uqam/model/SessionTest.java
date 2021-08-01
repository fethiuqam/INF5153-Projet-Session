package com.uqam.model;

import com.uqam.dao.DataSource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SessionTest {
    @Mock
    DataSource dataSourceMock;

    @Test
    public void loginTestTrue(){
        Doctor doctor = new Doctor("aaa", "bbb", "123456","Cardiologie",
                new Establishment("111", "CHUM"));
        User user = new User("user", "pass", doctor);
        when(dataSourceMock.findByUsernameAndPassword("user", "pass")).thenReturn(user);
        Session session = new Session(dataSourceMock);
        assertTrue( session.login("user","pass" ));
    }

    @Test
    public void loginTestFalse(){
        when(dataSourceMock.findByUsernameAndPassword("user01", "pass")).thenReturn(null);
        Session session = new Session(dataSourceMock);
        assertFalse( session.login("user01","pass" ));
    }



}