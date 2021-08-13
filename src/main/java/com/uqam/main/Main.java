package com.uqam.main;


import com.uqam.model.Contact;
import com.uqam.model.Gender;
import com.uqam.model.Patient;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Main  {

    public static void main(String[] args) {
   //     MyApp.main(args);


        String dateStr = "1996-02-23";
        Date date = Date.valueOf(dateStr);
        Contact contact = new Contact("2054 maisonneuve,h2c 2e2","5149871234","ck191923@ens.uqam.ca");
        Patient patient = new Patient("Mohamed","Rehouma", Gender.MALE,date,"Algiers","REHM26154978",contact);

        System.out.println(patient);
    }
}

