package com.uqam.main;


import com.uqam.model.Contact;
import com.uqam.model.Gender;
import com.uqam.model.Patient;
import com.uqam.model.User;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Main  {

    public static void main(String[] args) {
      //MyApp.main(args);

        User user = new User("Mohamed","Rehouma",null);
        System.out.println(user);

    }
}

