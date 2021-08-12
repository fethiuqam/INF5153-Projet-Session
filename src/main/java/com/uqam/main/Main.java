package com.uqam.main;


import com.uqam.model.Contact;
import com.uqam.model.Diagnostic;
import com.uqam.model.Doctor;
import com.uqam.model.Establishment;

public class Main  {

    public static void main(String[] args) {
       // MyApp.main(args);
        Establishment establishment = new Establishment("A55646","Hopital Juif");

        System.out.println(establishment.toString());
    }
}

