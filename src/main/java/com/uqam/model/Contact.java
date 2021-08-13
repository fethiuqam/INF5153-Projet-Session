package com.uqam.model;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Table(name = "tContacts")
public class Contact implements Cloneable{

    @Id
    @GeneratedValue( strategy= GenerationType.IDENTITY )
    private long id;

    private String address;
    private String phone;
    private String email;

    public Contact(){}

    public Contact(String address, String phone, String email) {
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    //method to validate an adress usign regex
    public boolean validAddress(String address) throws AppException{
        if( !address.matches("[A-Za-z0-9]+")){
            throw new AppException("L'adresse contient des caracteres invalide. Veuillez entrer une adresse valide.");
        }
        return true;
    }

    // method tov validate phone numebr using regex
    public boolean validPhone(String phone) throws AppException{
        if(!phone.matches("[0-9]+")){
            throw new AppException("Le numero de telephone est invalide. Veuillez entrer que des chiffres.");
        }
        if(!(phone.length() ==10)){
            throw new AppException(("Le numero est invalide. Veuillez entrer un numero a 10 chiffres."));
        }
        return true;
    }


    //method to validate an email using regex expression
    public boolean validEmail(String email)throws AppException {
        if( !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")){
            throw new AppException("Le format du email est invalide.");
        }
        return true;
    }


    //getters
    public String getAddress() {
        return Optional.ofNullable(address).orElse("Non spécifié");
    }
    public String getPhone() {
        return Optional.ofNullable(phone).orElse("Non spécifié");
    }
    public String getEmail() {
        return Optional.ofNullable(email).orElse("Non spécifié");
    }

    @Override
    protected Contact clone() {
        try {
            return (Contact) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
